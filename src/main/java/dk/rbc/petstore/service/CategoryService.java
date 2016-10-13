package dk.rbc.petstore.service;

import dk.rbc.petstore.domain.entities.Category;

/**
 * Service to manipulate Categories
 * 
 * @author daaa
 */
public interface CategoryService {
    
    /**
     * Get all the currently available categories
     * 
     * @return the available categories
     */
    Iterable<Category> findAllCategories();
}
