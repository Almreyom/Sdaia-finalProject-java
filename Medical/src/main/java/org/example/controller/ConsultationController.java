package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.ConsultationDAO;
import org.example.dto.MedicalRecordFilterDTO;
import org.example.models.Consultations;
import org.example.models.Medical_Reports;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Path("/consultations")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ConsultationController {
    ConsultationDAO consultationDAO = new ConsultationDAO();

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @GET
    public Response getAllConsultations() throws SQLException, ClassNotFoundException {
        List<Consultations> consultations = consultationDAO.selectAllConsultations();
        if (consultations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No consultations found").build();
        }
        return Response.ok(consultations).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response insertConsultation(Consultations consultation) throws ClassNotFoundException {
        try {
            consultationDAO.requestConsultation(consultation);
            return Response.status(Response.Status.CREATED).entity("Consultation created").build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error creating consultation", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateConsultation(@PathParam("id") int id, Consultations consultation) throws ClassNotFoundException {
        try {
            consultation.setId(id);
            consultationDAO.updateConsultation(consultation);
            return Response.ok().entity("Consultation updated").build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error updating consultation with id " + id, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 3 ( Patient should be able to request consultation from a selected doctor)

    @POST
    @Path("/request")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response requestConsultation(Consultations consultation) throws ClassNotFoundException {
        try {
            if (consultation == null) {
                throw new IllegalArgumentException("Consultation object is null");
            }

            // Ensure proper initialization and handling of nested objects like medicalReport
            if (consultation.getMedicalReport() != null) {
                // Set consultationId for medical report if necessary
                consultation.getMedicalReport().setConsultationId(consultation.getId());
            }

            consultation.setRequestTime(LocalDateTime.now());
            consultation.setStatus("Pending");
            consultationDAO.requestConsultation(consultation);

            return Response.status(Response.Status.CREATED).entity("Consultation request sent").build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error requesting consultation", Response.Status.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Invalid consultation object: " + e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    //Requirement 4 ( Patient can check a consultation result)

    @Path("/{patientId}/consultation-results")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsultationResultsForPatient(
            @PathParam("patientId") int patientId,
            @BeanParam MedicalRecordFilterDTO filter
    ) throws ClassNotFoundException {
        try {
            List<Medical_Reports> consultationResults = consultationDAO.selectConsultationResultsForPatient(patientId, filter);
            if (consultationResults.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No consultation results found for patient with id " + patientId).build();
            }
            return Response.ok(consultationResults).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving consultation results for patient with id " + patientId, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 6 ( Patient should be able to rate a doctor)

    @PUT
    @Path("/{doctorId}/rate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rateDoctor(
            @PathParam("doctorId") int doctorId,
            int rating) throws ClassNotFoundException {
        try {
            consultationDAO.updateDoctorRating(doctorId, rating);
            return Response.ok().entity("Doctor rated successfully").build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error rating doctor", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}