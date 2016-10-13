package dk.rbc.petstore.persistence.dao;

import dk.rbc.petstore.domain.entities.Category;

/**
 * Gives access to the pet categories in the DB
 * 
 * @author daaa
 */
public interface CategoryDao {

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

    /**
     * Get all categories from the DB
     * @return
     */
    Iterable<Category> findAllCategories();
}
