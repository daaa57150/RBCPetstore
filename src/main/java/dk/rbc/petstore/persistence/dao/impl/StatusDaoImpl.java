package dk.rbc.petstore.persistence.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dk.rbc.petstore.domain.Status;
import dk.rbc.petstore.persistence.dao.StatusDao;
import dk.rbc.petstore.persistence.repositories.StatusRepo;

/**
 * Implementation of the status Dao using Spring Data JPA.
 * 
 * @author daaa
 */
@Repository
public class StatusDaoImpl implements StatusDao {
   
    /** The status repo */
    @Autowired
    private StatusRepo repo;

    /** {@inheritDoc} */
    @Override
    public Status createStatusWithName(String name) {
        Status status = new Status(name);
        repo.save(status);
        return status;
    }

    /** {@inheritDoc} */
    @Override
    public Status findStatusByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /** {@inheritDoc} */
    @Override
    public Iterable<Status> findAllStatuses() {
        return repo.findAll();
    }
}
