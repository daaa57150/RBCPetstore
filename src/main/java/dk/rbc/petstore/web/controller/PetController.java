package dk.rbc.petstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.service.CategoryService;
import dk.rbc.petstore.service.PetService;
import dk.rbc.petstore.web.Response;

/**
 * Controller for pets
 * 
 * @author daaa
 */
@Controller
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
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
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
}
