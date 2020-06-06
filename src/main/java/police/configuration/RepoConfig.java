package police.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import police.model.repo.AccidentRepository;
import police.model.repo.impl.AccidentRepositoryHibernateImpl;
import police.model.repo.impl.AccidentRepositoryJdbcImpl;
import police.model.repo.impl.AccidentRepositoryMemoryImpl;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "police")
@PropertySource("classpath:app.properties")
@EnableTransactionManagement
public class RepoConfig {
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

    @Bean
    public LocalSessionFactoryBean sessionFactory(@Value("${hibernate.dialect}") String dialect, DataSource ds) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(ds);
        sessionFactory.setPackagesToScan("police.model");
        Properties cfg = new Properties();
        cfg.setProperty("hibernate.dialect", dialect);
        sessionFactory.setHibernateProperties(cfg);
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager htx(SessionFactory sf) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sf);
        return tx;
    }

    @Bean(name = "memRepo")
    AccidentRepository accidentRepository() {
        return new AccidentRepositoryMemoryImpl();
    }

    @Bean(name = "jdbcRepo")
    AccidentRepository accidentRepository(JdbcTemplate jdbc) {
        return new AccidentRepositoryJdbcImpl(jdbc);
    }

    @Primary
    @Bean(name = "hibernateRepo")
    AccidentRepository hibernateRepository(SessionFactory sf) {
        return new AccidentRepositoryHibernateImpl(sf);
    }
}
