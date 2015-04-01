package company;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@org.springframework.stereotype.Service
public class DBFHelper {
    private DBFReader dbfReader;

    public void readFromP(String filename, Map<String, Human> humanMap) throws FileNotFoundException, DBFException {
        Object[] row;
        dbfReader = new DBFReader(new FileInputStream(filename));
        Map<String, Integer> fieldList = new HashMap<>();
        dbfReader.setCharactersetName("cp866");
        //Заполнение карты с полями, чтобы обращаться по имени поля
        for (Integer i = 0; i < dbfReader.getFieldCount(); i++) {
            DBFField field = dbfReader.getField(i);
            fieldList.put(field.getName(), i);
        }

        while ((row = dbfReader.nextRecord()) != null) {
            Human human;
            String humanid = (String) row[fieldList.get("ISTI")];
            if (!humanMap.containsKey(humanid)) {
                human = new Human();
                human.setFio(((String) row[fieldList.get("FIO")]).trim());
                human.setIma(((String) row[fieldList.get("IMA")]).trim());
                human.setOtch(((String) row[fieldList.get("OTCH")]).trim());
                human.setDatr((Date) row[fieldList.get("DATR")]);
                human.setIsti(humanid);
                humanMap.put(humanid, human);
            }
            human = humanMap.get(humanid);
            Treatment treatment = (Treatment) SpringApplicationContext.getBean("treatment");
            treatment.setParent(human);
            treatment.setDatn((Date) row[fieldList.get("DATN")]);
            treatment.setDato((Date) row[fieldList.get("DATO")]);
            human.getTreatmentList().put((Double) row[fieldList.get("SN")], treatment);
        }
    }

    public void readFromU(String filename, Map<String, Human> humanMap) throws FileNotFoundException, DBFException {
        Object[] row;
        dbfReader = new DBFReader(new FileInputStream(filename));
        dbfReader.setCharactersetName("cp866");
        Map<String, Integer> fieldList = new HashMap<>();

        for (Integer i = 0; i < dbfReader.getFieldCount(); i++) {
            DBFField field = dbfReader.getField(i);
            fieldList.put(field.getName(), i);
        }

        Map<Double, String> humansObrash = new HashMap<>();
        for (String str : humanMap.keySet()) {
            for (Double num : humanMap.get(str).getTreatmentList().keySet()) {
                humansObrash.put(num, str);
            }
        }

        while ((row = dbfReader.nextRecord()) != null) {
            Double obrId = (Double) row[fieldList.get("SN")];
            Human human = humanMap.get(humansObrash.get(obrId));
            Treatment treatment = human.getTreatmentList().get(obrId);
            Map<Double, Service> uslugi = treatment.getUslugi();
            Double uslid = (Double) row[fieldList.get("UID")];
            if (!uslugi.containsKey(uslid)) {
                Service service = new Service();
                service.setParent(treatment);
                service.setKusl(((String) row[fieldList.get("KUSL")]).trim());
                String mkb = ((String) row[fieldList.get("MKBX")]).trim();
                if (mkb.length() > 0) {
                    treatment.setMkb(((String) row[fieldList.get("MKBX")]).trim());
                }
                service.setDatn((Date) row[fieldList.get("DATN")]);
                service.setDato((Date) row[fieldList.get("DATO")]);
                treatment.setDoctor((String) row[fieldList.get("DOC_TABN")]);
                uslugi.put(uslid, service);
            }
        }

    }
}