package org.example.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Consultations {
    private Integer id;
    private Integer doctorId;
    private Integer patientId;
    private LocalDateTime requestTime;
    private LocalDateTime consultationTime;
    private String status;
    private Integer rating;
    private Medical_Reports medicalReport;

    public Consultations() {
    }

    public Consultations(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.doctorId = rs.getInt("doctor_id");
        this.patientId = rs.getInt("patient_id");
        this.requestTime = rs.getTimestamp("request_time").toLocalDateTime();
        this.consultationTime = rs.getTimestamp("consultation_time").toLocalDateTime();
        this.status = rs.getString("status");
        this.rating = rs.getInt("rating");

        // Initialize medicalReport if it's available in ResultSet
        if (rs.getInt("medical_report_id") != 0) {
            this.medicalReport = new Medical_Reports(rs);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public LocalDateTime getConsultationTime() {
        return consultationTime;
    }

    public void setConsultationTime(LocalDateTime consultationTime) {
        this.consultationTime = consultationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Medical_Reports getMedicalReport() {
        return medicalReport;
    }

    public void setMedicalReport(Medical_Reports medicalReport) {
        this.medicalReport = medicalReport;
    }
}