package dk.rbc.petstore.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.domain.enums.Status;
import dk.rbc.petstore.service.CategoryService;
import dk.rbc.petstore.service.InitializerService;
import dk.rbc.petstore.service.PetService;

/**
 * Creates the needed data for the demo
 * 
 * @author daaa
 */
@Service
public class InitializerServiceImpl implements InitializerService {

    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializerServiceImpl.class);
            
    /** Category Service */
    @Autowired
    private CategoryService categoryService;
    
    /** Pet Service */
    @Autowired
    private PetService petService;
    
    /** Initial categories to inject, read from the file initial-categories.txt */
    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString(" +
                "T(org.springframework.util.ResourceUtils).getFile('classpath:initial-categories.txt')" +
            ")}")
    private String initialCategories;
    
    /** Initial pets to inject, read from the file initial-pets.txt */
    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString(" +
            "T(org.springframework.util.ResourceUtils).getFile('classpath:initial-pets.txt')" +
        ")}")
    private String initialPets;
    


    /**
     * Creates the initial (and only?) categories from the file initial-categories.txt
     */
    @Override
    @Transactional
    public void createCategories() {
        for(String category: initialCategories.split("\\W+")) {
            category = category.trim();
            if(!StringUtils.isEmpty(category) && !category.startsWith("#")) {
                categoryService.createCategoryWithName(category);
            }
        }
    }
    

    /**
     * Creates the initial pets from the file initial-pets.txt
     */
    @Override
    @Transactional
    public void createPets() {
        for(String line: initialPets.split("[\\r\\n]")) {
            line = line.trim();
            if(!StringUtils.isEmpty(line) && !line.startsWith("#")) {
                String[] parts = line.split("\\W+");
                if(parts.length == 3) {
                    Category category = categoryService.findCategoryByName(parts[1]);
                    if(category == null) {
                        LOGGER.error("Cannot create a pet with category " + parts[1] + " because it doesn't exist");
                    }
                    else {
                        Status status = Status.valueOf(parts[2].toUpperCase());
                        Pet pet = new Pet(parts[0], category);
                        pet.setStatus(status);
                        petService.createPet(pet);
                    }
                }
                else {
                    LOGGER.error("Cannot create a pet with this line: " + line);
                }
            }
        }
    }
    
    
    
}
