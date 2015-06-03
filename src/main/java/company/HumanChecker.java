package company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;
@org.springframework.stereotype.Service()
@Scope(value = "singleton")
public class HumanChecker {
    @Autowired
    TreatmentChecker treatmentChecker;
    public List<Error> checkErrors(Human human) {
        List<Error> result = new ArrayList<Error>();
        List<Treatment> list = new ArrayList<Treatment>(human.getTreatmentList().values());
        for (Treatment treatment : list) {
            result.addAll(treatmentChecker.checkUslugi(treatment));
            List<Treatment> obrasheniesWithOutCurrent = new ArrayList<Treatment>(list);
            obrasheniesWithOutCurrent.remove(treatment);
            for (Treatment otherObr : obrasheniesWithOutCurrent) {
                Boolean res = otherObr.getDoctor().equalsIgnoreCase(treatment.getDoctor()) &&
                        otherObr.getMkb().equalsIgnoreCase(treatment.getMkb());
                if (res && treatment.getUslugi().values().stream().anyMatch(e -> !e.getKusl().startsWith("B04"))) {
                    result.add(new Error(treatment.getParent(), treatment, "более одного обращения"));
                }
            }
        }
        return result;
    }
}