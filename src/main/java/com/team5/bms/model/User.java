package com.team5.bms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String role;
    
    private String creditCardNumber;
    private String creditCardExpiry;
    private String creditCardCVV;
    private Long buildingId;
    private String buildingName;
    private String buildingAddress;
    private String buildingPhoe;
    private String unitNumber;
    
	public User(/*Long id,*/ String username, String firstname, String lastname, String email, String phone, String role,
			String creditCardNumber, String creditCardExpiry, String creditCardCVV, Long buildingId,
			String buildingName, String buildingAddress, String buildingPhoe, String unitNumber) {
		super();
		/* this.id = id; */
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.creditCardNumber = creditCardNumber;
		this.creditCardExpiry = creditCardExpiry;
		this.creditCardCVV = creditCardCVV;
		this.buildingId = buildingId;
		this.buildingName = buildingName;
		this.buildingAddress = buildingAddress;
		this.buildingPhoe = buildingPhoe;
		this.unitNumber = unitNumber;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardExpiry() {
		return creditCardExpiry;
	}

	public void setCreditCardExpiry(String creditCardExpiry) {
		this.creditCardExpiry = creditCardExpiry;
	}

	public String getCreditCardCVV() {
		return creditCardCVV;
	}

	public void setCreditCardCVV(String creditCardCVV) {
		this.creditCardCVV = creditCardCVV;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuildingAddress() {
		return buildingAddress;
	}

	public void setBuildingAddress(String buildingAddress) {
		this.buildingAddress = buildingAddress;
	}

	public String getBuildingPhoe() {
		return buildingPhoe;
	}

	public void setBuildingPhoe(String buildingPhoe) {
		this.buildingPhoe = buildingPhoe;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", email=" + email + ", phone=" + phone + ", role=" + role + ", creditCardNumber=" + creditCardNumber
				+ ", creditCardExpiry=" + creditCardExpiry + ", creditCardCVV=" + creditCardCVV + ", buildingId="
				+ buildingId + ", buildingName=" + buildingName + ", buildingAddress=" + buildingAddress
				+ ", buildingPhoe=" + buildingPhoe + ", unitNumber=" + unitNumber + "]";
	}
	
	
    
    
    
    
    
    
    
    
    
    
	

}
