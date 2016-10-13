package dk.rbc.petstore.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import dk.rbc.petstore.domain.Status;

/**
 * Spring Data JPA repository to access statuses in the DB 
 * 
 * @author daaa
 */
public interface StatusRepo extends CrudRepository<Status, Long> { 
    
    /**
     * Finds the status with the given name
     * 
     * @param name the name of the status to look for
     * @return the status with the given name; null otherwise
     */
    Status findOneByName(String name);
}
