package org.jvnet.hyperjaxb3.ejb.tests.issues.pre;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.xml.bind.annotation.XmlElement;



@MappedSuperclass
public abstract class IssueHJIII45SuperClass implements Serializable{

    private static final long serialVersionUID = 7724857660567518243L;

	
    @XmlElement(name = "Id")
    @Id	
    @Column(name = "Id", updatable = false, nullable = false)
    private String id = "";
    

    public String getId() {
		return this.id;
	}
	
    
    private void setId(String uuid) {
		this.id = uuid;
	}
    
	@PrePersist
	private void prePersist() {
		if (getId().trim().length() == 0) {
			setId(UUID.randomUUID().toString());
		}
	}
	
	
	
	


}

