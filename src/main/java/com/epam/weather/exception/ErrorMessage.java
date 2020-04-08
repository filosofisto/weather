package com.epam.weather.exception;

public class ErrorMessage {

    private String message;

    private String detailedMessage;

    public ErrorMessage(String message, String detailedMessage) {
        this.message = message;
        this.detailedMessage = detailedMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }
}
