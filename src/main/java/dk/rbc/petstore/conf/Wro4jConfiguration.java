package dk.rbc.petstore.conf;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import ro.isdc.wro.http.ConfigurableWroFilter;

/**
 * Configures the needed wro4j filter.
 * @see http://instea.sk/2015/08/spring-boot-configuration-of-wro4j/
 * 
 * @author daaa
 *
 */
@Configuration
public class Wro4jConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(Wro4jConfiguration.class);
    
    
    /**
     * Reads the wro.properties file to configure the wro
     * 
     * @return
     */
    private Properties readProperties() {
        final Properties properties = new Properties();
        try (final InputStream stream = this.getClass().getResourceAsStream("/wro.properties")) {
            if(stream == null) throw new IOException("Null stream");
            properties.load(stream);
        } catch (IOException e) {
            LOGGER.error("Cannot read wro.properties", e);
        }
        
        return properties;
    }
    
    /**
     * wro filter registration
     * 
     * @param env the Spring Environment
     * @return the filter registration
     */
    @Bean
    FilterRegistrationBean webResourceOptimizer(Environment env) {
        FilterRegistrationBean fr = new FilterRegistrationBean();
        ConfigurableWroFilter filter = new ConfigurableWroFilter();
        //Properties props = buildWroProperties(env);
        Properties props = readProperties();
        filter.setProperties(props);
        filter.setWroManagerFactory(new Wro4jCustomXmlModelManagerFactory(props));
        fr.setFilter(filter);
        fr.addUrlPatterns("/wro/*");
        return fr;
    }

//    private static final String[] OTHER_WRO_PROP = new String[] {
//            ConfigurableProcessorsFactory.PARAM_PRE_PROCESSORS,
//            ConfigurableProcessorsFactory.PARAM_POST_PROCESSORS };
//
//    private Properties buildWroProperties(Environment env) {
//        Properties prop = new Properties();
//        for (ConfigConstants c : ConfigConstants.values()) {
//            addProperty(env, prop, c.name());
//        }
//        for (String name : OTHER_WRO_PROP) {
//            addProperty(env, prop, name);
//        }
//        LOGGER.debug("WRO4J properties {}", prop);
//        return prop;
//    }
//
//    private void addProperty(Environment env, Properties to, String name) {
//        String value = env.getProperty("wro." + name);
//        if (value != null) {
//            to.put(name, value);
//        }
//    }
}
