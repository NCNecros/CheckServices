package company;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

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
        services = getUslugi().values().stream()
                .map(Service::getKusl)
                .collect(Collectors.toList());

        for (Service service : getUslugi().values()) {
            result.addAll(UslugaChecker.check(service));
        }
        checkForServiceDuplicates();
        checkForMissedService();
        checkIncorrectDateOfService();
//        checkForReduantServices();
        return result;
    }

    private void checkForMissedService() {

        for (Uslugi307 uslugi307 : uslugi307List.getUslugi()) {
            checkMissedUslugi(uslugi307.getUslugi(), uslugi307.getObrashenie(), uslugi307.getDoctor());
        }
    }

    private void checkForReduantServices() {
        for (Uslugi307 uslugi307 : uslugi307List.getUslugi()) {
            checkForReduantDoctorService(uslugi307.getUslugi(), uslugi307.getObrashenie(), uslugi307.getDoctor());
        }
    }

    private void checkMissedUslugi(List<String> uslugi, String obrashenie, String doctor) {
        if (services.size() > 1 && (CollectionUtils.containsAny(services, uslugi)) && !services.contains(obrashenie)) {
            addErrorForMissedObrashenie(doctor);
        }
    }

    private void checkIncorrectDateOfService() {
        List<Date> dates = getUslugi().values().stream().map(Service::getDatn).collect(Collectors.toList());
        if (!dates.contains(getDatn())) {
            result.add(toString() + " нет услуги совпадающей с датой начала лечения");
        }
        if (!dates.contains(getDato())){
            result.add(toString() + " нет услуги совпадающей с датой окончания лечения");
        }
    }

    private void addErrorForMissedObrashenie(String doctor) {
        result.add(toString() + " отсутствует обращение - врач-" + doctor);
    }

    private void addErrorForReduantObrashenie(String doctor) {
        result.add(toString() + " лишнее обращение - врач-" + doctor);
    }

    private void checkForReduantDoctorService(List<String> uslugi, String obrashenie, String doctor) {
        if (CollectionUtils.containsAny(services, uslugi) && services.contains(obrashenie) && services.size() == 2) {
            addErrorForReduantObrashenie(doctor);
        }
    }

    private void checkForServiceDuplicates() {
        List<Service> allServices = new ArrayList<>(getUslugi().values());
        List<Service> servicesWithOutDuplicates = allServices.stream()
                .filter(e -> Collections.frequency(allServices, e) == 1)
                .collect(Collectors.toList());

        if (allServices.size() != servicesWithOutDuplicates.size()){
            result.add(toString() + " содержит дубликаты услуг");
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
