package org.example.controller;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.PatientDAO;
import org.example.models.Patients;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("patients")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class PatientController {
   PatientDAO patientDAO = new PatientDAO();
   @Context
   UriInfo uriInfo;
   @Context
    HttpHeaders header;

    @GET
    public Response getAllPatients(
            @Context UriInfo uriInfo,
            @Context HttpHeaders headers
    ) throws ClassNotFoundException {
        try {
            ArrayList<Patients> patients = PatientDAO.selectAllPatients();
            if (patients.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No patients found").build();
            }
            return Response.ok(patients)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving patients", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }}
