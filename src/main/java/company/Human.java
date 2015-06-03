package company;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Necros on 19.03.2015.
 */
public class Human implements Comparable {

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String otch = "";
    Date datr = new Date();
    String isti = "";

    ;
    Map<Double, Treatment> treatmentList = new HashMap<>();
    private String fio = "";
    private String ima = "";
    public Human() {
    }
    public Human(String isti, String fio, String ima, String otch, Date datr) {
        this.fio = fio;
        this.ima = ima;
        this.otch = otch;
        this.datr = datr;
        this.isti = isti;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getIma() {
        return ima;
    }

    public void setIma(String ima) {
        this.ima = ima;
    }

    public String getOtch() {
        return otch;
    }

    public void setOtch(String otch) {
        this.otch = otch;
    }

    public Date getDatr() {
        return datr;
    }

    public void setDatr(Date datr) {
        this.datr = datr;
    }

    public String getReadableDatr() {
        return sdf.format(datr);
    }

    public String getIsti() {
        return isti;
    }

    public void setIsti(String isti) {
        this.isti = isti;
    }

    public Map<Double, Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(Map<Double, Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public String getFullName() {
        return fio + " " + ima + " " + otch;
    }

    @Override
    public int compareTo(Object o) {
        Human otherHuman = (Human) o;
        String otherFIO = otherHuman.getFio() + otherHuman.getIma() + otherHuman.getOtch();
        String thisFIO = getFio() + getIma() + getOtch();
        return thisFIO.compareTo(otherFIO);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return getIsti() + "\t" + getFio() + " " + getIma() + " " + getOtch() + "\t" + sdf.format(getDatr());
    }
}
