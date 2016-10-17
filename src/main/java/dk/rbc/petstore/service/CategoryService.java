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
    
    /**
     * Creates a category with the given name
     * 
     * @param name the name of the category to create
     * @return the created category
     */
    Category createCategoryWithName(String name);
    
    /**
     * Finds the category with the given name
     * @param name the name of the category to look for
     * @return the category with the given name if found; null otherwise
     */
    Category findCategoryByName(String name);
}
