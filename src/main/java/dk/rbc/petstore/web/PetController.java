package dk.rbc.petstore.web;

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
     * @param pet
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createPet(@RequestBody Pet pet) {
        //TODO: create the pet
        LOGGER.debug("TODO: Create this pet: ");
        LOGGER.debug(pet.toString());
        
        return "TODO!";
    }
}
