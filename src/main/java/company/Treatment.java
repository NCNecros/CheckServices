package company;

import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Necros on 19.03.2015.
 */
@org.springframework.stereotype.Service()
@Scope(value = "prototype")

public class Treatment {
    Map<Double, Service> uslugi = new HashMap<>();
    private Date datn;
    private Date dato;
    private String mkb = "";
    private String doctor;
    private Human parent;
    private String OGRN;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public String getOGRN() {
        return OGRN;
    }

    public void setOGRN(String OGRN) {
        this.OGRN = OGRN;
    }

    public String getReadableDatN() {
        return simpleDateFormat.format(datn);
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
