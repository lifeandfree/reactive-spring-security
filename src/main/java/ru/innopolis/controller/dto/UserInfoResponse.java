package ru.innopolis.controller.dto;

import ru.innopolis.model.Role;
import ru.innopolis.model.UserStatus;

import java.io.Serializable;

public class UserInfoResponse implements Serializable {

    private static final long serialVersionUID = -2192428755729701237L;

    private String uuid;
    private String name;
    private Role role;
    private UserStatus status;
    private String fullName;
    private String organisationName;

    public UserInfoResponse() {
        this.uuid = "";
        this.name = "";
        this.role = null;
        this.status = null;
        this.fullName = "";
        this.organisationName = "";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }
}
