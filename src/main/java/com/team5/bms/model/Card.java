package com.team5.bms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
* This class represents the domain object model for the Bank Card of a Building Owner
*
* @author Jasper Belenzo
*/
@Entity
@Table(name = "cards")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @NotNull
    @Column(name = "cardName", nullable = false)
    private String cardName;

    @NotNull
    @Column(name = "expiry", nullable = false)
    private String expiry;

    @NotNull
    @Column(name = "cvv", nullable = false)
    private String cvv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requests", "cards", "building" }, allowSetters = true)
    private User user;

    public Long getId() {
        return this.id;
    }

    public Card id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public Card number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCardName() {
        return this.cardName;
    }

    public Card cardName(String cardName) {
        this.setCardName(cardName);
        return this;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getExpiry() {
        return this.expiry;
    }

    public Card expiry(String expiry) {
        this.setExpiry(expiry);
        return this;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCvv() {
        return this.cvv;
    }

    public Card cvv(String cvv) {
        this.setCvv(cvv);
        return this;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card user(User user) {
        this.setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        return getId() != null && getId().equals(((Card) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", cardName='" + getCardName() + "'" +
            ", expiry='" + getExpiry() + "'" +
            ", cvv='" + getCvv() + "'" +
            "}";
    }
}
