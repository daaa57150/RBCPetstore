package dk.rbc.petstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.persistence.repositories.CategoryRepo;
import dk.rbc.petstore.service.CategoryService;

/**
 * Service to manipulate Categories 
 * 
 * @author daaa
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /** The categories repo */
    @Autowired
    private CategoryRepo repo;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Category createCategoryWithName(String name) {
        Category category = new Category(name);
        repo.save(category);
        return category;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Category findCategoryByName(String name) {
        return repo.findOneByName(name);
    }

    
    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Iterable<Category> findAllCategories() {
        return repo.findAll();
    }

}
