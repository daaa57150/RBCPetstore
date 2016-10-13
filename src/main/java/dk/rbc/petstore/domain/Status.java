package dk.rbc.petstore.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a pet's status
 * 
 * @author daaa
 */
@Entity
public class Status implements Serializable{

    /** Serializable class version uid */
    private static final long serialVersionUID = 7880080832445334174L;

    /** The id */
    @Id
    @GeneratedValue
    private Long id;
    
    /** The name of the status */
    @Column(nullable = false, unique = true)
    private String name;

    
    /** Default constructor, needed by JPA */
    public Status() {
        super();
    }

    /**
     * Constructor
     * @param name the status' name
     */
    public Status(String name) {
        super();
        this.name = name;
    }

    
    /** @return the id */
    public Long getId() {
        return id;
    }

    /** @param id the id to set */
    public void setId(Long id) {
        this.id = id;
    }

    /** * @return the name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /** (@inheritDoc) */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
