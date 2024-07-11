package org.example.dto;

import org.example.models.Patients;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDTO {
    private Integer id;
    private String name;


    public PatientDTO() {
    }


    public PatientDTO(Patients patient) {
        this.id = patient.getId();
        this.name = patient.getName();

    }

    public PatientDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name_patient");

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


    public Patients toModel() {
        return new Patients();
    }
}
