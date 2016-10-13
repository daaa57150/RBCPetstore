package dk.rbc.petstore.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.rbc.petstore.domain.StatusEnum;
import dk.rbc.petstore.persistence.dao.StatusDao;
import dk.rbc.petstore.service.InitializerService;

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
     * Creates the statuses from {@link StatusEnum}:
     */
    @Override
    @Transactional
    public void createStatuses() {
        for(StatusEnum status : StatusEnum.values()) {
            statusDao.createStatusWithName(status.name().toLowerCase());
        }
    }

    /**
     * TODO: comment me
     */
    @Override
    public void createPets() {
        // TODO Create pets!
    }
    
}
