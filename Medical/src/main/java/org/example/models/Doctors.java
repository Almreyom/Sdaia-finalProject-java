package org.example.models;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
    private int id;
    private String name;
    private String specialty;
    private String email;
    private String password;
    private String phone;

    public Doctors(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name_doctor");
        this.specialty = rs.getString("specialty");
        this.email = rs.getString("email");
        this.password = rs.getString("password_doctor");
        this.phone = rs.getString("phone");
    }

    public Doctors(int id, String name, String specialty, String email, String password, String phone) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Doctors() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}