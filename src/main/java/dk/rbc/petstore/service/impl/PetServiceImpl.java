package dk.rbc.petstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.persistence.dao.PetDao;
import dk.rbc.petstore.service.PetService;

/**
 * Service to manipulate Pets
 * 
 * @author daaa
 */
@Service
public class PetServiceImpl implements PetService {

    // TODO: squeeze the DAO completely, move the code from the DAO to here
    /** Access to the data layer for pets */
    @Autowired
    private PetDao dao;
    
    /** {@inheritDoc} */
    @Override
    @Transactional
    public Pet createPet(Pet pet) {
        return dao.createPet(pet);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Pet createPet(String name, String category) {
        return dao.createPet(name, category);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(Long id) {
        return dao.findPetById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public boolean deletePetById(Long id) {
        return dao.deletePetById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Iterable<Pet> findAllPets() {
        return dao.findAllPets();
    }

}
