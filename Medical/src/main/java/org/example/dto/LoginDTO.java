package org.example.dto;
public class LoginDTO
{
    String LOGIN_email;
    String LOGIN_password;

    public LoginDTO() {
    }

    public LoginDTO(String LOGIN_email, String LOGIN_password) {
        this.LOGIN_email = LOGIN_email;
        this.LOGIN_password = LOGIN_password;
    }

    public String getLOGIN_email() {
        return LOGIN_email;
    }

    public String getLOGIN_password() {
        return LOGIN_password;
    }

    public void setLOGIN_email(String LOGIN_email) {
        this.LOGIN_email = LOGIN_email;
    }

    public void setLOGIN_password(String LOGIN_password) {
        this.LOGIN_password = LOGIN_password;
    }
}