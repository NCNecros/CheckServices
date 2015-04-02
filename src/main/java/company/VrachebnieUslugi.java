package company;

import org.simpleframework.xml.*;

import java.util.List;

/**
 * Created by necros on 02.04.15.
 */
public class VrachebnieUslugi {
    private List<String> uslugi;
    private String obrashenie;

    public List<String> getUslugi() {
        return uslugi;
    }

    public void setUslugi(List<String> uslugi) {
        this.uslugi = uslugi;
    }

    public String getObrashenie() {
        return obrashenie;
    }

    public void setObrashenie(String obrashenie) {
        this.obrashenie = obrashenie;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    private String doctor;

}
