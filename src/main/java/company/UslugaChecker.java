package company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Necros on 20.03.2015.
 */
public class UslugaChecker {
    public static List<String> check(Service service) {
        List<String> result = new ArrayList<>();
        if (service.getDatn() == null || service.getDato() == null) {
            Human human = service.getParent().getParent();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            result.add(human.toString() + "\tсодержит обращение с незакрытыми мероприятиями");
        }
        return result;
    }
}
