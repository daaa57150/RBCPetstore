package dk.rbc.petstore;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.rbc.petstore.domain.Status;
import dk.rbc.petstore.persistence.dao.StatusDao;
import dk.rbc.petstore.service.impl.InitializerServiceImpl;

@Component
public class Initializer {

    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Initializer.class);

    /** Service to initialize the data for the demo */
    @Autowired
    private InitializerServiceImpl initService;
    
    /** Direct access to the DB statuses */
    @Autowired
    private StatusDao statusDao;
    
    
    /** Inserts the demo data in the DB when the application launches */
    @PostConstruct
    public void init() {
        LOGGER.info("Inserting data...");
        
        LOGGER.debug("# statuses...");
        initService.createStatuses();
        
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Inserted statuses: ");
            statusDao.findAllStatuses().forEach(status -> {
                LOGGER.debug(status.toString());
            });
        }
        
        // test look for one status...
        Status available = statusDao.findStatusByName("available");
        Status none = statusDao.findStatusByName("plop");
        LOGGER.debug(available.toString());
        LOGGER.debug(none == null ? null : none.toString());
        
        
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
