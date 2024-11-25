package com.team5.bms.model;

import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* This class represents the domain object model for a Building.
*
* @author Leandro "Project Manager, Software Development Engineer" Mananquil
* @author Jasper Belenzo
* 
*/
@Entity
@Table(name = "buildings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Building implements Serializable {
	
	   private static final long serialVersionUID = 1L;
	   
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "id")
	   private Long id;
	 
	   @NotNull
	   @Column(name = "buildingName", nullable = false)
	   private String buildingName;
	 
	   @NotNull
	   @Column(name = "address", nullable = false)
	   private String address;
	 
	   @NotNull
	   @Column(name = "buildingPhone", nullable = false)
	   private String buildingPhone;
	 
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
	 
	   public String getBuildingName() {
	       return this.buildingName;
	   }
	 
	   public Building buildingName(String buildingName) {
	       this.setBuildingName(buildingName);
	       return this;
	   }
	 
	   public void setBuildingName(String buildingName) {
	       this.buildingName = buildingName;
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
	 
	   public String getBuildingPhone() {
	       return this.buildingPhone;
	   }
	 
	   public Building buildingPhone(String buildingPhone) {
	       this.setBuildingPhone(buildingPhone);
	       return this;
	   }
	 
	   public void setBuildingPhone(String buildingPhone) {
	       this.buildingPhone = buildingPhone;
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
	           ", buildingName='" + getBuildingName() + "'" +
	           ", address='" + getAddress() + "'" +
	           ", buildingPhone='" + getBuildingPhone() + "'" +
	           "}";
	   }	

}
