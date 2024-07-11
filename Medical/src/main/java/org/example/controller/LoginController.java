package org.example.controller;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;
import org.example.dao.DoctorDAO;
import org.example.dao.LoginDAO;
import org.example.dao.PatientDAO;
import org.example.dto.LoginDTO;
import org.example.dto.LoginRespondDTO;
import org.example.models.Doctors;
import org.example.models.Login;
import org.example.models.Patients;

import java.sql.SQLException;
import java.util.ArrayList;

@Path("/Login")
public class LoginController
{
    static LoginDAO loginDAO = new LoginDAO();
    PatientDAO patientDAO = new PatientDAO();
    DoctorDAO doctorDAO = new DoctorDAO();

    @POST
    public Response login(LoginDTO loginDTO) throws SQLException {

        LoginRespondDTO loginRespondDTO;
        try
        {
            Login login = loginDAO.getLogin(loginDTO.getLOGIN_email(), loginDTO.getLOGIN_password());

            GenericEntity<ArrayList<Doctors>> doctors = new GenericEntity<ArrayList<Doctors>>(doctorDAO.selectAllDoctors()) {};
            //select * from DOCTOR;

            GenericEntity<ArrayList<Patients>> patients = new GenericEntity<ArrayList<Patients>>(patientDAO.selectAllPatients()) {};
            //select * from PATIENT;

            if (login != null)
            {
                if (login.getLOGIN_Type().equals("Doctor")) {

                    for (Doctors doctor : doctors.getEntity()) {
                        if (doctor.getEmail().equals(loginDTO.getLOGIN_email()) && doctor.getPassword().equals(loginDTO.getLOGIN_password())) {

                            Integer doctorID = doctor.getId();
                            loginRespondDTO = new LoginRespondDTO(doctorID, login.getLOGIN_Type());
                            return Response
                                    .ok(loginRespondDTO)
                                    .build();
                        }
                    }
                }
                else
                {
                    for (Patients patient : patients.getEntity()) {
                        if (patient.getEmail().equals(loginDTO.getLOGIN_email()) && patient.getPassword().equals(loginDTO.getLOGIN_password())) {

                            Integer patientID = patient.getId();
                            loginRespondDTO = new LoginRespondDTO(patientID, login.getLOGIN_Type());
                            return Response
                                    .ok(loginRespondDTO)
                                    .build();
                        }
                    }
                }
            }
            loginRespondDTO = new LoginRespondDTO(0, "");
            return Response
                    .ok(loginRespondDTO)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Response register(Login login)
    {
        try {
            loginDAO.insertLogin(login);
            IdDTO dto = new IdDTO();

            return Response
                    .ok(dto)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}