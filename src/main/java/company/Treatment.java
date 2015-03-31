package company;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Necros on 19.03.2015.
 */
@org.springframework.stereotype.Service
@Scope(value = "prototype")
public class Treatment {
    public String OSMOTR_GINECOLOGA = "B01.001.019";
    public String OSMOTR_PEDIATRA = "B01.031.016";
    public String OSMOTR_UPEDIATRA = "B01.031.016.01";
    public String OSMOTR_OFTALMOLOGA = "B01.029.015";
    public String OSMOTR_NEVROLOGA = "B01.023.012";
    public String OSMOTR_LORA = "B01.028.011";
    public String OSMOTR_HIRURGA = "B01.057.010";
    public String OSMOTR_TERAPEVTA = "B01.047.022";
    public String OSMOTR_UTERAPEVTA = "B01.047.022.01";
    public String OSMOTR_ENDOCRINOLOG = "B01.058.013";

    private Treatment treatment;

    @Resource
    private List<String> ginecologServices;
    @Resource
    private List<String> pediatrServices;
    @Resource
    private List<String> nevrologServices;
    @Resource
    private List<String> lorServices;
    @Resource
    private List<String> oftalmologServices;
    @Resource
    private List<String> terapevtServices;

    Date datn;
    Date dato;
    String mkb = "";
    String doctor;
    Human parent;
    Map<Double, Service> uslugi = new HashMap<>();


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
        List<String> result = new ArrayList<>();
        Human human = getParent();
        for (Service service : getUslugi().values()) {
            result.addAll(UslugaChecker.check(service));
        }
//        checkForServiceDuplicates(result, human);
        checkForMissedService(result, human);

        return result;
    }

    private void checkForMissedService(List<String> result, Human human) {
        List<String> services = getUslugi().values().stream()
                .map(Service::getKusl)
                .collect(Collectors.toList());

        //TODO Интересный пример как использовать стрим-апи для наполнения MAP
        //Map<String, Date> stringDateMap = getUslugi().values().stream()
        //                  .collect(Collectors.toMap(Service::getKusl, Service::getDatn));

        checkMissedPediatrService(result, human, services);
        checkMissedGinekologService(result, human, services);
        checkMissedNevrologService(result, human, services);
        checkMissedLORService(result, human, services);
        checkMissedOftalmologService(result, human, services);
        checkMissedTerapevtService(result, human, services);
        checkMissedHirurgService(result, human, services);
        checkMissedEndokrinologService(result, human, services);
        checkForReduantNevrologService(result, human, services);
        checkForReduantGinecologService(result, human, services);
        checkMissedUPediatrService(result, human, services);
        checkMissedUTerapevtService(result, human, services);

    }

    private void checkMissedPediatrService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && services.contains("B01.031.014") && !services.contains("B01.031.016")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-педиатру.");
        }
    }

    private void checkMissedUPediatrService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && (services.contains("B01.031.013") || services.contains("B01.031.006")) && !services.contains("B01.031.016.01")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-педиатру участковому.");
        }
    }

    private void checkMissedGinekologService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && (CollectionUtils.containsAny(services,ginecologServices)) && !services.contains("B01.001.019")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-акушеру-гинекологу.");
        }
    }

    private void checkMissedNevrologService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && (services.contains("B01.023.001")
                || services.contains("B01.023.004")) && !services.contains("B01.023.012")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-неврологу.");
        }
    }

    private void checkMissedLORService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && (services.contains("B01.028.001")) && !services.contains("B01.028.011")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-отоларингологу.");
        }
    }

    private void checkMissedOftalmologService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && (services.contains("B01.029.001")
                || services.contains("B01.029.006")) && !services.contains("B01.029.015")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-офтальмологу.");
        }
    }

    private void checkMissedHirurgService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && (services.contains("B01.057.007")
                || services.contains("B01.057.008")) && !services.contains("B01.057.010")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-хирургу.");
        }
    }

    private void checkMissedEndokrinologService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && (services.contains("B01.058.001")
                || services.contains("B01.058.010")) && !services.contains("B01.058.013")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-акушеру-гинекологу.");
        }
    }

    private void checkMissedTerapevtService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && services.contains("B01.047.019") && !services.contains("B01.047.022")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-терапевту.");
        }
    }

    private void checkMissedUTerapevtService(List<String> result, Human human, List<String> services) {
        if (services.size() > 1 && (services.contains("B01.047.014") || services.contains("B01.047.020")) && !services.contains("B01.047.022.01")) {
            result.add(human + "\t" + "случай лечения не содержит обращения к врачу-терапевту участковому.");
        }
    }

    private void checkForReduantGinecologService(List<String> result, Human human, List<String> services){
        if (CollectionUtils.containsAny(services, ginecologServices) && services.contains(OSMOTR_GINECOLOGA) && services.size() ==2){
            result.add(human + "\tсодержит лишнее обращение к врачу-акушеру-гинекологу.");
        }
    }

    private void checkForReduantNevrologService(List<String> result, Human human, List<String> services){
        if (CollectionUtils.containsAny(services, nevrologServices) && services.contains(OSMOTR_NEVROLOGA) && services.size() ==2){
            result.add(human + "\tсодержит лишнее обращение к врачу-неврологу.");
        }
    }

    private void checkForServiceDuplicates(List<String> result, Human human) {
        for (Service service : getUslugi().values()) {
            List<Service> servicesWithOutCurrent = new ArrayList<>(getUslugi().values());
            servicesWithOutCurrent.remove(service);
            result.addAll(servicesWithOutCurrent.stream()
                    .filter(service::isDuplicates)
                    .map(otherService -> human + "\t" + "содержит дублирующуюся услугу")
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public String toString(){
        String result="";
        for (Service service : uslugi.values()){
            result = result + "{"+service.getKusl()+"}";
        }
        return result;
    }
    public Map<Double, Service> getUslugi() {
        return uslugi;
    }

    public void setUslugi(Map<Double, Service> uslugi) {
        this.uslugi = uslugi;
    }
}
