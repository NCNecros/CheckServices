package company;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Necros on 31.03.2015.
 */
@Configuration
@ComponentScan(basePackages = "company")
public class AppConfig {

    @Bean
    public List<String> ginecologServices() {
        List<String> strings = new ArrayList<>();
        strings.add("B01.001.001");
        strings.add("B01.001.002");
        strings.add("B01.001.003");
        strings.add("B01.001.004");
        strings.add("B01.001.005");
        strings.add("B01.001.006");
        strings.add("B01.001.007");
        strings.add("B01.001.008");
        strings.add("B01.001.010");
        strings.add("B01.001.011");
        strings.add("B01.001.017");
        return strings;
    }

    @Bean
    public List<String> pediatrServices() {
        List<String> strings = new ArrayList<>();
        return strings;
    }

    @Bean
    public List<String> nevrologServices() {
        List<String> strings = new ArrayList<>();
        return strings;
    }

    @Bean
    public List<String> lorServices() {
        List<String> strings = new ArrayList<>();
        return strings;
    }

    @Bean
    public List<String> oftalmologServices() {
        List<String> strings = new ArrayList<>();
        return strings;
    }

    @Bean
    public List<String> terapevtServices() {
        List<String> strings = new ArrayList<>();
        return strings;
    }
}
