package org.example.dao;

import org.example.db.MCPConnection;
import org.example.dto.ConsultationFilterDTO;
import org.example.dto.MedicalRecordFilterDTO;
import org.example.models.Consultations;
import org.example.models.Medical_Reports;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDAO {

    private static final String INSERT_CONSULTATION = "INSERT INTO CONSULTATIONS (doctor_id, patient_id, request_time, consultation_time, status,rating) VALUES (?, ?, datetime('now','localtime'),datetime('now','localtime'), ?,,?)";
    private static final String UPDATE_CONSULTATION = "UPDATE CONSULTATIONS SET doctor_id = ?, patient_id = ?, request_time = datetime('now','localtime'), consultation_time = datetime('now','localtime'), status = ? ,  rating = ? WHERE id = ?";
    private static final String SELECT_ALL_CONSULTATIONS = "SELECT c.*, mr.id AS medical_report_id, mr.details, mr.report_date, mr.diagnosis FROM CONSULTATIONS c LEFT JOIN MEDICAL_REPORTS mr ON c.id = mr.consultation_id";
    private static final String SELECT_CONSULTATION_RESULTS = "SELECT * FROM MEDICAL_REPORTS WHERE patient_id = ? AND details IS NOT NULL";
    private static final String UPDATE_DOCTOR_RATING = "UPDATE DOCTORS SET rating = ? WHERE id = ?";


    public List<Consultations> selectAllConsultations() throws SQLException, ClassNotFoundException {
        List<Consultations> consultations = new ArrayList<>();
        try (Connection conn = MCPConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_CONSULTATIONS)) {

            while (rs.next()) {
                consultations.add(mapToConsultation(rs));
            }
        }
        return consultations;
    }


    //Requirement 3 ( Patient should be able to request consultation from a selected doctor)
    public void requestConsultation(Consultations consultation) throws SQLException, ClassNotFoundException {
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_CONSULTATION, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, consultation.getDoctorId());
            stmt.setInt(2, consultation.getPatientId());
            stmt.setTimestamp(3, Timestamp.valueOf(consultation.getRequestTime()));
            stmt.setString(4, consultation.getStatus());
            stmt.setInt(5,consultation.getRating());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    consultation.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static void updateConsultation(Consultations consultation) throws SQLException, ClassNotFoundException {
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_CONSULTATION)) {

            stmt.setInt(1, consultation.getDoctorId());
            stmt.setInt(2, consultation.getPatientId());
            stmt.setString(3, consultation.getStatus());
            stmt.setInt(4, consultation.getId());
            stmt.setInt(5,consultation.getRating());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating consultation failed, no rows affected.");
            }
        }
    }


    public List<Medical_Reports> selectConsultationResults(ConsultationFilterDTO filter) throws SQLException, ClassNotFoundException {
        List<Medical_Reports> consultationResults = new ArrayList<>();
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_CONSULTATION_RESULTS)) {

            stmt.setInt(1, filter.getPatientId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                consultationResults.add(new Medical_Reports(rs));
            }
        }
        return consultationResults;
    }

    private static Consultations mapToConsultation(ResultSet rs) throws SQLException {
        Consultations consultation = new Consultations();
        consultation.setId(rs.getInt("id"));
        consultation.setDoctorId(rs.getInt("doctor_id"));
        consultation.setPatientId(rs.getInt("patient_id"));
        consultation.setRequestTime(rs.getTimestamp("request_time").toLocalDateTime());
        consultation.setConsultationTime(rs.getTimestamp("consultation_time") != null ? rs.getTimestamp("consultation_time").toLocalDateTime() : null);
        consultation.setStatus(rs.getString("status"));

        if (rs.getInt("medical_report_id") != 0) {
            Medical_Reports medicalReport = new Medical_Reports();
            medicalReport.setId(rs.getInt("medical_report_id"));
            medicalReport.setDetails(rs.getString("details"));
            medicalReport.setReportDate(rs.getTimestamp("report_date").toLocalDateTime());
            medicalReport.setDiagnosis(rs.getString("diagnosis"));

            consultation.setMedicalReport(medicalReport);
        }

        return consultation;
    }

    //Requirement 4 ( Patient can check a consultation result)
    public List<Medical_Reports> selectConsultationResultsForPatient(int patientId, MedicalRecordFilterDTO filter) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM MEDICAL_REPORTS WHERE patient_id = ?";

        if (filter.getFromDate() != null) {
            sql += " AND report_date >= ?";
        }
        if (filter.getToDate() != null) {
            sql += " AND report_date <= ?";
        }

        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            int index = 2;

            if (filter.getFromDate() != null) {
                stmt.setTimestamp(index++, Timestamp.valueOf(filter.getFromDate()));
            }
            if (filter.getToDate() != null) {
                stmt.setTimestamp(index, Timestamp.valueOf(filter.getToDate()));
            }

            ResultSet rs = stmt.executeQuery();
            List<Medical_Reports> consultationResults = new ArrayList<>();
            while (rs.next()) {
                consultationResults.add(new Medical_Reports(rs));
            }
            return consultationResults;
        }
    }

    //Requirement 6 ( Patient should be able to rate a doctor)
    public void updateDoctorRating(int doctorId, int newRating) throws SQLException, ClassNotFoundException {
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_DOCTOR_RATING)) {
            stmt.setInt(1, newRating);
            stmt.setInt(2, doctorId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating doctor rating failed, no rows affected.");
            }
        }
    }
}