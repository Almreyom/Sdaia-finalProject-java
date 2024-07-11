package org.example.helper;

import org.example.dto.PatientDTO;
import org.example.models.Patients;

public class PatientHelper {
    public static PatientDTO toDTO(Patients patient) {
        return new PatientDTO(patient);
    }

    public static Patients toModel(PatientDTO patientDTO) {
        Patients patient = new Patients();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        return patient;
    }
}