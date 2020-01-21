package ru.innopolis.model;

import ru.innopolis.model.interfaces.CreateAtIdentified;
import ru.innopolis.model.interfaces.Identified;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Представление Организации в системе
 */
public class Organization implements Identified<UUID>, CreateAtIdentified {

    private static final long serialVersionUID = -9005741475704378708L;

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrganizationStatus organizationStatus;
    private String organizationName;
    private Set<User> users;

    public Organization() {
        this.users = new HashSet<>();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OrganizationStatus getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(OrganizationStatus organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
