package org.ashfan.dto;

public class LoginUserResponseDTO {
    private String message;
    private String errorCode;
    private String status;
    private String token;

    public LoginUserResponseDTO(String message, String errorCode, String status, String token) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.token = token;
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
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
