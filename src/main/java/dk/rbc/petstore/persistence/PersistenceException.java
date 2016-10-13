package dk.rbc.petstore.persistence;

import org.springframework.dao.DataAccessException;

/**
 * Used in the persistence layer
 * 
 * @author daaa
 */
public class PersistenceException extends DataAccessException {

    /** Serializable call version UID */
    private static final long serialVersionUID = 4118821652982671241L;

    /**
     * Constructor with message only
     * @param msg the error message
     */
    public PersistenceException(String msg) {
        super(msg);
    }
    
    /**
     * Constructor with message and cause
     * @param msg the error message
     * @param cause the cause
     */
    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
