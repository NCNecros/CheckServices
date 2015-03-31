package company;

import java.util.Date;

/**
 * Created by Necros on 19.03.2015.
 */
public class Service {
    Date datn;
    Treatment parent;
    Date dato;
    String kusl;

    public Treatment getParent() {
        return parent;
    }

    public void setParent(Treatment parent) {
        this.parent = parent;
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

    public String getKusl() {
        return kusl;
    }

    public void setKusl(String kusl) {
        this.kusl = kusl;
    }

    public Boolean isDuplicates(Service otherService) {
        return getDatn().equals(otherService.getDatn())
                && getKusl().equalsIgnoreCase(otherService.getKusl());
    }
}