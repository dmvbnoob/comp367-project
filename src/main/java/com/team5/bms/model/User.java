package com.team5.bms.model;
 
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team5.bms.model.enumeration.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.team5.bms.model.Request;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.JoinColumn;
 
/**
* This class represents the domain object model for all User types.
*
* @author Leandro "The Project Manager" Mananquil
* @author Jasper Belenzo
*/
@Entity
@Table(name="Users")
public class User {
	
    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //@Column(name = "buildingId")
    //private Long buildingId;
 
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;
 
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
 
    @Column(name = "firstname")
    private String firstname;
 
    @Column(name = "lastname")
    private String lastname;
 
    @Column(name = "email")
    private String email;
 
    @Column(name = "userPhone")
    private String userPhone;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;
 
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<Request> requests = new HashSet<>();
 
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<Card> cards = new HashSet<>();
 
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "building_id")
    @JsonManagedReference 
    private Building building;
 
    public Long getId() {
        return this.id;
    }
 
    public User id(Long id) {
        this.setId(id);
        return this;
    }
 
    public void setId(Long id) {
        this.id = id;
    }

    /*public Long getBuildingId() {
        return this.buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }*/
 
    public String getUsername() {
        return this.username;
    }
 
    public User username(String username) {
        this.setUsername(username);
        return this;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return this.password;
    }
 
    public User password(String password) {
        this.setPassword(password);
        return this;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getFirstname() {
        return this.firstname;
    }
 
    public User firstname(String firstname) {
        this.setFirstname(firstname); // Updated
        return this;
    }
 
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
 
    public String getLastname() {
        return this.lastname;
    }
 
    public User lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }
 
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
 
    public String getEmail() {
        return this.email;
    }
 
    public User email(String email) {
        this.setEmail(email);
        return this;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getUserPhone() {
        return this.userPhone;
    }
 
    public User userPhone(String userPhone) {
        this.setUserPhone(userPhone);
        return this;
    }
 
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
 
    public Roles getRole() {
        return this.role;
    }
 
    public User role(Roles role) {
        this.setRole(role);
        return this;
    }
 
    public void setRole(Roles role) {
        this.role = role;
    }
 
    public Set<Request> getRequests() {
        return this.requests;
    }
 
    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setUser(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setUser(this));
        }
        this.requests = requests;
    }
 
    public User requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }
 
    public User addRequest(Request request) {
        this.requests.add(request);
        request.setUser(this);
        return this;
    }
 
    public User removeRequest(Request request) {
        this.requests.remove(request);
        request.setUser(null);
        return this;
    }
 
    public Set<Card> getCards() {
        return this.cards;
    }
 
    public void setCards(Set<Card> cards) {
        if (this.cards != null) {
            this.cards.forEach(i -> i.setUser(null));
        }
        if (cards != null) {
            cards.forEach(i -> i.setUser(this));
        }
        this.cards = cards;
    }
 
    public User cards(Set<Card> cards) {
        this.setCards(cards);
        return this;
    }
 
    public User addCard(Card card) {
        this.cards.add(card);
        card.setUser(this);
        return this;
    }
 
    public User removeCard(Card card) {
        this.cards.remove(card);
        card.setUser(null);
        return this;
    }
 
    public Building getBuilding() {
        return this.building;
    }
 
    public void setBuilding(Building building) {
        this.building = building;
    }
 
    public User building(Building building) {
        this.setBuilding(building);
        return this;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return getId() != null && getId().equals(((User) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
 
    @Override
    public String toString() {
        return "User{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", email='" + getEmail() + "'" +
            ", userPhone='" + getUserPhone() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
    
}