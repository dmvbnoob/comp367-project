package com.team5.bms.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/**
* This class represents the domain object model for a Building.
*
* @author Leandro "Project Manager, Software Development Engineer" Mananquil
*/

public class Building implements Serializable{
	
	   private static final long serialVersionUID = 1L;
	   
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "id")
	   private Long id;
	 
	   @NotNull
	   @Column(name = "name", nullable = false)
	   private String name;
	 
	   @NotNull
	   @Column(name = "address", nullable = false)
	   private String address;
	 
	   @NotNull
	   @Column(name = "phone", nullable = false)
	   private String phone;
	 
	   public Long getId() {
	       return this.id;
	   }
	 
	   public Building id(Long id) {
	       this.setId(id);
	       return this;
	   }
	 
	   public void setId(Long id) {
	       this.id = id;
	   }
	 
	   public String getName() {
	       return this.name;
	   }
	 
	   public Building name(String name) {
	       this.setName(name);
	       return this;
	   }
	 
	   public void setName(String name) {
	       this.name = name;
	   }
	 
	   public String getAddress() {
	       return this.address;
	   }
	 
	   public Building address(String address) {
	       this.setAddress(address);
	       return this;
	   }
	 
	   public void setAddress(String address) {
	       this.address = address;
	   }
	 
	   public String getPhone() {
	       return this.phone;
	   }
	 
	   public Building phone(String phone) {
	       this.setPhone(phone);
	       return this;
	   }
	 
	   public void setPhone(String phone) {
	       this.phone = phone;
	   }
	 
	   @Override
	   public boolean equals(Object o) {
	       if (this == o) {
	           return true;
	       }
	       if (!(o instanceof Building)) {
	           return false;
	       }
	       return getId() != null && getId().equals(((Building) o).getId());
	   }
	 
	   @Override
	   public int hashCode() {
	       return getClass().hashCode();
	   }
	 
	   @Override
	   public String toString() {
	       return "Building{" +
	           "id=" + getId() +
	           ", name='" + getName() + "'" +
	           ", address='" + getAddress() + "'" +
	           ", phone='" + getPhone() + "'" +
	           "}";
	   }	

}
