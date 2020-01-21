package ru.innopolis.model;

public enum OrganizationStatus {
    STATUS_CREATED("Создан"),
    STATUS_LOCKED("Заблокирован"),
    STATUS_ACTIVED("Активен");

    private String description;

    OrganizationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {return description;}
}
