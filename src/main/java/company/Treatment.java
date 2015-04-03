package company;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private Uslugi307List uslugi307List;

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
//        checkForReduantGinecologUsluga();
        return result;
    }

    private void checkForMissedService() {
        services = getUslugi().values().stream()
                .map(Service::getKusl)
                .collect(Collectors.toList());

        for (Uslugi307 uslugi307: uslugi307List.getUslugi()){
            checkMissedUslugi(uslugi307.getUslugi(), uslugi307.getObrashenie(), uslugi307.getDoctor());
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

//    private void checkForReduantGinecologUsluga() {
//        if (CollectionUtils.containsAny(services, ginecologUslugi) && services.contains(GINECOLOG_OBRASHENIE) && services.size() == 2) {
//            addErrorForReduantObrashenie("акушер-гинеколог");
//        }
//    }
//
//    private void checkForReduantNevrologService() {
//        if (CollectionUtils.containsAny(services, nevrologUslugi) && services.contains(NEVROLOG_OBRASHENIE) && services.size() == 2) {
//            addErrorForReduantObrashenie("невролог");
//        }
//    }

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
