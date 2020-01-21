package ru.innopolis.model;

public enum UserStatus {
    STATUS_CREATED("Создан"),
    STATUS_LOCKED("Заблокирован"),
    STATUS_ACTIVED("Активен");

    private String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {return description;}
}
