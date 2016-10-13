package dk.rbc.petstore.domain.enums;

/**
 * The possible statuses for a pet
 * 
 * @author daaa
 */
public enum Status {
    
    /** Pet is available to be bought */
    AVAILABLE, 
    
    /** Pet is being sold to someone */
    PENDING, 
    
    /** Pet is sold, sorry */
    SOLD
}
