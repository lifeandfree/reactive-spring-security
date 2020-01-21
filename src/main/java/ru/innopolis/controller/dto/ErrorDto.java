package ru.innopolis.controller.dto;

import java.io.Serializable;

public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 3854574740368307575L;

    private String error;
    private String message;

    public ErrorDto() {
    }

    public ErrorDto(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{").
                append("error='").append(error).append('\'').
                append(", message='").append(message).append('\'').
                append('}').toString();
    }
}
