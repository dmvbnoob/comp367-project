package com.team5.bms.model;

import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
* This class represents the domain object model for Service Requests.
*
* @author Alicia "Product Owner, Subject Matter Expert (SME), Software Quality" Singca
*/
@Entity
@Table(name = "Requests")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")

public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
 
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Statuses status;
 
    @Column(name = "create_date")
    private Instant createDate;
 
    @Column(name = "progress_date")
    private Instant progressDate;
 
    @Column(name = "update_date")
    private Instant updateDate;
 
    @Column(name = "assignee")
    private String assignee;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requests", "cards", "building" }, allowSetters = true)
    private User user;
 
    public Long getId() {
        return this.id;
    }
 
    public Request id(Long id) {
        this.setId(id);
        return this;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getTitle() {
        return this.title;
    }
 
    public Request title(String title) {
        this.setTitle(title);
        return this;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getDescription() {
        return this.description;
    }
 
    public Request description(String description) {
        this.setDescription(description);
        return this;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public Statuses getStatus() {
        return this.status;
    }
 
    public Request status(Statuses status) {
        this.setStatus(status);
        return this;
    }
 
    public void setStatus(Statuses status) {
        this.status = status;
    }
 
    public Instant getCreateDate() {
        return this.createDate;
    }
 
    public Request createDate(Instant createDate) {
        this.setCreateDate(createDate);
        return this;
    }
 
    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }
 
    public Instant getProgressDate() {
        return this.progressDate;
    }
 
    public Request progressDate(Instant progressDate) {
        this.setProgressDate(progressDate);
        return this;
    }
 
    public void setProgressDate(Instant progressDate) {
        this.progressDate = progressDate;
    }
 
    public Instant getUpdateDate() {
        return this.updateDate;
    }
 
    public Request updateDate(Instant updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }
 
    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }
 
    public String getAssignee() {
        return this.assignee;
    }
 
    public Request assignee(String assignee) {
        this.setAssignee(assignee);
        return this;
    }
 
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
 
    public User getUser() {
        return this.user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
 
    public Request user(User user) {
        this.setUser(user);
        return this;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return getId() != null && getId().equals(((Request) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
 
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", progressDate='" + getProgressDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", assignee='" + getAssignee() + "'" +
            "}";
    }
}
