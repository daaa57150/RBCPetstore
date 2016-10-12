package dk.rbc.petstore.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Pet implements Serializable {
    
    /** Serializable class version uid */
    private static final long serialVersionUID = -529670939573880286L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 65535, nullable=true)
    private ArrayList<String> photoUrls;
    
    
    
    
    
    //TODO: test if setId is needed
    
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
    public void setPhotoUrls(List<String> photoUrls) {
        if(photoUrls == null) {
            this.photoUrls = null;
        }
        else {
            this.photoUrls = new ArrayList<String>(photoUrls.size());
            this.photoUrls.addAll(photoUrls);
        }
    }
    
    
    
    /** (@inheritDoc) */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}









