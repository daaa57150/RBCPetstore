package dk.rbc.petstore.dao;

import org.springframework.data.repository.CrudRepository;

import dk.rbc.petstore.domain.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {

    
}
