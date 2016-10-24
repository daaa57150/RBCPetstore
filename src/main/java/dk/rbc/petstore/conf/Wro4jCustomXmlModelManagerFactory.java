package dk.rbc.petstore.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.factory.XmlModelFactory;


/**
 * wro manager factory
 * @see http://instea.sk/2015/08/spring-boot-configuration-of-wro4j/
 * 
 * @author daaa
 */
public class Wro4jCustomXmlModelManagerFactory extends ConfigurableWroManagerFactory {
    
    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Wro4jCustomXmlModelManagerFactory.class);
    
    /** The properties for the wro */
    final private Properties props;

    /**
     * Constructor
     * @param props the properties for the wro
     */
    public Wro4jCustomXmlModelManagerFactory(Properties props) {
        this.props = props;
    }
    
    /** {@inheritDoc} */
    @Override
    protected Properties newConfigProperties() {
        return props;
    }

    /** {@inheritDoc} */
    @Override
    protected WroModelFactory newModelFactory() {
        LOGGER.debug("loading from /wro.xml");
        return new XmlModelFactory() {
            @Override
            protected InputStream getModelResourceAsStream() throws IOException {
                String resourceLocation = "/wro.xml";
                LOGGER.info("Loading resource {}", resourceLocation);
                final InputStream stream = getClass().getResourceAsStream(resourceLocation);

                if (stream == null) {
                    throw new IOException("Invalid resource requested: " + resourceLocation);
                }

                return stream;
            }
        };
    }

}
