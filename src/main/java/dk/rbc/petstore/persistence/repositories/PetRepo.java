package dk.rbc.petstore.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import dk.rbc.petstore.domain.Pet;

/**
 * Spring Data JPA repository to access pets in the DB
 * 
 * @author daaa
 */
public interface PetRepo extends CrudRepository<Pet, Long> {

}
