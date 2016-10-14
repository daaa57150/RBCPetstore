package dk.rbc.petstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.domain.enums.Status;
import dk.rbc.petstore.service.CategoryService;
import dk.rbc.petstore.service.PetService;
import dk.rbc.petstore.web.Response;

/**
 * Rest controller for pets and everything related to it (ie: everything for the demo)
 * 
 * @author daaa
 */
@RestController // same as @Controller but adds @ResponseBody to all mappings
@RequestMapping("pet")
public class PetController {
    
    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PetController.class);
    
    /** The pet service */
    @Autowired
    private PetService petService;
    
    /** The category service */
    @Autowired
    private CategoryService categoryService;
    
    
    /**
     * Creates a pet 
     * @param pet the pet to create 
     * @return the response with the created pet
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Pet> createPet(@RequestBody Pet pet) {
        try {
            Pet created = petService.createPet(pet);
            
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Pet created: ");
                LOGGER.debug(created.toString());
            }
            
            return new Response<>(created);
        }
        catch(Exception e) {
            LOGGER.error("Could not create pet: " + pet, e);
            return new Response<>(e);
        }
    }
    
    /**
     * Searches for the pet with the given id
     * @param id the id of the pet to find
     * @return the response with the pet found
     */
    @RequestMapping(value = "{petId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Pet> findPetById(@PathVariable("petId") Long id) {
        try {
            Pet found = petService.findPetById(id);
            return new Response<>(found); // even if null, it's a result
        }
        catch(Exception e) {
            LOGGER.error("Could not find pet with id: " + id, e);
            return new Response<>(e);
        }
    }
    
    /**
     * Deletes a pet with the given id
     * @param id
     * @return the response with a boolean at true if a pet was effectively deleted
     */
    @RequestMapping(value = "{petId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> deletePet(@PathVariable("petId") Long id) {
        try {
            Boolean deleted = petService.deletePetById(id);
            return new Response<>(deleted);
        }
        catch(Exception e) {
            LOGGER.error("Could not delete pet with id: " + id, e);
            return new Response<>(e);
        }
    }
    
    /**
     * Lists all the pets in the DB. Mainly a debug tool.
     * @return the response with all the pets
     */
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Iterable<Pet>> listPets() {
        try {
            Iterable<Pet> pets = petService.findAllPets();
            return new Response<>(pets);
        }
        catch(Exception e) {
            LOGGER.error("Could not list pets", e);
            return new Response<>(e);
        }
    }
    
    /**
     * Lists all the pet categories
     * @return the response with all the pet categories
     */
    @RequestMapping(value = "categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Iterable<Category>> categories() {
        try {
            Iterable<Category> categories = categoryService.findAllCategories();
            return new Response<>(categories);
        }
        catch(Exception e) {
            LOGGER.error("Could not list categories", e);
            return new Response<>(e);
        }
    }
    
    
    /**
     * Lists all the pet statuses
     * @return the response with all the pet statuses
     */
    @RequestMapping(value = "statuses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Status[]> statuses() {
        try {
            Status[] statuses = Status.values();
            return new Response<>(statuses);
        }
        catch(Exception e) {
            LOGGER.error("Could not list statuses", e);
            return new Response<>(e);
        }
    }
}














