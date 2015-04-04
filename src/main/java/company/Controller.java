package company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.*;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Necros on 23.03.2015.
 */
@org.springframework.stereotype.Service
public class Controller{
    String dir;
    @FXML
    private TextArea textArea;

    @FXML
    public void selectFile(ActionEvent actionEvent) throws IOException, ZipException {
        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File(dir));
        fileChooser.setTitle("Выберите файл счета");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
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
        List<String> errors = new ArrayList<>();
        for (Human human : humanMap.values()) {
            errors.addAll(human.checkErrors());
        }
        String pathToFile = file.getParentFile().getAbsolutePath();
        String fileName = file.getName();
//            saveErrorsToFile(errors, pathToFile + File.separator + fileName + "_ошибки.csv", "utf-8");
        saveErrorsToExcel(errors, pathToFile + File.separator + fileName + "_ошибки.xls");
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

    private void saveErrorsToExcel(List<String> errors, String filename){
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Ошибки");
        sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);

        int counter = 0;
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Номер карты");
        row.createCell(1).setCellValue("ФИО");
        row.createCell(2).setCellValue("Дата рождения");
        row.createCell(3).setCellValue("Ошибка");
        for (String line : errors.stream()
                .distinct()
                .sorted((e1, e2) -> e1.split("\t")[1].compareTo(e2.split("\t")[1]))
                .collect(Collectors.toList())) {
            counter++;
            String[] err = line.split("\t");
            row = sheet.createRow(counter);
            row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(err[0]);
            row.createCell(1).setCellValue(err[1]);
            row.createCell(2).setCellValue(err[2]);
            row.createCell(3).setCellValue(err[3]);
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
