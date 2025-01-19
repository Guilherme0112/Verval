package com.br.verval.models.dto;

public class ErrorValidationDTO {
    
    private String message;
    private Object details;

    public ErrorValidationDTO(String message, Object details){
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getDetails() {
        return details;
    }
    public void setDetails(Object details) {
        this.details = details;
    }
}
