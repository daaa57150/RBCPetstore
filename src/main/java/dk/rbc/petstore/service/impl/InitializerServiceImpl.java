package dk.rbc.petstore.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.rbc.petstore.persistence.dao.StatusDao;

/**
 * Creates the needed data for the demo
 * 
 * @author daaa
 */
@Service
public class InitializerServiceImpl implements InitializerService {
   
    /** Status DAO */
    @Autowired
    private StatusDao statusDao;

    /**
     * Creates the following statuses:
     *  - available
     *  - pending
     *  - sold
     */
    @Override
    @Transactional
    public void createStatuses() {
        statusDao.createStatusWithName("available");
        statusDao.createStatusWithName("pending");
        statusDao.createStatusWithName("sold");
    }

    /**
     * TODO: comment me
     */
    @Override
    public void createPets() {
        // TODO Create pets!
    }
    
}
