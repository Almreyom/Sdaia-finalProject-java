package org.example.helper;

import org.example.dto.ConsultationDTO;
import org.example.models.Consultations;

public class ConsultationHelper {
    public static ConsultationDTO toDTO(Consultations consultation) {
        return new ConsultationDTO(consultation);
    }

    public static Consultations toModel(ConsultationDTO consultationDTO) {
        Consultations consultation = new Consultations();
        consultation.setId(consultationDTO.getId());
        consultation.setDoctorId(consultationDTO.getDoctorId());
        consultation.setPatientId(consultationDTO.getPatientId());
        consultation.setRequestTime(consultationDTO.getRequestTime());
        consultation.setConsultationTime(consultationDTO.getConsultationTime());
        consultation.setStatus(consultationDTO.getStatus());
        return consultation;
    }
}