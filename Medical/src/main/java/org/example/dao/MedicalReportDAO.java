package org.example.dao;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.example.db.MCPConnection;
import org.example.dto.MedicalRecordFilterDTO;
import org.example.models.Medical_Reports;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalReportDAO {
   private static final String SELECT_ALL_MEDICAL_REPORTS = "SELECT * FROM MEDICAL_REPORTS";
  private static final String SELECT_MEDICAL_REPORT_BY_ID = "SELECT * FROM MEDICAL_REPORTS WHERE id = ?";

    private static final String SELECT_CONSULTATION_RESULTS = "SELECT * FROM MEDICAL_REPORTS WHERE patient_id = ? AND report_date >= ? AND report_date <= ?";

    public ArrayList<Medical_Reports> selectAllMedicalReports() throws ClassNotFoundException {

            try (Connection conn = MCPConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(SELECT_ALL_MEDICAL_REPORTS)) {

                if (!conn.isValid(0)) {
                    throw new SQLException("Database connection is invalid");
                }

                ArrayList<Medical_Reports> medicalReports = new ArrayList<>();
                while (rs.next()) {
                    medicalReports.add(new Medical_Reports(rs));
                }
                return medicalReports;
            } catch (SQLException e) {
                String errorMessage = e.getMessage() != null ? e.getMessage() : "An unknown error occurred while retrieving medical reports";
                throw new WebApplicationException(errorMessage, Response.Status.INTERNAL_SERVER_ERROR);
            }
    }


    public Medical_Reports selectMedicalReportById(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_MEDICAL_REPORT_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Medical_Reports(rs);
            }
            return null; // or throw exception if not found
        }
    }


    public static List<Medical_Reports> selectConsultationResults(MedicalRecordFilterDTO filter) throws SQLException, ClassNotFoundException {
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_CONSULTATION_RESULTS)) {

            stmt.setInt(1, filter.getPatientId());
            stmt.setTimestamp(2, Timestamp.valueOf(filter.getFromDate()));
            stmt.setTimestamp(3, Timestamp.valueOf(filter.getToDate()));

            ResultSet rs = stmt.executeQuery();
            ArrayList<Medical_Reports> consultationResults = new ArrayList<>();
            while (rs.next()) {
                consultationResults.add(new Medical_Reports(rs));
            }
            return consultationResults;
        }
    }

    
    //Requirement 5 (Patient can request a medical history report for all previously recorded diagnosis)

    public List<Medical_Reports> selectMedicalReportsByPatient(int patientId) throws SQLException, ClassNotFoundException {
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_MEDICAL_REPORT_BY_ID)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            List<Medical_Reports> medicalReports = new ArrayList<>();
            while (rs.next()) {
                medicalReports.add(new Medical_Reports(rs));
            }
            return medicalReports;
        }
    }
}