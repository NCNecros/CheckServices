package company;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Necros on 31.03.2015.
 */
@Configuration
@ComponentScan(basePackages = "company")
public class AppConfig {
    @Bean
    public Uslugi307List uslugi307() throws Exception {
        Reader reader = new FileReader(getClass().getClassLoader().getResource("Uslugi307.xml").getFile());
        Serializer serializer = new Persister();
        Uslugi307List uslugi307List = serializer.read(Uslugi307List.class, reader);
        return uslugi307List;
    }
}
