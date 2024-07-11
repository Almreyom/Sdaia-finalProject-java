package org.example.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login
{
    Integer LOGIN_ID;
    String LOGIN_Type;
    String LOGIN_email;
    String LOGIN_password;

    public Login() {
    }

    public Login(ResultSet rs) throws SQLException {
        LOGIN_ID = rs.getInt("LOGIN_ID");
        LOGIN_Type = rs.getString("LOGIN_Type");
        LOGIN_email  = rs.getString("LOGIN_email");
        LOGIN_password  = rs.getString("LOGIN_password");
    }

    public Login(Integer LOGIN_ID, String LOGIN_Type, String LOGIN_email, String LOGIN_password) {
        this.LOGIN_ID = LOGIN_ID;
        this.LOGIN_Type = LOGIN_Type;
        this.LOGIN_email = LOGIN_email;
        this.LOGIN_password = LOGIN_password;
    }

    public Integer getLOGIN_ID() {
        return LOGIN_ID;
    }

    public String getLOGIN_Type() {
        return LOGIN_Type;
    }

    public String getLOGIN_email() {
        return LOGIN_email;
    }

    public String getLOGIN_password() {
        return LOGIN_password;
    }

    public void setLOGIN_ID(Integer LOGIN_ID) {
        this.LOGIN_ID = LOGIN_ID;
    }

    public void setLOGIN_Type(String LOGIN_Type) {
        this.LOGIN_Type = LOGIN_Type;
    }

    public void setLOGIN_email(String LOGIN_email) {
        this.LOGIN_email = LOGIN_email;
    }

    public void setLOGIN_password(String LOGIN_password) {
        this.LOGIN_password = LOGIN_password;
    }
}