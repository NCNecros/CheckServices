package company;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Necros on 19.03.2015.
 */
@org.springframework.stereotype.Service
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
        return result;
    }

    private void checkForMissedService() {
        services = getUslugi().values().stream()
                .map(Service::getKusl)
                .collect(Collectors.toList());

        checkMissedGinecologUslugi();
        checkForReduantGinecologUsluga();
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

    private void checkMissedGinecologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, ginecologUslugi)) && !services.contains(GINECOLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("акушер-гинеколог.");
        }
    }

    private void checkMissedGinecologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, ginecologDetiUslugi)) && !services.contains(GINECOLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("акушер-гинеколог (дети).");
        }
    }

    private void checkMissedGastroenterologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, gastroenterologUslugi)) && !services.contains(GASTROENTEROLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("гастроэнтеролог.");
        }
    }

    private void checkMissedGastroenterologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, gastroenterologDetiUslugi)) && !services.contains(GASTROENTEROLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("гастроэнтеролог (дети).");
        }
    }

    private void checkMissedHirurgDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, hirurgDetiUslugi)) && !services.contains(HIRURG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("детский-хирург");
        }
    }

    private void checkMissedInfectionistDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, infectionistDetiUslugi)) && !services.contains(INFECTIONIST_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("инфекционист (дети)");
        }
    }

    private void checkMissedInfectionistUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, infectionistUslugi)) && !services.contains(INFECTIONIST_OBRASHENIE)) {
            addErrorForMissedObrashenie("инфекционист.");
        }
    }

    private void checkMissedCardiologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, cardiologUslugi)) && !services.contains(CARDIOLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("кардиолог");
        }
    }

    private void checkMissedCardiologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, cardiologDetiUslugi)) && !services.contains(CARDIOLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("кардиолог-дети.");
        }
    }

    private void checkMissedColoproctologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, coloproctologUslugi)) && !services.contains(COLOPROCTOLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("колопроктолог");
        }
    }

    private void checkMissedNevrologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, nevrologUslugi)) && !services.contains(NEVROLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("невролог");
        }
    }

    private void checkMissedNevrologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, nevrologDetiUslugi)) && !services.contains(NEVROLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("невролог (дети)");
        }
    }

    private void checkMissedLorUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, lorUslugi)) && !services.contains(LOR_OBRASHENIE)) {
            addErrorForMissedObrashenie("отоларинголог");
        }
    }

    private void checkMissedLorDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, lorDetiUslugi)) && !services.contains(LOR_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("отоларинголог (дети)");
        }
    }

    private void checkMissedOftalmologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, oftalmologUslugi)) && !services.contains(OFTALMOLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("офтальмолог");
        }
    }

    private void checkMissedOftalmologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, oftalmologDetiUslugi)) && !services.contains(OFTALMOLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("офтальмолог (дети)");
        }
    }

    private void checkMissedPediatrUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, pediatrUslugi)) && !services.contains(PEDIATR_OBRASHENIE)) {
            addErrorForMissedObrashenie("педиатр");
        }
    }

    private void checkMissedPediatrUchastkovyiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, pediatrUchastkovyiUslugi)) && !services.contains(PEDIATR_UCHASTKOVYJ_OBRASHENIE)) {
            addErrorForMissedObrashenie("педиатр-участковый");
        }
    }

    private void checkMissedPulmonologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, pulmonologUslugi)) && !services.contains(PULMONOLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("пульмонолог");
        }
    }

    private void checkMissedPulmonologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, pulmonologDetiUslugi)) && !services.contains(PULMONOLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("пульмонолог (дети)");
        }
    }

    private void checkMissedTerapevtUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, terapevtUslugi)) && !services.contains(TERAPEVT_OBRASHENIE)) {
            addErrorForMissedObrashenie("тераевт");
        }
    }

    private void checkMissedTerapevtUchastkovyiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, terapevtUchastkovyiUslugi)) && !services.contains(TERAPEVT_UCHASTKOVYJ_OBRASHENIE)) {
            addErrorForMissedObrashenie("терапевт-участковый");
        }
    }

    private void checkMissedTravmatologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, travmatologUslugi)) && !services.contains(TRAVMATOLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("травматолог");
        }
    }

    private void checkMissedTravmatologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, travmatologDetiUslugi)) && !services.contains(TRAVMATOLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("травматолог (дети)");
        }
    }

    private void checkMissedUrologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, urologUslugi)) && !services.contains(UROLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("уролог");
        }
    }

    private void checkMissedUrologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, urologDetiUslugi)) && !services.contains(UROLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("уролог (дети)");
        }
    }

    private void checkMissedHirugrUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, hirurgUslugi)) && !services.contains(HIRURG_OBRASHENIE)) {
            addErrorForMissedObrashenie("хирург");
        }
    }

    private void checkMissedEndocrinologUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, endocrinologUslugi)) && !services.contains(ENDOCRINOLOG_OBRASHENIE)) {
            addErrorForMissedObrashenie("эндокринолог");
        }
    }

    private void checkMissedEndocrinologDetiUslugi() {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, endocrinologDetiUslugi)) && !services.contains(ENDOCRINOLOG_DETI_OBRASHENIE)) {
            addErrorForMissedObrashenie("эндокринолог");
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
