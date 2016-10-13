package dk.rbc.petstore;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.rbc.petstore.persistence.dao.CategoryDao;
import dk.rbc.petstore.service.impl.InitializerServiceImpl;

/**
 * Initializes the app when it starts.
 * 
 * @author daaa
 */
@Component
public class Initializer {
    
    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Initializer.class);

    /** Service to initialize the data for the demo */
    @Autowired
    private InitializerServiceImpl initService;
    
    /** Direct access to the DB statuses */
    @Autowired
    private CategoryDao categoryDao;
    
    
    /** Inserts the demo data in the DB when the application launches */
    @PostConstruct
    public void init() {
        LOGGER.info("Inserting data...");
        
        LOGGER.debug("# categories...");
        initService.createCategories();
        
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Inserted categories: ");
            categoryDao.findAllCategories().forEach(category -> {
                LOGGER.debug(category.toString());
            });
        }
        
        LOGGER.debug("# pets...");
        // TODO: insert pets
        
        LOGGER.info("Data inserted.");
    }
    
    
    
    /*
    @PostConstruct
    public void testEntityManager() {
        System.out.println("POST CONSTRUCT!!");
        
        System.out.println("statuses:");
        List<Status> statuses = entityManager.createQuery("Select s from Status s", Status.class).getResultList();
        System.out.println(statuses);
        
        System.out.println("find pets");
        System.out.println(petRepo.findAll());
        
        
    }
    */
    
}
