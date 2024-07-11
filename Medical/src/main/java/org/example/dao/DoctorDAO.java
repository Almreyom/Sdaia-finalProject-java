package org.example.dao;

import org.example.db.MCPConnection;
import org.example.dto.DoctorFilterDTO;
import org.example.exceptions.DoctorNotFoundException;
import org.example.models.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;


public class DoctorDAO {

    private static final String SELECT_ALL_DOCTORS = "SELECT * FROM DOCTORS";
    private static final String SELECT_ONE_DOCTOR = "SELECT * FROM DOCTORS WHERE id = ?";
    private static final String SELECT_PASS_EMAIL = "select * from DOCTORS where email = ?AND password = ?";


    public ArrayList<Doctors> selectAllDoctors() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<Doctors> doctors = new ArrayList<>();

        try {
            conn = MCPConnection.getConnection();
            stmt = conn.prepareStatement(SELECT_ALL_DOCTORS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                doctors.add(new Doctors(rs));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return doctors;
    }

    public Doctors SELECT_ONE_DOCTOR(int id) throws SQLException, ClassNotFoundException {
        Connection conn = MCPConnection.getConnection();
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_DOCTOR);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new Doctors(rs);
        } else {
            throw new DoctorNotFoundException("Doctor not found with id " + id);
        }
    }


    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as per your application's requirement
        }
    }



    //Requirement 2 ( Doctor should be able to define and manage schedule availability)
    public void insertSchedule(Schedule schedule) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO SCHEDULES (doctor_id, start_time, end_time, is_available) VALUES (?, ?, ?, ?)";
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, schedule.getDoctorId());
            stmt.setTimestamp(2, Timestamp.valueOf(String.valueOf(schedule.getStartTime())));
            stmt.setTimestamp(3, Timestamp.valueOf(String.valueOf(schedule.getEndTime())));
            stmt.setBoolean(4, schedule.isAvailable());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    schedule.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    //Requirement 2 ( Doctor should be able to define and manage schedule availability)
    public List<Schedule> getAllSchedulesByDoctorId(int doctorId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM SCHEDULES WHERE doctor_id = ?";
        List<Schedule> schedules = new ArrayList<>();
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Schedule schedule = new Schedule();
                    schedule.setId(rs.getInt("id"));
                    schedule.setDoctorId(rs.getInt("doctor_id"));
                    schedule.setStartTime(LocalTime.from(rs.getTimestamp("start_time").toLocalDateTime()));
                    schedule.setEndTime(LocalTime.from(rs.getTimestamp("end_time").toLocalDateTime()));
                    schedule.setAvailable(rs.getBoolean("is_available"));

                    schedules.add(schedule);
                }
            }
        }

        return schedules;
    }

    //Requirement 3 ( Doctor should be able to check all pending consultation requests)
    public List<Consultations> getPendingConsultationRequests(int doctorId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM CONSULTATIONS WHERE doctor_id = ? AND status = 'pending'";
        List<Consultations> consultations = new ArrayList<>();
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Consultations consultation = new Consultations();
                    consultation.setId(rs.getInt("id"));
                    consultation.setDoctorId(rs.getInt("doctor_id"));
                    consultation.setPatientId(rs.getInt("patient_id"));
                    consultation.setRequestTime(rs.getTimestamp("request_time").toLocalDateTime());
                    consultation.setConsultationTime(rs.getTimestamp("consultation_time").toLocalDateTime());
                    consultation.setStatus(rs.getString("status"));

                    consultations.add(consultation);
                }
            }
        }

        return consultations;
    }

    public Consultations getConsultationById(int consultationId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM CONSULTATIONS WHERE id = ?";
        Consultations consultation = null;
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consultationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    consultation = new Consultations();
                    consultation.setId(rs.getInt("id"));
                    consultation.setDoctorId(rs.getInt("doctor_id"));
                    consultation.setPatientId(rs.getInt("patient_id"));
                    consultation.setRequestTime(rs.getTimestamp("request_time").toLocalDateTime());
                    consultation.setConsultationTime(rs.getTimestamp("consultation_time").toLocalDateTime());
                    consultation.setStatus(rs.getString("status"));
                }
            }
        }

        return consultation;
    }

    //Requirement 4 ( Doctor should be able to give consultation to a pending request)
    public void updateConsultationStatus(int consultationId, String newStatus) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE CONSULTATIONS SET status = ? WHERE id = ?";
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, consultationId);
            stmt.executeUpdate();
        }
    }

    //Requirement 5 ( Doctor should be able to record a patient's diagnosis)
    public void recordDiagnosis(int medicalReportId, String diagnosis) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE MEDICAL_REPORTS SET diagnosis = ? WHERE id = ?";
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, diagnosis);
            stmt.setInt(2, medicalReportId);
            stmt.executeUpdate();
        }
    }

    //Requirement 6 ( Doctor should be able to search patients' medical records)
    public List<Medical_Reports> searchMedicalRecords(int patientId, LocalDateTime fromDate, LocalDateTime toDate) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM MEDICAL_REPORTS WHERE patient_id = ?";

        if (fromDate != null) {
            sql += " AND report_date >= ?";
        }
        if (toDate != null) {
            sql += " AND report_date <= ?";
        }
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            int index = 2;

            if (fromDate != null) {
                stmt.setTimestamp(index++, Timestamp.valueOf(fromDate));
            }
            if (toDate != null) {
                stmt.setTimestamp(index, Timestamp.valueOf(toDate));
            }

            ResultSet rs = stmt.executeQuery();
            List<Medical_Reports> medicalReports = new ArrayList<>();
            while (rs.next()) {
                medicalReports.add(new Medical_Reports(rs));
            }
            return medicalReports;
        }
    }

    //Requirement 2 ( Patient should be able to search for doctor by dynamic criteria [specialties, ratings, availability, id, or name])
    public ArrayList<Doctors> searchDoctors(DoctorFilterDTO filter) throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT * FROM DOCTORS WHERE 1=1");

        if (filter.getSpecialty() != null) {
            sql.append(" AND specialty = ?");
        }
        if (filter.getRating() != null) {
//            sql.append(" AND id IN (SELECT doctor_id FROM Ratings WHERE rating = ?)");
            sql.append(" AND id IN (SELECT doctor_id FROM CONSULTATIONS WHERE rating = ?)");
        }
        if (filter.getAvailability() != null) {
//          sql.append("SELECT doctor_id FROM SCHEDULES");
//          ArrayList<Schedule> schedules = new ArrayList<>();
//          LocalTime current = LocalTime.now();
//          for(Schedule s: schedules)
//          {
//              Boolean isAvailable = current.isAfter(s.getStartTime()) && current.isBefore(s.getEndTime());
//              updateScheduleValue(s.getId(), isAvailable);
//          }
            sql.append(" AND id IN (SELECT doctor_id FROM SCHEDULES WHERE is_available = ?)");
        }
        if (filter.getId() != null) {
            sql.append(" AND id = ?");
        }
        if (filter.getName() != null) {
            sql.append(" AND name_doctor LIKE ?");
        }
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (filter.getSpecialty() != null) {
                st.setString(index++, filter.getSpecialty());
            }
            if (filter.getRating() != null) {
                st.setInt(index++, filter.getRating());
            }
            if (filter.getAvailability() != null) {
                st.setBoolean(index++, filter.getAvailability());
            }
            if (filter.getId() != null) {
                st.setInt(index++, filter.getId());
            }
            if (filter.getName() != null) {
                st.setString(index++, "%" + filter.getName() + "%");
            }

            ResultSet rs = st.executeQuery();
            ArrayList<Doctors> doctors = new ArrayList<>();
            while (rs.next()) {
                doctors.add(new Doctors(rs)); // Assuming Doctors class handles ResultSet to object mapping
            }
            return doctors;
        }
    }


    public Doctors DoctorLogin(String email, String password)throws SQLException,ClassNotFoundException {
        Connection conn = MCPConnection.getConnection();
        PreparedStatement st = conn.prepareStatement(SELECT_PASS_EMAIL);
        st.setString(1, email);
        st.setString(2, password);
        ResultSet rs = st.executeQuery();
        if (rs.next()){
            return new Doctors(rs);
        }
        else {
            return null;
        }
    }
}