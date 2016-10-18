package dk.rbc.petstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller serving all the views for the demo
 * 
 * @author daaa
 */
@Controller
@RequestMapping("template")
public class TemplateController {

    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);
    
//    /**
//     * Serves the index view
//     * @return index.html
//     * @throws IOException 
//     */
//    @RequestMapping({"/"})
//    public void index(HttpServletResponse response) 
//    throws IOException {
//        LOGGER.debug("redirection to: index.html");
//        response.sendRedirect("/index.html");
//    }
    
    /**
     * Test thymeleaf  
     * 
     * @param model
     * @param name
     * @return
     */
    @RequestMapping("hello")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        LOGGER.debug("Thymeleaf template hello with name " + name);
        return "hello";
    }
    
    
    /**
     * Main page template
     * 
     * @return
     */
    @RequestMapping("petList")
    public String templateIndex() {
        LOGGER.debug("Thymeleaf template petList");
        return "pets/petList :: main";
    }
    
}














