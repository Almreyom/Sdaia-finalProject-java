package org.example.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Patients {

    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate birthDate;

    public Patients(int id, String name, String email, String password, String phone, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public Patients(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name_patient");
        this.email = rs.getString("email");
        this.password = rs.getString("password_patient");
        this.phone = rs.getString("phone");
        this.birthDate = rs.getObject("birth_date", LocalDate.class);

    }

    public Patients() {

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}