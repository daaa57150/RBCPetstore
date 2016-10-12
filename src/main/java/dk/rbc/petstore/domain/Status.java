package dk.rbc.petstore.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Status implements Serializable{

    /** Serializable class version uid */
    private static final long serialVersionUID = 7880080832445334174L;

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
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

    
    
    
    //TODO: test if setId is needed
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
