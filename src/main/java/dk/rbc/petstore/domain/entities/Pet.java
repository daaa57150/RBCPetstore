package dk.rbc.petstore.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import dk.rbc.petstore.domain.enums.Status;
import dk.rbc.petstore.service.impl.InitializerServiceImpl;

/**
 * Represents a Pet
 * 
 * @author daaa
 */
@Entity
public class Pet implements Serializable {
    
    /** Serializable class version uid */
    private static final long serialVersionUID = -529670939573880286L;

    /** The id */
    @Id
    @GeneratedValue
    private Long id;
    
    /** The name of the pet */
    @Column(nullable = false)
    private String name;
    
    /** A list of photo urls */
    @Column(length = 65535, nullable = true)
    private ArrayList<String> photoUrls;
    
    /** Category of the pet, see {@link InitializerServiceImpl} for starting values available */
    @ManyToOne(optional = false)
    private Category category;
    
    /** Status of the pet */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.AVAILABLE; // defaults to available
    
    
    // === CONSTRUCTORS === //
    
    /** Default constructor, needed by JPA */
    public Pet() {
        super();
    }

    /**
     * Constructor
     * @param name the pet's name
     * @param category the pet's category
     */
    public Pet(String name, Category category) {
        super();
        this.name = name;
        this.category = category;
    }
    
    /**
     * Constructor
     * @param name the pet's name
     * @param category the pet's category
     * @param status the pet's status
     */
    public Pet(String name, Category category, Status status) {
        super();
        this.name = name;
        this.category = category;
        this.status = status;
    }
    
    
    // === GETTERS AND SETTERS === //

    /** @return the id */
    public Long getId() {
        return id;
    }

    /** @param id the id to set */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the photoUrls */
    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    /** @param photoUrls the photoUrls to set */
    public void setPhotoUrls(ArrayList<String> photoUrls) {
        if(photoUrls == null) {
            this.photoUrls = null;
        }
        else {
            this.photoUrls = new ArrayList<String>(photoUrls.size());
            this.photoUrls.addAll(photoUrls);
        }
    }
    
    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param status the status to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    
    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    
    
    
    // === TOSTRING ETC === //

    /** (@inheritDoc) */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}









