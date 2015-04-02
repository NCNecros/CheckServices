package company;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Necros on 19.03.2015.
 */
@org.springframework.stereotype.Service()
@Scope(value = "prototype")

public class Treatment {
    Date datn;
    Date dato;
    String mkb = "";
    String doctor;
    Human parent;
    Map<Double, Service> uslugi = new HashMap<>();
    List<String> result;
    List<String> services;

    @Value("#{'${ginecolog.services}'.split(',')}")
    private List<String> ginecologUslugi;
    @Value("${ginecolog.obrashenie}")
    private String GINECOLOG_OBRASHENIE;
    @Value("#{'${ginecolog.deti.services}'.split(',')}")
    private List<String> ginecologDetiUslugi;
    @Value("${ginecolog.deti.obrashenie}")
    private String GINECOLOG_DETI_OBRASHENIE;
    @Value("#{'${gastroenterolog.uslugi}'.split(',')}")
    private List<String> gastroenterologUslugi;
    @Value("${gastroenterolog.obrashenie}")
    private String GASTROENTEROLOG_OBRASHENIE;
    @Value("#{'${gastroenterolog.deti.uslugi}'.split(',')}")
    private List<String> gastroenterologDetiUslugi;
    @Value("${gastroenterolog.deti.obrashenie}")
    private String GASTROENTEROLOG_DETI_OBRASHENIE;
    @Value("#{'${hirurg.deti.uslugi}'.split(',')}")
    private List<String> hirurgDetiUslugi;
    @Value("${hirurg.deti.obrashenie}")
    private String HIRURG_DETI_OBRASHENIE;
    @Value("#{'${infectionist.uslugi}'.split(',')}")
    private List<String> infectionistUslugi;
    @Value("${infectionist.obrashenie}")
    private String INFECTIONIST_OBRASHENIE;
    @Value("#{'${infectionist.deti.uslugi}'.split(',')}")
    private List<String> infectionistDetiUslugi;
    @Value("${infectionist.deti.obrashenie}")
    private String INFECTIONIST_DETI_OBRASHENIE;
    @Value("#{'${cardiolog.uslugi}'.split(',')}")
    private List<String> cardiologUslugi;
    @Value("${cardiolog.obrashenie}")
    private String CARDIOLOG_OBRASHENIE;
    @Value("#{'${cardiolog.deti.uslugi}'.split(',')}")
    private List<String> cardiologDetiUslugi;
    @Value("${cardiolog.deti.obrashenie}")
    private String CARDIOLOG_DETI_OBRASHENIE;
    @Value("#{'${coloproctolog.uslugi}'.split(',')}")
    private List<String> coloproctologUslugi;
    @Value("${coloproctolog.obrashenie}")
    private String COLOPROCTOLOG_OBRASHENIE;
    @Value("#{'${nevrolog.uslugi}'.split(',')}")
    private List<String> nevrologUslugi;
    @Value("${nevrolog.obrashenie}")
    private String NEVROLOG_OBRASHENIE;
    @Value("#{'${nevrolog.deti.uslugi}'.split(',')}")
    private List<String> nevrologDetiUslugi;
    @Value("${nevrolog.deti.obrashenie}")
    private String NEVROLOG_DETI_OBRASHENIE;
    @Value("#{'${lor.uslugi}'.split(',')}")
    private List<String> lorUslugi;
    @Value("${lor.obrashenie}")
    private String LOR_OBRASHENIE;
    @Value("#{'${lor.deti.uslugi}'.split(',')}")
    private List<String> lorDetiUslugi;
    @Value("${lor.deti.obrashenie}")
    private String LOR_DETI_OBRASHENIE;
    @Value("#{'${oftalmolog.uslugi}'.split(',')}")
    private List<String> oftalmologUslugi;
    @Value("${oftalmolog.obrashenie}")
    private String OFTALMOLOG_OBRASHENIE;
    @Value("#{'${oftalmolog.deti.uslugi}'.split(',')}")
    private List<String> oftalmologDetiUslugi;
    @Value("${oftalmolog.deti.obrashenie}")
    private String OFTALMOLOG_DETI_OBRASHENIE;
    @Value("#{'${pediatr.uslugi}'.split(',')}")
    private List<String> pediatrUslugi;
    @Value("${pediatr.obrashenie}")
    private String PEDIATR_OBRASHENIE;
    @Value("#{'${pediatr.uchastkovyj.uslugi}'.split(',')}")
    private List<String> pediatrUchastkovyiUslugi;
    @Value("${pediatr.uchastkovyj.obrashenie}")
    private String PEDIATR_UCHASTKOVYJ_OBRASHENIE;
    @Value("#{'${pulmonolog.uslugi}'.split(',')}")
    private List<String> pulmonologUslugi;
    @Value("${pulmonolog.obrashenie}")
    private String PULMONOLOG_OBRASHENIE;
    @Value("#{'${pulmonolog.deti.uslugi}'.split(',')}")
    private List<String> pulmonologDetiUslugi;
    @Value("${pulmonolog.deti.obrashenie}")
    private String PULMONOLOG_DETI_OBRASHENIE;
    @Value("#{'${terapevt.uslugi}'.split(',')}")
    private List<String> terapevtUslugi;
    @Value("${terapevt.obrashenie}")
    private String TERAPEVT_OBRASHENIE;
    @Value("#{'${terapevt.uchastkovyj.uslugi}'.split(',')}")
    private List<String> terapevtUchastkovyiUslugi;
    @Value("${terapevt.uchastkovyj.obrashenie}")
    private String TERAPEVT_UCHASTKOVYJ_OBRASHENIE;
    @Value("#{'${travmatolog.uslugi}'.split(',')}")
    private List<String> travmatologUslugi;
    @Value("${travmatolog.obrashenie}")
    private String TRAVMATOLOG_OBRASHENIE;
    @Value("#{'${travmatolog.deti.uslugi}'.split(',')}")
    private List<String> travmatologDetiUslugi;
    @Value("${travmatolog.deti.obrashenie}")
    private String TRAVMATOLOG_DETI_OBRASHENIE;
    @Value("#{'${urolog.uslugi}'.split(',')}")
    private List<String> urologUslugi;
    @Value("${urolog.obrashenie}")
    private String UROLOG_OBRASHENIE;
    @Value("#{'${urolog.deti.uslugi}'.split(',')}")
    private List<String> urologDetiUslugi;
    @Value("${urolog.deti.obrashenie}")
    private String UROLOG_DETI_OBRASHENIE;
    @Value("#{'${hirurg.uslugi}'.split(',')}")
    private List<String> hirurgUslugi;
    @Value("${hirurg.obrashenie}")
    private String HIRURG_OBRASHENIE;
    @Value("#{'${endocrinolog.uslugi}'.split(',')}")
    private List<String> endocrinologUslugi;
    @Value("${endocrinolog.obrashenie}")
    private String ENDOCRINOLOG_OBRASHENIE;
    @Value("#{'${endocrinolog.deti.uslugi}'.split(',')}")
    private List<String> endocrinologDetiUslugi;
    @Value("${endocrinolog.deti.obrashenie}")
    private String ENDOCRINOLOG_DETI_OBRASHENIE;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private Map<List<String>, String> uslugiAndObrahsenie = new HashMap<>();
    private Map<String, String> obrahsenieAndDoctor= new HashMap<>();

    @PostConstruct
    public void init() {
        uslugiAndObrahsenie.put(ginecologUslugi,            GINECOLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(ginecologDetiUslugi,        GINECOLOG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(gastroenterologUslugi,      GASTROENTEROLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(gastroenterologDetiUslugi,  GASTROENTEROLOG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(hirurgDetiUslugi,           HIRURG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(infectionistUslugi,         INFECTIONIST_OBRASHENIE);
        uslugiAndObrahsenie.put(infectionistDetiUslugi,     INFECTIONIST_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(cardiologUslugi,            CARDIOLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(cardiologDetiUslugi,        CARDIOLOG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(coloproctologUslugi,        COLOPROCTOLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(nevrologUslugi,             NEVROLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(nevrologDetiUslugi,         NEVROLOG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(lorUslugi,                  LOR_OBRASHENIE);
        uslugiAndObrahsenie.put(lorDetiUslugi,              LOR_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(oftalmologUslugi,           OFTALMOLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(oftalmologDetiUslugi,       OFTALMOLOG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(pediatrUslugi,              PEDIATR_OBRASHENIE);
        uslugiAndObrahsenie.put(pediatrUchastkovyiUslugi,   PEDIATR_UCHASTKOVYJ_OBRASHENIE);
        uslugiAndObrahsenie.put(pulmonologUslugi,           PULMONOLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(pulmonologDetiUslugi,       PULMONOLOG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(terapevtUslugi,             TERAPEVT_OBRASHENIE);
        uslugiAndObrahsenie.put(terapevtUchastkovyiUslugi,  TERAPEVT_UCHASTKOVYJ_OBRASHENIE);
        uslugiAndObrahsenie.put(travmatologUslugi,          TRAVMATOLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(travmatologDetiUslugi,      TRAVMATOLOG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(urologUslugi,               UROLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(urologDetiUslugi,           UROLOG_DETI_OBRASHENIE);
        uslugiAndObrahsenie.put(hirurgUslugi,               HIRURG_OBRASHENIE);
        uslugiAndObrahsenie.put(endocrinologUslugi,         ENDOCRINOLOG_OBRASHENIE);
        uslugiAndObrahsenie.put(endocrinologDetiUslugi,     ENDOCRINOLOG_DETI_OBRASHENIE);

        obrahsenieAndDoctor.put(GINECOLOG_OBRASHENIE, "акушер-гинеколог");
        obrahsenieAndDoctor.put(GINECOLOG_DETI_OBRASHENIE, "акушер-гинеколог(дети)");
        obrahsenieAndDoctor.put(GASTROENTEROLOG_OBRASHENIE, "гастроэнтеролог");
        obrahsenieAndDoctor.put(GASTROENTEROLOG_DETI_OBRASHENIE, "гастроэнтеролог (дети)");
        obrahsenieAndDoctor.put(HIRURG_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(INFECTIONIST_OBRASHENIE, "");
        obrahsenieAndDoctor.put(INFECTIONIST_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(CARDIOLOG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(CARDIOLOG_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(COLOPROCTOLOG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(NEVROLOG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(NEVROLOG_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(LOR_OBRASHENIE, "");
        obrahsenieAndDoctor.put(LOR_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(OFTALMOLOG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(OFTALMOLOG_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(PEDIATR_OBRASHENIE, "");
        obrahsenieAndDoctor.put(PEDIATR_UCHASTKOVYJ_OBRASHENIE, "");
        obrahsenieAndDoctor.put(PULMONOLOG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(PULMONOLOG_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(TERAPEVT_OBRASHENIE, "");
        obrahsenieAndDoctor.put(TERAPEVT_UCHASTKOVYJ_OBRASHENIE, "");
        obrahsenieAndDoctor.put(TRAVMATOLOG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(TRAVMATOLOG_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(UROLOG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(UROLOG_DETI_OBRASHENIE, "");
        obrahsenieAndDoctor.put(HIRURG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(ENDOCRINOLOG_OBRASHENIE, "");
        obrahsenieAndDoctor.put(ENDOCRINOLOG_DETI_OBRASHENIE, "");
    }

    public Date getDatn() {
        return datn;
    }

    public void setDatn(Date datn) {
        this.datn = datn;
    }

    public Date getDato() {
        return dato;
    }

    public void setDato(Date dato) {
        this.dato = dato;
    }

    public String getMkb() {
        return mkb;
    }

    public void setMkb(String mkb) {
        this.mkb = mkb;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Human getParent() {
        return parent;
    }

    public void setParent(Human parent) {
        this.parent = parent;
    }

    public List<String> checkUslugi() {
        result = new ArrayList<>();
        for (Service service : getUslugi().values()) {
            result.addAll(UslugaChecker.check(service));
        }
//        checkForServiceDuplicates(result, human);
        checkForMissedService();
        checkForReduantGinecologUsluga();
        return result;
    }

    private void checkForMissedService() {
        services = getUslugi().values().stream()
                .map(Service::getKusl)
                .collect(Collectors.toList());

        for (Map.Entry<List<String>, String> entry : uslugiAndObrahsenie.entrySet()){
            checkMissedUslugi(entry.getKey(), entry.getValue(), obrahsenieAndDoctor.get(entry.getValue()));
        }
    }

    private void checkMissedUslugi(List<String> uslugi, String obrashenie, String doctor) {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, uslugi)) && !services.contains(obrashenie)) {
            addErrorForMissedObrashenie(doctor);
        }
    }

    private void addErrorForMissedObrashenie(String doctor) {
        result.add(toString() + " отсутствует обращение - врач-" + doctor);
    }
    private void addErrorForReduantObrashenie(String doctor){
        result.add(toString() + " лишнее обращение - врач-" + doctor);
    }

    private void checkForReduantGinecologUsluga() {
        if (CollectionUtils.containsAny(services, ginecologUslugi) && services.contains(GINECOLOG_OBRASHENIE) && services.size() == 2) {
            addErrorForReduantObrashenie("акушер-гинеколог");
        }
    }

    private void checkForReduantNevrologService() {
        if (CollectionUtils.containsAny(services, nevrologUslugi) && services.contains(NEVROLOG_OBRASHENIE) && services.size() == 2) {
            addErrorForReduantObrashenie("невролог");
        }
    }

    private void checkForServiceDuplicates(List<String> result, Human human) {
        for (Service service : getUslugi().values()) {
            List<Service> servicesWithOutCurrent = new ArrayList<>(getUslugi().values());
            servicesWithOutCurrent.remove(service);
            result.addAll(servicesWithOutCurrent.stream()
                    .filter(service::isDuplicates)
                    .map(otherService -> toString()+ " дублирование услуги")
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public String toString() {
        return parent + "\t"
                + "(" + simpleDateFormat.format(getDatn()) + ")";
    }

    public Map<Double, Service> getUslugi() {
        return uslugi;
    }

    public void setUslugi(Map<Double, Service> uslugi) {
        this.uslugi = uslugi;
    }
}
