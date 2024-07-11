package org.example.dao;

import org.example.db.MCPConnection;
import org.example.dto.PatientDTO;
import org.example.models.Patients;

import java.sql.*;
import java.util.ArrayList;

import static org.hibernate.cfg.AvailableSettings.URL;

public class PatientDAO {
   private static final String SELECT_ALL_PATIENTS = "SELECT * FROM PATIENTS";
     private static final String SELECT_PASS_EMAIL = "select * from PATIENTS where email = ?AND password_patient = ?";


    public static ArrayList<Patients> selectAllPatients() throws SQLException, ClassNotFoundException {

        try (Connection conn = MCPConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_PATIENTS)) {

            ArrayList<Patients> patients = new ArrayList<>();
            while (rs.next()) {
                patients.add(new Patients(rs));
            }
            return patients;
        }
    }

    public Patients PatientsLogin(String email, String password)throws SQLException,ClassNotFoundException {
        Connection conn = MCPConnection.getConnection();
        PreparedStatement st = conn.prepareStatement(SELECT_PASS_EMAIL);
        st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                return new Patients(rs);
            }
            else {
                return null;
            }
    }
}