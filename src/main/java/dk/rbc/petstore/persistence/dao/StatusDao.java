package dk.rbc.petstore.persistence.dao;

import dk.rbc.petstore.domain.Status;
import dk.rbc.petstore.domain.StatusEnum;

/**
 * Gives access to the Statuses in the DB
 * 
 * @author daaa
 */
public interface StatusDao {

    /**
     * Creates a status with the given name
     * 
     * @param name the name of the status to create
     * @return the created status
     */
    Status createStatusWithName(String name);
    
    /**
     * Finds the status with the given name
     * @param name the name of the status to look for
     * @return the status with the given name if found; null otherwise
     */
    Status findStatusByName(String name);
    
    /**
     * Finds the corresponding status 
     * @param status the status to look for
     * @return the corresponding status if found; null otherwise
     */
    Status findStatus(StatusEnum status);

    /**
     * Get all statuses from the DB
     * @return
     */
    Iterable<Status> findAllStatuses();
}
