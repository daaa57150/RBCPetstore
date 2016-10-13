package dk.rbc.petstore.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import dk.rbc.petstore.domain.entities.Category;

/**
 * Spring Data JPA repository to access statuses in the DB 
 * 
 * @author daaa
 */
public interface CategoryRepo extends CrudRepository<Category, Long> { 
    
    /**
     * Finds the status with the given name
     * 
     * @param name the name of the status to look for
     * @return the status with the given name; null otherwise
     */
    Category findOneByName(String name);
}
