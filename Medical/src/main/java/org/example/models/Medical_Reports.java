package org.example.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Medical_Reports {
    private int id;
    private int patientId;
    private String details;
    private LocalDateTime reportDate;
    private String diagnosis;
    private int consultationId;

    public Medical_Reports() {
    }

    public Medical_Reports(int id, int patientId, String details, LocalDateTime reportDate, String diagnosis, int consultationId) {
        this.id = id;
        this.patientId = patientId;
        this.details = details;
        this.reportDate = reportDate;
        this.diagnosis = diagnosis;
        this.consultationId = consultationId;
    }

    public Medical_Reports(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.patientId = rs.getInt("patient_id");
        this.details = rs.getString("details");
        this.reportDate = rs.getTimestamp("report_date").toLocalDateTime();
        this.diagnosis = rs.getString("diagnosis");
        this.consultationId =rs.getInt("consultation_id");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }
}