package dev.c20.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scripting.groovy.GroovyScriptFactory;
import org.springframework.scripting.support.ScriptFactoryPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@Configuration
@EnableWebMvc
//@ComponentScan("dev.c20.workflow")
//@EnableAutoConfiguration
@EnableJpaRepositories("dev.c20.workflow")
//@EntityScan(basePackages= "dev.c20.workflow")
@EnableTransactionManagement
@EnableAspectJAutoProxy

public class WorkflowApplication {

    static public final String DB_PREFIX ="C20_";
    static public final String HEADER_USER_NAME = "Authentication";
    static public final String HEADER_AUTHORIZATION = "Authorization";
    static public final String HEADER_AUTHORIZATION_TOKEN = "token ";
    static public final String TOKEN_KEY = "856war98mq7qE9NADH";
    static public final String FILE_KEY = "Ch1sasComoAsiSiJalaCon una LLave mas laraga";
    static public final String[] SERVICES_WITHOUT_AUTH = { "authentication" };
    static public final String WORKFLOWS_PATH = "/Workflows";
    // un mes
    static public final long TOKEN_MINUTES_VALID = 43200;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        logger.info("Set EntityManager to MySQL:" + Database.MYSQL);
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(this.getClass().getPackage().getName());
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        logger.info("Set DataSource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        logger.info("Set hibernate props");
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.setProperty("hibernate.current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        properties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));

        /*
        properties.setProperty("hibernate.search.default.directory_provider", JdbcDirectory.class.getName());
        properties.setProperty("hibernate.search.generate_statistics",env.getProperty("spring.jpa.properties.hibernate.search.generate_statistics"));
        properties.setProperty("hibernate.search.lucene_version",env.getProperty("spring.jpa.properties.hibernate.search.lucene_version"));
        properties.setProperty("hibernate.search.default.indexBase",env.getProperty("spring.jpa.properties.hibernate.search.default.indexBase"));
        */
        return properties;
    }

    @Bean
    ScriptFactoryPostProcessor scriptFactory() {
        return new ScriptFactoryPostProcessor();
    }

    public static void main2(String[] args) {

        SpringApplication.run(WorkflowApplication.class, args);
    }

    public static void main(String[] args ) {
        SpringApplication app = new SpringApplication(WorkflowApplication.class);
        // Add GroovyScriptFactory after Application is prepared...
        app.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent applicationEvent) {

                if( !( applicationEvent instanceof ContextRefreshedEvent) ) {
                    return;
                }
                ApplicationContext applicationContext = ((ContextRefreshedEvent) applicationEvent).getApplicationContext();
                AutowireCapableBeanFactory registry = applicationContext.getAutowireCapableBeanFactory();
                BeanDefinitionBuilder bd = BeanDefinitionBuilder.genericBeanDefinition(GroovyScriptFactory.class);
                bd.addConstructorArgValue("file:E:\\develop\\projects\\TaskServer\\workflow\\groovys\\HolaSpring.groovy");
                bd.getBeanDefinition().
                    setAttribute(ScriptFactoryPostProcessor.REFRESH_CHECK_DELAY_ATTRIBUTE, 1000);

                GenericApplicationContext context = new GenericApplicationContext();
                context.setParent(applicationContext);


                System.out.println(bd.getBeanDefinition());
                context.registerBeanDefinition("holaSpring", bd.getBeanDefinition());
            }

        });
        app.run(args);
    }

}
