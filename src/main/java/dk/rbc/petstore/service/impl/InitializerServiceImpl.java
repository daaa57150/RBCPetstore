package dk.rbc.petstore.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.rbc.petstore.persistence.dao.CategoryDao;
import dk.rbc.petstore.service.InitializerService;

/**
 * Creates the needed data for the demo
 * 
 * @author daaa
 */
@Service
public class InitializerServiceImpl implements InitializerService {
   
    /** Status DAO */
    @Autowired
    private CategoryDao categoryDao;
    
    /** The categories inserted by this service when calling createCategories */
    public static final String[] INITIAL_CATEGORIES = new String[] {
        "dog", "cat", "snake"
    };

    /**
     * Creates the categories from {@link INITIAL_CATEGORIES}:
     */
    @Override
    @Transactional
    public void createCategories() {
        for(String category: INITIAL_CATEGORIES) {
            categoryDao.createCategoryWithName(category);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public String[] getInitialCategoryNames() {
        return INITIAL_CATEGORIES;
    }

    /**
     * TODO: comment me
     */
    @Override
    public void createPets() {
        // TODO Create pets!
    }
    
    
    
}
