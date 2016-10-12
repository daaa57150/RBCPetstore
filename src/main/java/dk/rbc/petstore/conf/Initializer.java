package dk.rbc.petstore.conf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.rbc.petstore.dao.PetRepository;
import dk.rbc.petstore.domain.Status;

@Component
public class Initializer {

    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Initializer.class);

    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private InitializerService initService;
    
    @Autowired
    private PetRepository petRepo;
    
    /**
     * Inserts the demo data in the DB when the application launches
     */
    @PostConstruct
    public void initData() {
        LOGGER.info("Inserting data...");
        
        Status statusAvailable = initService.createStatusWithName("available");
        Status statusPending = initService.createStatusWithName("pending");
        Status statusSold = initService.createStatusWithName("sold");
        
        
        LOGGER.debug("Data inserted.");
    }
    
    
    
    
    @PostConstruct
    public void testEntityManager() {
        System.out.println("POST CONSTRUCT!!");
        
        System.out.println("statuses:");
        List<Status> statuses = entityManager.createQuery("Select s from Status s", Status.class).getResultList();
        System.out.println(statuses);
        
        System.out.println("find pets");
        System.out.println(petRepo.findAll());
        
        
    }
    
    
}
