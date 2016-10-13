package dk.rbc.petstore.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import dk.rbc.petstore.domain.Status;

/**
 * Spring Data JPA repository to access statuses in the DB 
 * 
 * @author daaa
 */
public interface StatusRepo extends CrudRepository<Status, Long> { 

}
