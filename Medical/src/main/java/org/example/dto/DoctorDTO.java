package org.example.dto;

import org.example.models.Doctors;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDTO {
    private Integer id;
    private String name;
    private String specialty;

    public DoctorDTO() {
    }

    public DoctorDTO(Doctors doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.specialty = doctor.getSpecialty();

    }

    public DoctorDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name_doctor");
        this.specialty = rs.getString("specialty");

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}