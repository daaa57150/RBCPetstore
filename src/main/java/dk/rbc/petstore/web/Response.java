package dk.rbc.petstore.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Response returned by the controllers, which can contain data and error messages. 
 * 
 * @author daaa
 *
 * @param <T> the type od data the response contains
 */
public class Response<T> {
    
    /** The data */
    private T data;
    
    /** An error message, marking the response as failed */
    private String errorMessage;
    
    /** An exception, marking the response as failed */
    private Exception exception;
    
    /**
     * @return true if the response is a success; ie no error is defined (message or exception)
     */
    public boolean isSuccess() {
        return exception == null && StringUtils.isEmpty(errorMessage); 
    }
    
    
    // === CONSTRUCTORS === //
    
    /**
     * Creates a response with data
     * @param data
     */
    public Response(T data) {
        this.data = data;
    }
    
    /**
     * Creates a failed response with an exception
     * @param exception
     */
    public Response(Exception exception) {
        this.exception = exception;
    }
    
    /**
     * Creates a failed response with en error message
     * @param errorMessage
     */
    public Response(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    /**
     * Creates a failed response with en error message and en exception
     * @param errorMessage
     * @param exception
     */
    public Response(String errorMessage, Exception exception) {
        this.exception = exception;
        this.errorMessage = errorMessage;
    }
    
    // === GETTERS AND SETTERS === //
    // Only getters here because users should not be able to create it with data + exception for example
    // Getters are interesting for testing purposes
    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @return the exception
     */
    public Exception getException() {
        return exception;
    }
    
    
    // === TOSTRING ETC === //
    
    /** (@inheritDoc) */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    
}
