package org.jvnet.hyperjaxb3.hibernate.tests.hhh4384;

import javax.persistence.AssociationOverride;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class D {

	private String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private E e;

	@Embedded
	@AssociationOverride(name = "f"/*, joinColumns = { @JoinColumn(name = "E_F") }*/)
	public E getE() {
		return e;
	}

	public void setE(E e) {
		this.e = e;
	}
}