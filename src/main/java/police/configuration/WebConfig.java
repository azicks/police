package police.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import police.model.repo.AccidentRepository;
import police.model.repo.impl.AccidentRepositoryMemoryImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "police")
public class WebConfig {

    @Bean
    ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    AccidentRepository accidentRepository() {
        return new AccidentRepositoryMemoryImpl();
    }
}
