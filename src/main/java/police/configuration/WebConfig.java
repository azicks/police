package police.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import police.model.repo.AccidentRepository;
import police.model.repo.impl.AccidentRepositoryJdbcImpl;
import police.model.repo.impl.AccidentRepositoryMemoryImpl;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "police")
@PropertySource("classpath:app.properties")
public class WebConfig {

    @Bean
    ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public DataSource ds(@Value("${jdbc.driver}") String driver,
                         @Value("${jdbc.url}") String url,
                         @Value("${jdbc.username}") String username,
                         @Value("${jdbc.password}") String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    @Bean
    public JdbcTemplate jdbc(DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "memRepo")
    AccidentRepository accidentRepository() {
        return new AccidentRepositoryMemoryImpl();
    }

    @Primary
    @Bean(name = "jdbcRepo")
    AccidentRepository accidentRepository(JdbcTemplate jdbc) {
        return new AccidentRepositoryJdbcImpl(jdbc);
    }
}
