package dk.rbc.petstore.service;

/**
 * Exposes all needed methods to populate the application when it starts
 * with demo data.
 * 
 * @author daaa
 */
public interface InitializerService {
    
    /** Get the names of the initial categories  */
    String[] getInitialCategoryNames();
    
    /** Should create the pet categories needed for the demo */
    void createCategories();
    
    /** Should create pets for the demo */
    void createPets();
}
