package dk.rbc.petstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.persistence.dao.CategoryDao;
import dk.rbc.petstore.service.CategoryService;

/**
 * Service to manipulate Categories 
 * 
 * @author daaa
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    // TODO: squeeze the DAO completely, move the code from the DAO to here
    /** Access to the data layer for categories */
    @Autowired
    private CategoryDao dao;
    
    
    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Iterable<Category> findAllCategories() {
        return dao.findAllCategories();
    }

}
