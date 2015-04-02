package company;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class PropertiesConfiguration {
    @Bean
    public PropertyPlaceholderConfigurer properties() {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setIgnoreResourceNotFound(false);
        final List<Resource> resourceLst = new ArrayList<>();
        resourceLst.add(new ClassPathResource("application.properties"));
        ppc.setLocations(resourceLst.toArray(new Resource[]{}));
        return ppc;
    }
}