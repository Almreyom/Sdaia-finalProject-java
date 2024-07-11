package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.MedicalReportDAO;
import org.example.dto.MedicalRecordFilterDTO;
import org.example.dto.MedicalReportDTO;
import org.example.models.Medical_Reports;

import java.sql.SQLException;
import java.util.List;

@Path("medical-reports")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class MedicalReportController {

    MedicalReportDAO medicalReportDAO = new MedicalReportDAO();

    @Context private UriInfo uriInfo;

    @Context private HttpHeaders headers;

    @GET
    public Response getAllMedicalReports() {
        try {
            List<Medical_Reports> medicalReports = medicalReportDAO.selectAllMedicalReports();
            if (medicalReports.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No medical reports found").build();
            }
            return Response.ok(medicalReports).build();
        } catch (Exception e) {
            String errorMessage = "Error retrieving medical reports: " + e.getMessage();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
        }
    }


    @GET
    @Path("/{id}")
    public Response getMedicalReport(@PathParam("id") int id) throws ClassNotFoundException {
        try {
            Medical_Reports medicalReport = medicalReportDAO.selectMedicalReportById(id);
            if (medicalReport == null) {
                throw new NotFoundException("Medical report not found with id " + id);
            }
            return Response.ok(new MedicalReportDTO(medicalReport)).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving medical report with id " + id, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/consultation")
    public Response getConsultationResults(
            @BeanParam MedicalRecordFilterDTO filter
    ) throws ClassNotFoundException {
        try {
            List<Medical_Reports> consultationResults = MedicalReportDAO.selectConsultationResults(filter);
            if (consultationResults.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No consultation results found").build();
            }
            return Response.ok(consultationResults).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving consultation results", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 5 (Patient can request a medical history report for all previously recorded diagnosis)
    @GET
    @Path("/patient/{patientId}")
    public Response getMedicalHistoryForPatient(@PathParam("patientId") int patientId) {
        try {
            // Fetch all medical reports for the given patientId
            List<Medical_Reports> medicalHistory = medicalReportDAO.selectMedicalReportsByPatient(patientId);
            if (medicalHistory.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No medical history found for patient with id " + patientId).build();
            }
            return Response.ok(medicalHistory).build();
        } catch (SQLException | ClassNotFoundException e) {
            throw new WebApplicationException("Error retrieving medical history for patient with id " + patientId,
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}