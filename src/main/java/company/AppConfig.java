package company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    public List<String> pediatrServices() {
        List<String> strings = new ArrayList<>();
        strings.add("B01.031.014");
        return strings;
    }

    @Bean
    public List<String> uPediatrService(){
        List<String> strings = new ArrayList<>();
        strings.add("B01.031.013");
        strings.add("B01.031.006");
        return strings;
    }

    @Bean
    public List<String> nevrologServices() {
        List<String> strings = new ArrayList<>();
        strings.add("B01.023.001");
        strings.add("B01.023.004");
        return strings;
    }

    @Bean
    public List<String> lorServices() {
        List<String> strings = new ArrayList<>();
        strings.add("B01.028.001");
        return strings;
    }

    @Bean
    public List<String> oftalmologServices() {
        List<String> strings = new ArrayList<>();
        strings.add("B01.029.001");
        strings.add("B01.029.006");
        return strings;
    }

    @Bean
    public List<String> terapevtServices() {
        List<String> strings = new ArrayList<>();
        strings.add("B01.047.019");
        return strings;
    }

    @Bean
    public List<String> uTerapevtServices() {
        List<String> strings = new ArrayList<>();
        strings.add("B01.047.014");
        strings.add("B01.047.020");
        return strings;
    }

}
