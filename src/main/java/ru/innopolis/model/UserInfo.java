package ru.innopolis.model;

import ru.innopolis.model.interfaces.Identified;

import java.util.UUID;

/**
 * Представление сущности дополнительной информации о пользователя в системе.
 */
public class UserInfo implements Identified<UUID> {

    private static final long serialVersionUID = -3415549133677946887L;

    private UUID id;
    private String lastName;
    private String firstName;
    private String secondName;

    public UserInfo() {
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

}
