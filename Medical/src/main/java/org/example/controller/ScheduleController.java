package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.ScheduleDAO;
import org.example.models.Schedule;


import java.sql.SQLException;
import java.util.ArrayList;


@Path("schedules")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ScheduleController {
    ScheduleDAO scheduleDAO = new ScheduleDAO();

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @GET
    public Response getAllSchedules() throws SQLException, ClassNotFoundException {
        try {
            ArrayList<Schedule> schedules = scheduleDAO.selectAllSchedules();
            if (schedules.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No schedules found").build();
            }
            return Response.ok(schedules).build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error retrieving schedules", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response insertSchedule(Schedule schedule) throws ClassNotFoundException {
        try {
            scheduleDAO.insertSchedule(schedule);
            return Response.status(Response.Status.CREATED).entity("Schedule created").build();
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return a detailed error message to the client
            String errorMessage = "Error creating schedule: " + e.getMessage();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response updateSchedule(@PathParam("id") int id, Schedule schedule) throws ClassNotFoundException {
        try {
            schedule.setId(id);
            scheduleDAO.updateSchedule(schedule);
            return Response.ok().entity("Schedule updated").build();
        } catch (SQLException e) {
            throw new WebApplicationException("Error updating schedule with id " + id, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


}