package company;

import com.esotericsoftware.yamlbeans.YamlWriter;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Necros on 31.03.2015.
 */
@Configuration
@ComponentScan(basePackages = "company")
public class AppConfig {


    @Bean
    public String xmlUslugi() throws Exception {
        YamlWriter writer = new YamlWriter(new FileWriter("output.yml"));
        writer.getConfig().setClassTag("vrachebnieuslugi", VrachebnieUslugi.class);
        List<VrachebnieUslugi> vrachebnieUslugiList = new ArrayList<>();
        VrachebnieUslugi usluga = new VrachebnieUslugi();
        usluga.setUslugi(Arrays.asList("B01", "B02", "B03"));
        usluga.setDoctor("asfas");
        usluga.setObrashenie("VVVV");
        vrachebnieUslugiList.add(usluga);
        usluga = new VrachebnieUslugi();
        usluga.setUslugi(Arrays.asList("B01", "B02", "B03"));
        usluga.setDoctor("asfas");
        usluga.setObrashenie("VVVV");
        vrachebnieUslugiList.add(usluga);
        usluga = new VrachebnieUslugi();
        usluga.setUslugi(Arrays.asList("B01", "B02", "B03"));
        usluga.setDoctor("asfas");
        usluga.setObrashenie("VVVV");
        vrachebnieUslugiList.add(usluga);
        usluga = new VrachebnieUslugi();
        usluga.setUslugi(Arrays.asList("B01", "B02", "B03"));
        usluga.setDoctor("asfas");
        usluga.setObrashenie("VVVV");
        vrachebnieUslugiList.add(usluga);
        usluga = new VrachebnieUslugi();
        usluga.setUslugi(Arrays.asList("B01", "B02", "B03"));
        usluga.setDoctor("asfas");
        usluga.setObrashenie("VVVV");
        vrachebnieUslugiList.add(usluga);
        writer.write(vrachebnieUslugiList);
        writer.close();
        return "";
    }
}
