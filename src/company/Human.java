package company;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Necros on 19.03.2015.
 */
public class Human implements Comparable {

    String fio;
    String ima;
    String otch;
    Date datr;
    String isti;
    Map<Double, Treatment> treatmentList = new HashMap<>();

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

    public List<String> checkErrors() {
        List<String> result = new ArrayList<>();
        List<Treatment> list = new ArrayList<>(treatmentList.values());
        for (Treatment treatment : list) {
            result.addAll(treatment.checkUslugi());
            List<Treatment> obrasheniesWithOutCurrent = new ArrayList<>(list);
            obrasheniesWithOutCurrent.remove(treatment);
            for (Treatment otherObr : obrasheniesWithOutCurrent) {
                Boolean res = otherObr.getDoctor().equalsIgnoreCase(treatment.getDoctor()) &&
                        otherObr.getMkb().equalsIgnoreCase(treatment.getMkb());
                if (res) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    result.add(toString() + "\tсодержит более одного обращения с одним диагнозом и одним врачом ("+sdf.format(treatment.getDatn())+")");
                }
            }
        }
        return result;
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
