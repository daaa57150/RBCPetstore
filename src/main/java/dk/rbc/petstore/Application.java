package dk.rbc.petstore;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

/**
 * Spring Boot application entry point
 * 
 * @author daaa
 */
@SpringBootApplication //means @Configuration, @EnableAutoConfiguration & @ComponentScan
@EntityScan(basePackages={"dk.rbc.petstore.domain"})
public class Application {
    
    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /** Main function, used by Spring boot to run */
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Let's inspect the beans provided by Spring Boot:");
    
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                LOGGER.debug(beanName);
            }
        }
    }

}




