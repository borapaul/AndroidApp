package com.example.borapaulco2mobileapp.domain;

public class EmailDto {

    private String email;
    private String subject;
    private String message;

    public EmailDto(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public EmailDto(){
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
