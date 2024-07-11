/*import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.controller.DoctorController;
import org.example.dao.DoctorDAO;
import org.example.models.Doctors;
import org.example.models.Schedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestDoctorController {

    @InjectMocks
    DoctorController doctorController;

    @Mock
    DoctorDAO doctorDAO;

    @Mock
    UriInfo uriInfo;

    @Test
    public void testGetAllDoctors() throws SQLException {
        // Mock data
        ArrayList<Doctors> doctorsList = new ArrayList<>();
        doctorsList.add(new Doctors(1, "Dr. John Doe", "Cardiology", "john.doe@example.com", "password", "1234567890"));

        // Mock behavior
        when(doctorDAO.selectAllDoctors()).thenReturn(doctorsList);

        // Test the controller method
        Response response = doctorController.getAllDoctors(uriInfo, null);

        // Assertions
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(doctorsList, response.getEntity());
    }

    @Test
    public void testGetOneDoctor() throws SQLException, ClassNotFoundException {
        // Mock data
        int doctorId = 1;
        Doctors doctor = new Doctors(1, "Dr. John Doe", "Cardiology", "john.doe@example.com", "password", "1234567890");

        // Mock behavior
        when(doctorDAO.SELECT_ONE_DOCTOR(doctorId)).thenReturn(doctor);

        // Test the controller method
        Response response = doctorController.getOneDoctor(doctorId);

        // Assertions
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(doctor, response.getEntity());
    }

    @Test
    public void testInsertDoctor() throws SQLException, ClassNotFoundException {
        // Mock data
        Doctors doctorToInsert = new Doctors(1, "Dr. Ali", "Cardiology", "ali@example.com", "password", "1234567890");

        // Mock behavior
        doNothing().when(doctorDAO).insertDoctor(doctorToInsert);

        // Test the controller method
        Response response = doctorController.insertDoctor(doctorToInsert);

        // Assertions
        Assertions.assertEquals(201, response.getStatus());
        Assertions.assertEquals(doctorToInsert, response.getEntity());
    }

    @Test
    public void testUpdateDoctor() throws SQLException {
        // Mock data
        int doctorId = 1;
        Doctors updatedDoctor = new Doctors(doctorId, "Dr. John Doe", "Cardiology", "john.doe@example.com", "password", "1234567890");

        // Mock behavior
        doNothing().when(doctorDAO).updateDoctor(updatedDoctor);

        // Test the controller method
        Response response = doctorController.updateDoctor(doctorId, updatedDoctor);

        // Assertions
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("Doctor updated", response.getEntity());
    }

    @Test
    public void testDeleteDoctor() throws SQLException {
        // Mock data
        int doctorId = 1;

        // Mock behavior
        doNothing().when(doctorDAO).deleteDoctor(doctorId);

        // Test the controller method
        Response response = doctorController.deleteDoctor(doctorId);

        // Assertions
        Assertions.assertEquals(204, response.getStatus());
        Assertions.assertEquals("Doctor deleted", response.getEntity());
    }

    @Test
    public void testCreateSchedule() throws SQLException {
        // Mock data
        int doctorId = 1;
        Schedule scheduleToCreate = new Schedule();
        scheduleToCreate.setId(1);
        scheduleToCreate.setDoctorId(doctorId);

        // Mock behavior
        doNothing().when(doctorDAO).insertSchedule(scheduleToCreate);

        // Test the controller method
        Response response = doctorController.createSchedule(doctorId, scheduleToCreate);

        // Assertions
        Assertions.assertEquals(201, response.getStatus());
        Assertions.assertEquals(scheduleToCreate, response.getEntity());
    }

    // Add more tests for other methods as needed
}
*/