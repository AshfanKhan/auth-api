package org.ashfan.dto;

public class RegisterUserResponseDTO {
    private String message;
    private String errorCode;
    private String status;

    public RegisterUserResponseDTO(String message, String errorCode, String status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
