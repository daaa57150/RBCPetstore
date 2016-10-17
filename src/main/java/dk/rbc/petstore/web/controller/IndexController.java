package dk.rbc.petstore.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller serving the index page
 * 
 * @author daaa
 */
@Controller
public class IndexController {

    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    
    /**
     * Serves the index view
     * @return index.html
     * @throws IOException 
     */
    @RequestMapping({"/", "index"})
    public String index(HttpServletResponse response) 
    throws IOException {
        LOGGER.debug("Resquesting index page");
        return "index";
    }
    
    
}














