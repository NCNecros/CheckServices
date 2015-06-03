package company;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Necros on 20.03.2015.
 */
public class UslugaChecker {
    public static List<Error> check(Service service) {
        List<Error> result = new ArrayList<>();
        if (service.getDatn() == null || service.getDato() == null) {
            Human human = service.getParent().getParent();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            result.add(new Error(human, null, "содержит обращение с незакрытыми мероприятиями"));
        }
        return result;
    }
}
