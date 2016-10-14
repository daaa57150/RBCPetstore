package dk.rbc.petstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
   
    @Value("${test.name}")
    private String name;
    
    // Ca marche:
    @RequestMapping("/helloTest")
    public String index() {
        
        String msg = "Greetings from Spring Boot " + name + " !"; 
        LOGGER.debug(msg);
        
        return msg;
    }

}
