package company;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.util.List;

/**
 * Created by Necros on 02.04.2015.
 */
@Configuration
public class ServicesFactory {
    private List<String> ginecologServices;
    @Bean
    public List<String> getGinecologSerices(){
     return ginecologServices;
    }
}
