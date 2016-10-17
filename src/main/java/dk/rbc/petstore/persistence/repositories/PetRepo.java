package dk.rbc.petstore.persistence.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.domain.enums.Status;

/**
 * Spring Data JPA repository to access pets in the DB
 * 
 * @author daaa
 */
// interesting read on repo types: 
// http://stackoverflow.com/questions/14014086/what-is-difference-between-crudrepository-and-jparepository-interfaces-in-spring
public interface PetRepo extends CrudRepository<Pet, Long> {
    
    Collection<Pet> findByStatusIn(Collection<Status> statuses);
}
