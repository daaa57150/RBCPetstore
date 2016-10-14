package dk.rbc.petstore;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.rbc.petstore.service.CategoryService;
import dk.rbc.petstore.service.PetService;
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
    
    /** Access to the DB categories */
    @Autowired
    private CategoryService categoryService;
    
    /** Access to the DB pets */
    @Autowired
    private PetService petService;
    
    
    /** Inserts the demo data in the DB when the application launches */
    @PostConstruct
    public void init() {
        LOGGER.info("Inserting data...");
        
        LOGGER.debug("# categories...");
        initService.createCategories();
        
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Inserted categories: ");
            categoryService.findAllCategories().forEach(category -> {
                LOGGER.debug(category.toString());
            });
        }
        
        LOGGER.debug("# pets...");
        initService.createPets();
        
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Inserted pets: ");
            petService.findAllPets().forEach(pet -> {
                LOGGER.debug(pet.toString());
            });
        }
        
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
