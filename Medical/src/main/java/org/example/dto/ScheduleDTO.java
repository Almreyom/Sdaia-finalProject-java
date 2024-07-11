package org.example.dto;

import org.example.models.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleDTO {
    private int id;
    private int doctorId;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available;

    public ScheduleDTO() {
    }

    public ScheduleDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.doctorId = schedule.getDoctorId();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.available = schedule.isAvailable();
    }

    public ScheduleDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.doctorId = rs.getInt("doctor_id");
        this.startTime = rs.getTime("start_time").toLocalTime();
        this.endTime = rs.getTime("end_time").toLocalTime();
        this.available = rs.getBoolean("is_available");
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}