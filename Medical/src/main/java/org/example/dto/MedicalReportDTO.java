package org.example.dto;

import org.example.models.Medical_Reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MedicalReportDTO {
    private Integer id;
    private Integer patientId;
    private String details;
    private String diagnosis;

    public MedicalReportDTO() {
    }

    public MedicalReportDTO(Medical_Reports medicalReport) {
        this.id = medicalReport.getId();
        this.patientId = medicalReport.getPatientId();
        this.details = medicalReport.getDetails();
        this.diagnosis = medicalReport.getDiagnosis(); // Initialize diagnosis field

    }
    public MedicalReportDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.patientId = rs.getInt("patient_id");
        this.details = rs.getString("details");
        this.diagnosis = rs.getString("diagnosis");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}