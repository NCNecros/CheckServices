package company;

import org.simpleframework.xml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Necros on 03.04.2015.
 */
@org.simpleframework.xml.Root
public class Uslugi307List {
    @ElementList(inline = true)
    private List<Uslugi307> uslugi307List = new ArrayList<>();
    public void addUslugi(Uslugi307 uslugi307){
        uslugi307List.add(uslugi307);
    }
    public List<Uslugi307> getUslugi(){
        return uslugi307List;
    }
}
