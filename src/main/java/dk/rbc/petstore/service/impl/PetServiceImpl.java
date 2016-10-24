package dk.rbc.petstore.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.domain.enums.Status;
import dk.rbc.petstore.persistence.PersistenceException;
import dk.rbc.petstore.persistence.repositories.PetRepo;
import dk.rbc.petstore.service.CategoryService;
import dk.rbc.petstore.service.PetService;

/**
 * Service to manipulate Pets
 * 
 * @author daaa
 */
@Service
public class PetServiceImpl implements PetService {

    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PetServiceImpl.class);
    
    /** The pet repository */
    @Autowired
    private PetRepo repo;
    
    /** Category Service */
    @Autowired
    private CategoryService categoryService;
    
    /** {@inheritDoc} */
    @Override
    @Transactional
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
    @Transactional
    public Pet createPet(String name, String category) {
        Pet pet = new Pet();
        Category categoryObj = categoryService.findCategoryByName(category);
        if(categoryObj == null) {
            throw new PersistenceException("The category " + category + " does not exist");
        }
        
        pet.setName(name);
        pet.setCategory(categoryObj);
        pet.setStatus(Status.AVAILABLE);
        
        return repo.save(pet);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(Long id) {
        return repo.findOne(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public boolean deletePetById(Long id) {
        boolean exists = repo.exists(id);
        if(!exists) {
            return false;
        }
        
        repo.delete(id);
        return true;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Iterable<Pet> findAllPets() {
        return repo.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Iterable<Pet> findPetByStatus(Collection<Status> statuses) {
        return repo.findByStatusIn(statuses);
    }

}
