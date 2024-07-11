package org.example.dao;



import org.example.db.MCPConnection;
import org.example.models.Login;

import java.sql.*;
import java.util.ArrayList;


public class LoginDAO
{

    public Login getLogin(String email, String passwors) throws SQLException, ClassNotFoundException {

        Connection conn = MCPConnection.getConnection();
        PreparedStatement st = conn.prepareStatement("select * from LOGIN where LOGIN_email = ? And LOGIN_password = ?;");

        st.setString(1, email);
        st.setString(2, passwors);

        ResultSet rs = st.executeQuery();

        if(rs.next()) {
            return new Login(rs);
        }
        else {
            return null;
        }
    }

    public void insertLogin(Login login) throws SQLException, ClassNotFoundException {
        Connection conn = MCPConnection.getConnection();

        PreparedStatement st = conn.prepareStatement("insert into LOGIN (LOGIN_Type, LOGIN_email, LOGIN_password) values (?, ?, ?);");

        st.setString(1, login.getLOGIN_Type());
        st.setString(2, login.getLOGIN_email());
        st.setString(3, login.getLOGIN_password());

        st.executeUpdate();
    }
}