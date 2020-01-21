package ru.innopolis.model;

import ru.innopolis.model.interfaces.CreateAtIdentified;
import ru.innopolis.model.interfaces.Identified;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Представление сущности пользователя в системе
 */
public class User implements Identified<UUID>, CreateAtIdentified {

    private static final long serialVersionUID = -7931737332645464539L;

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserStatus userStatus;
    private String username;
    private String password;
    private String email;
    private Role role;
    private UserInfo userInfo;

    public User() {
        this.role = Role.ROLE_OPERATOR;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

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

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
