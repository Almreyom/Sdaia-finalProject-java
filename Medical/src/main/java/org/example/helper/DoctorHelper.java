package org.example.helper;

import org.example.dto.DoctorDTO;
import org.example.models.Doctors;

public class DoctorHelper {
    public static DoctorDTO toDTO(Doctors doctor) {
        return new DoctorDTO(doctor);
    }

    public static Doctors toModel(DoctorDTO doctorDTO) {
        Doctors doctor = new Doctors();
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        return doctor;
    }
}