package org.example.dto;

import org.example.models.Consultations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConsultationDTO {
    private Integer id;
    private Integer doctorId;
    private Integer patientId;
    private LocalDateTime requestTime;
    private LocalDateTime consultationTime;
    private String status;
    private Integer rating;

    public ConsultationDTO() {
    }

    public ConsultationDTO(Consultations consultation) {
        this.id = consultation.getId();
        this.doctorId = consultation.getDoctorId();
        this.patientId = consultation.getPatientId();
        this.requestTime = consultation.getRequestTime();
        this.consultationTime = consultation.getConsultationTime();
        this.status = consultation.getStatus();
    }

    public ConsultationDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.doctorId = rs.getInt("doctor_id");
        this.patientId = rs.getInt("patient_id");
        this.requestTime = rs.getTimestamp("request_time").toLocalDateTime();
        this.consultationTime = rs.getTimestamp("consultation_time").toLocalDateTime();
        this.status = rs.getString("status");
        this.rating = rs.getInt("rating");

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
}