package org.example.dao;

import org.example.db.MCPConnection;
import org.example.models.Schedule;

import java.sql.*;
import java.util.ArrayList;

public class ScheduleDAO {
    private static final String SELECT_ALL_SCHEDULES = "SELECT * FROM SCHEDULES";
    private static final String INSERT_SCHEDULE = "INSERT INTO SCHEDULES (doctor_id,start_time, end_time,is_available) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SCHEDULE = "UPDATE SCHEDULES SET doctor_id = ?, start_time = ?, end_time = ? , is_available = ? WHERE id = ?";

    public ArrayList<Schedule> selectAllSchedules() throws SQLException, ClassNotFoundException {

        try (Connection conn = MCPConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SCHEDULES)) {

            ArrayList<Schedule> schedule = new ArrayList<>();
            while (rs.next()) {
                schedule.add(new Schedule(rs));
            }
            return schedule;
        }
    }

    public void insertSchedule(Schedule schedule) throws SQLException, ClassNotFoundException {
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SCHEDULE)) {

            stmt.setInt(1, schedule.getDoctorId());
            stmt.setTime(2, Time.valueOf(schedule.getStartTime()));
            stmt.setTime(3, Time.valueOf(schedule.getEndTime()));
            stmt.setBoolean(4, schedule.isAvailable());

            stmt.executeUpdate();
        }
    }

    public void updateSchedule(Schedule schedule) throws SQLException, ClassNotFoundException {
        try (Connection conn = MCPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SCHEDULE)) {

            stmt.setInt(1, schedule.getDoctorId());
            stmt.setTime(2, Time.valueOf(schedule.getStartTime()));
            stmt.setTime(3, Time.valueOf(schedule.getEndTime()));
            stmt.setBoolean(4, schedule.isAvailable());
            stmt.setInt(5, schedule.getId());

            stmt.executeUpdate();
        }
    }

}