package dk.rbc.petstore.persistence.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.persistence.dao.CategoryDao;
import dk.rbc.petstore.persistence.repositories.CategoryRepo;

/**
 * Implementation of the Category Dao using Spring Data JPA.
 * 
 * @author daaa
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {
   
    /** The categories repo */
    @Autowired
    private CategoryRepo repo;

    /** {@inheritDoc} */
    @Override
    public Category createCategoryWithName(String name) {
        Category category = new Category(name);
        repo.save(category);
        return category;
    }

    /** {@inheritDoc} */
    @Override
    public Category findCategoryByName(String name) {
        return repo.findOneByName(name);
    }

    
    /** {@inheritDoc} */
    @Override
    public Iterable<Category> findAllCategories() {
        return repo.findAll();
    }
}
