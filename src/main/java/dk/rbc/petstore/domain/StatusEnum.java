package dk.rbc.petstore.domain;

/**
 * The possible statuses for a pet
 * 
 * @author daaa
 */
public enum StatusEnum {
    
    /** Pet is available to be bought */
    AVAILABLE, 
    
    /** Pet is being sold to someone */
    PENDING, 
    
    /** Pet is sold, sorry */
    SOLD
}
