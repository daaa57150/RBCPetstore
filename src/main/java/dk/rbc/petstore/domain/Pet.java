package dk.rbc.petstore.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    @Column(length = 65535, nullable=true)
    private ArrayList<String> photoUrls;
    
    /** Status of the pet, see {@link StatusEnum} for available values */
    @OneToOne
    private Status status;
    
   
    
    

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

    
    
    
    /** (@inheritDoc) */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}









