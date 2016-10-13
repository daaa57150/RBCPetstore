package dk.rbc.petstore.service;

import dk.rbc.petstore.domain.entities.Pet;

/**
 * Service to manipulate Pets
 * 
 * @author daaa
 */
public interface PetService {
    /**
     * Creates a Pet
     * @param pet the pet to create, serves as a model and shouldn't be touched by the implementations.
     * @return the created pet, which is not the same instance as the parameter
     */
    Pet createPet(Pet pet);
    
    /**
     * Creates a pet with the given name and category, with default values for the rest (status: available)
     * @param name the name of the pet
     * @param category the category of the pet
     * @return the newly created pet
     */
    Pet createPet(String name, String category);
    
    /**
     * Finds a pet by its id
     * 
     * @param id the id of the pet to find
     * @return the pet with the given id if it exists, null otherwise
     */
    Pet findPetById(Long id);
    
    
    /**
     * Deletes a pet by its id
     * @param id the id of the pet to delete
     * @return true if a pet was deleted, false if the pet with the given id didn't exist
     */
    boolean deletePetById(Long id);
}
