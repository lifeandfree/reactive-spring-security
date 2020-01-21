package ru.innopolis.service.sec.service.dto;

import java.io.Serializable;

public class OrganizationDto implements Serializable {

    private static final long serialVersionUID = 3221511132970375162L;

    private String name;

    public OrganizationDto(String name) {
        this.name = name;
    }

    public OrganizationDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
