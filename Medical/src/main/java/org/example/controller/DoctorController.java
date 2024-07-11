package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.ConsultationDAO;
import org.example.dao.DoctorDAO;
import org.example.dto.*;
import org.example.models.Consultations;
import org.example.models.Doctors;
import org.example.models.Medical_Reports;
import org.example.models.Schedule;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Path("/doctors")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class DoctorController {

    private DoctorDAO doctorDAO = new DoctorDAO();

    @GET
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllDoctors(
            @Context UriInfo uriInfo,
            @Context HttpHeaders headers) {
        try {
            ArrayList<Doctors> doctors = doctorDAO.selectAllDoctors();
            if (doctors.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No doctors found").build();
            }
            return Response.ok(doctors)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving doctors", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getOneDoctor(@PathParam("id") int id) throws ClassNotFoundException {
        try {
            Doctors doctor = doctorDAO.SELECT_ONE_DOCTOR(id);
            if (doctor == null) {
                throw new NotFoundException("Doctor not found with id " + id);
            }
            return Response.ok(new DoctorDTO(doctor)).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving doctor with id " + id, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 2 ( Doctor should be able to define and manage schedule availability)
    @POST
    @Path("/{doctorId}/schedules")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSchedule(@PathParam("doctorId") int doctorId, Schedule schedule) throws ClassNotFoundException {
        try {
            schedule.setDoctorId(doctorId);
            doctorDAO.insertSchedule(schedule);
            return Response.status(Response.Status.CREATED).entity(schedule).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error creating schedule for doctor with id " + doctorId, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 2 ( Doctor should be able to define and manage schedule availability)
    @GET
    @Path("/{doctorId}/schedules")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSchedulesByDoctorId(@PathParam("doctorId") int doctorId) throws ClassNotFoundException {
        try {
            List<Schedule> schedules = doctorDAO.getAllSchedulesByDoctorId(doctorId);
            if (schedules.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No schedules found for doctor with id " + doctorId).build();
            }
            return Response.ok(schedules).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving schedules for doctor with id " + doctorId, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 3 ( Doctor should be able to check all pending consultation requests)
    @GET
    @Path("/{doctorId}/pending-consultations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingConsultationRequests(@PathParam("doctorId") int doctorId) throws ClassNotFoundException {
        try {
            List<Consultations> consultations = doctorDAO.getPendingConsultationRequests(doctorId);
            if (consultations.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No pending consultations found for doctor with id " + doctorId).build();
            }
            return Response.ok(consultations).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving pending consultations for doctor with id " + doctorId, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{doctorId}/consultations/{consultationId}/update-status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateConsultationStatus(
            @PathParam("doctorId") int doctorId,
            @PathParam("consultationId") int consultationId,
            String newStatus
    ) throws ClassNotFoundException {
        try {
            doctorDAO.updateConsultationStatus(consultationId, newStatus);
            return Response.ok().entity("Consultation status updated successfully").build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error updating consultation status", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 5 ( Doctor should be able to record a patient's diagnosis)

    @PUT
    @Path("/{doctorId}/medical-reports/{medicalReportId}/record-diagnosis")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recordDiagnosis(
            @PathParam("doctorId") int doctorId,
            @PathParam("medicalReportId") int medicalReportId,
            String diagnosis
    ) throws ClassNotFoundException {
        try {
            doctorDAO.recordDiagnosis(medicalReportId, diagnosis);
            return Response.ok().entity("Diagnosis recorded successfully").build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error recording diagnosis", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 6 ( Doctor should be able to search patients' medical records)

    @GET
    @Path("/{doctorId}/patients/{patientId}/medical-reports")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response searchMedicalRecords(
            @PathParam("doctorId") int doctorId,
            @PathParam("patientId") int patientId,
            @BeanParam MedicalRecordFilterDTO filter
    ) throws ClassNotFoundException {
        try {
            List<Medical_Reports> medicalReports = doctorDAO.searchMedicalRecords(patientId, filter.getFromDate(), filter.getToDate());
            if (medicalReports.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No medical records found for patient with id " + patientId).build();
            }
            return Response.ok(medicalReports).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving medical records for patient with id " + patientId, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Requirement 2 ( Patient should be able to search for doctor by dynamic criteria [specialties, ratings, availability, id, or name])

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchDoctors(@BeanParam DoctorFilterDTO filter) throws ClassNotFoundException {
        try {
            ArrayList<Doctors> doctors = doctorDAO.searchDoctors(filter);
            if (doctors.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No doctors found matching the criteria").build();
            }
            return Response.ok(doctors).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error searching doctors", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }



    //Requirement 4 ( Doctor should be able to give consultation to a pending request)

    @PUT
    @Path("/{doctorId}/consultations/{consultationId}/respond")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response respondToConsultationRequest(
            @PathParam("doctorId") int doctorId,
            @PathParam("consultationId") int consultationId,
            Consultations consultation) throws ClassNotFoundException {
        try {
            // Ensure the consultation is for the correct doctor and is pending
            Consultations existingConsultation = doctorDAO.getConsultationById(consultationId);
            if (existingConsultation == null || existingConsultation.getDoctorId() != doctorId) {
                throw new NotFoundException("Consultation not found or not assigned to this doctor");
            }
            if (!existingConsultation.getStatus().equalsIgnoreCase("pending")) {
                throw new IllegalArgumentException("Consultation is not pending");
            }

            // Update consultation details and set status to "Approved"
            consultation.setId(consultationId);
            consultation.setStatus("Approved");
            consultation.setConsultationTime(LocalDateTime.now()); // Set consultation time
            ConsultationDAO.updateConsultation(consultation);

            // Optionally, handle updating associated medical records or other related actions

            return Response.ok().entity("Consultation request approved").build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error responding to consultation request", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
