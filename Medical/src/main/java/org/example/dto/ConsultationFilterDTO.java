package org.example.dto;

import jakarta.ws.rs.QueryParam;

public class ConsultationFilterDTO {
    private @QueryParam("patientId") Integer patientId;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }
}
