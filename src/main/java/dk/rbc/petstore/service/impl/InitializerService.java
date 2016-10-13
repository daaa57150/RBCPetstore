package dk.rbc.petstore.service.impl;

/**
 * Exposes all needed methods to populate the application when it starts
 * with demo data.
 * 
 * @author daaa
 */
public interface InitializerService {
    
    /** Should create the pet statuses needed for the demo */
    void createStatuses();
    
    /** Should create pets for the demo */
    void createPets();
}
