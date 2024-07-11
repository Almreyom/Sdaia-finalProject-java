package org.example.helper;

import org.example.dto.ScheduleDTO;
import org.example.models.Schedule;

public class ScheduleHelper {
    public static ScheduleDTO toDTO(Schedule schedule) {
        return new ScheduleDTO(schedule);
    }

    public static Schedule toModel(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setDoctorId(scheduleDTO.getDoctorId());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setAvailable(scheduleDTO.isAvailable());
        return schedule;
    }
}
