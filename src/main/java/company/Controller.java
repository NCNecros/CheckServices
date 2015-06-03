package company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Necros on 23.03.2015.
 */
@org.springframework.stereotype.Service
public class Controller{
    private String dir;
    @FXML
    private TextArea textArea;
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @FXML
    public void selectFile(ActionEvent actionEvent) throws IOException, ZipException {
        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File(dir));
        fileChooser.setTitle("Выберите файл счета");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            logger.debug("Файл не выбран");
            processFile(file);
        }
    }

    @FXML
    public void selectDir(ActionEvent actionEvent) throws IOException, ZipException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setInitialDirectory(new File(dir));
        directoryChooser.setTitle("Выберите каталог с файлами");
        File file = directoryChooser.showDialog(null);
        List<File> files = Files.list(file.toPath())
                .map(Path::toFile)
                .filter(c -> c.getName().endsWith("zip") || c.getName().endsWith("ZIP"))
                .collect(Collectors.toList());
        for (File f : files) {
            processFile(f);
        }
    }

    private void processFile(File file) throws IOException, ZipException {
        dir = file.getParentFile().getAbsolutePath();
        Path outdir = Files.createTempDirectory("_tmp" + Math.random());
        DBFHelper helper = new DBFHelper();
        Map<String, Human> humanMap = new HashMap<>();
        ZipFile zipFile = new ZipFile(file);
        Map<String, String> filelist = new HashMap<>();
        for (Object obj : zipFile.getFileHeaders()) {
            FileHeader header = (FileHeader) obj;
            if (header.getFileName().startsWith("P")) {
                filelist.put("P", header.getFileName());
                zipFile.extractFile(header, outdir.toString());
            }
            if (header.getFileName().startsWith("U")) {
                filelist.put("U", header.getFileName());
                zipFile.extractFile(header, outdir.toString());
            }
        }
        helper.readFromP(outdir + File.separator + filelist.get("P"), humanMap);
        helper.readFromU(outdir + File.separator + filelist.get("U"), humanMap);

        List<Error> errors = new ArrayList<>();
        for (Human human : humanMap.values()) {
            errors.addAll(human.checkErrors());
        }
        List<Treatment> treatmentList = new ArrayList<>();
        humanMap.values().stream().filter(h -> h.getTreatmentList() != null).forEach(h -> {
            treatmentList.addAll(h.getTreatmentList().values());
        });

        List<Error> generalErrors = new ArrayList<>();
        if (humanMap.values().stream().flatMap(e-> e.getTreatmentList().values().stream()).map(Treatment::getOGRN).distinct().collect(Collectors.toList()).size()>1){
            generalErrors.add(new Error(null,null,"Реестр содержит записи на несколько плательщиков"));
        }

        List<String> ogrnList= humanMap.values().stream().flatMap(e -> e.getTreatmentList().values().stream()).map(Treatment::getOGRN).collect(Collectors.toList());
        Map<String, Long> stringLongMap = ogrnList.stream().collect(Collectors.groupingBy(o->o, Collectors.counting()));
        Long maxCount = stringLongMap.values().stream().max(Long::compareTo).get();

        Iterator iterator = stringLongMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = (Map.Entry<String, Long>) iterator.next();
            if (entry.getValue() == maxCount) {
                iterator.remove();
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<Treatment> treatments = humanMap.values().stream().flatMap(human -> human.getTreatmentList().values().stream()).collect(Collectors.toList());
        treatments.stream().filter(treatment -> stringLongMap.containsKey(treatment.getOGRN()))
                .forEach(e -> errors.add(new Error(e.getParent(),e,"посторонний ОГРН")));

        Map<String, Long> sortedMap = stringLongMap.entrySet().stream().sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        sortedMap.remove(sortedMap.entrySet().iterator().next().getKey());
        sortedMap.entrySet().stream().forEach(e-> System.out.println(e.getKey()+ " "+e.getValue()));
        String pathToFile = file.getParentFile().getAbsolutePath();
        String fileName = file.getName();
//            saveErrorsToFile(errors, pathToFile + File.separator + fileName + "_ошибки.csv", "utf-8");
        saveErrorsToExcel(errors,generalErrors, pathToFile + File.separator + fileName + "_ошибки.xls");
        textArea.appendText(fileName + " проверка завершена\n");
    }

    /**
     * Сохраняет список уникальных записей в файл на диске.
     *
     * @param fileName имя файла
     * @param charset  кодировка в которой будет вестись запись
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException
     */
    private void saveErrorsToFile(List<String> errors, String fileName, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter pw = new PrintWriter(fileName, charset);
        Set<String> set = new TreeSet<>(errors);
        set.forEach(pw::println);
        set.forEach(System.out::println);
        pw.close();
    }

    private void saveErrorsToExcel(List<Error> errors, List<Error> generalErrors, String filename){
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Ошибки");
        sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);

        int counter = 0;
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Номер карты");
        row.createCell(1).setCellValue("ФИО");
        row.createCell(2).setCellValue("Дата рождения");
        row.createCell(3).setCellValue("Ошибка");
        for (Error error : generalErrors){
            counter++;
            row = sheet.createRow(counter);
            Cell cell= row.createCell(0, Cell.CELL_TYPE_STRING);
            cell.setCellValue(error.getError());
            Font font = wb.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 18);
            CellStyle style = wb.createCellStyle();
            style.setFont(font);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(style);
            sheet.addMergedRegion(new CellRangeAddress(counter,counter,0,3));
        }
        for (Error error : errors.stream()
                .distinct()
                .sorted((error1, error2)-> error1.getHuman().compareTo(error2.getHuman()))
                .collect(Collectors.toList())) {
            counter++;
            row = sheet.createRow(counter);
            row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(error.getHuman().getIsti());
            row.createCell(1).setCellValue(error.getHuman().getFullName());
            row.createCell(2).setCellValue(error.getHuman().getReadableDatr());
            row.createCell(3).setCellValue("("+error.getTreatment().getReadableDatN()+") "+error.getError());
        }
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            wb.write(fos);
            fos.close();
        }catch (IOException e){
            textArea.appendText("Ошибка записи файла с ошибками: "+e.getLocalizedMessage()+"\n");
        }
    }
@PostConstruct
    public void init() {
        if (Objects.equals(System.getProperty("os.name"), "Linux")){
            dir="~/";
        }else{
            dir="d:/";
        }
    }
}
