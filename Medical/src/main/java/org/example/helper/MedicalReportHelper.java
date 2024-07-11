package org.example.helper;

import org.example.dto.MedicalReportDTO;
import org.example.models.Medical_Reports;

public class MedicalReportHelper {
    public static MedicalReportDTO toDTO(Medical_Reports medicalReport) {
        return new MedicalReportDTO(medicalReport);
    }

    public static Medical_Reports toModel(MedicalReportDTO medicalReportDTO) {
        Medical_Reports medicalReport = new Medical_Reports();
        medicalReport.setId(medicalReportDTO.getId());
        medicalReport.setPatientId(medicalReportDTO.getPatientId());
        medicalReport.setDetails(medicalReportDTO.getDetails());
        medicalReport.setDiagnosis(medicalReportDTO.getDiagnosis());
        return medicalReport;
    }
}
