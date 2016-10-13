package dk.rbc.petstore.persistence.dao.impl;

import java.lang.reflect.InvocationTargetException;

import dk.rbc.petstore.persistence.PersistenceException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.persistence.dao.CategoryDao;
import dk.rbc.petstore.persistence.dao.PetDao;
import dk.rbc.petstore.persistence.repositories.PetRepo;

/**
 * Implementation of the Pet Dao using Spring Data JPA.
 * 
 * @author daaa
 */
@Repository
public class PetDaoImpl implements PetDao{

    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PetDaoImpl.class);
    
    /** The pet repository */
    @Autowired
    private PetRepo repo;
    
    /** The category dao */
    @Autowired
    private CategoryDao categoryDao;
    
    
    /** {@inheritDoc} */
    @Override
    public Pet createPet(Pet pet) {
        
        // we clone the given pet because if the pet is already managed by JPA (has an id),
        // repo.save() would update it, and that's not what we want, we want to create one from 
        // the parameter which is essentially a model
        try {
            Pet newPet = (Pet)BeanUtils.cloneBean(pet);
            newPet.setId(null);
            
            return repo.save(newPet);
            
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            LOGGER.error("Cannot clone pet", e);
            throw new PersistenceException("Cannot clone pet", e);
        }
        
    }

    /** {@inheritDoc} */
    @Override
    public Pet createPet(String name, String category) {
        Pet pet = new Pet();
        Category categoryObj = categoryDao.findCategoryByName(category);
        if(categoryObj == null) {
            throw new PersistenceException("The category " + category + " does not exist");
        }
        
        return pet;
    }

    /** {@inheritDoc} */
    @Override
    public Pet findPetById(Long id) {
        return repo.findOne(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean deletePetById(Long id) {
        boolean exists = repo.exists(id);
        if(!exists) {
            return false;
        }
        
        repo.delete(id);
        return true;
    }
    
}






