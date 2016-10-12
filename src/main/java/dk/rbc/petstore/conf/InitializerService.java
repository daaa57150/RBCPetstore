package dk.rbc.petstore.conf;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.rbc.petstore.domain.Status;

@Service
public class InitializerService {

    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    public Status createStatusWithName(String name) {
        Status status = new Status(name);
        entityManager.persist(status);
        
        return status;
    }
    
}
