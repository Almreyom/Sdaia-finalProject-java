/*import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.controller.PatientController;
import org.example.dao.PatientDAO;
import org.example.dto.PatientDTO;
import org.example.dto.PatientFilterDTO;
import org.example.models.Patients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestPatientController {

    @InjectMocks
    PatientController patientController;

    @Mock
    PatientDAO patientDAO;

    @Mock
    UriInfo uriInfo;

    @Test
    public void testGetAllPatients() throws SQLException {
        // Mock data
        List<Patients> patientsList = new ArrayList<>();
        patientsList.add(new Patients(1, "John Doe", "john.doe@example.com", "password", "1234567890", LocalDate.of(1990, 5, 15)));

        // Mock behavior
        when(patientDAO.selectAllPatients(any(PatientFilterDTO.class))).thenReturn((ArrayList<Patients>) patientsList);

        // Test the controller method
        Response response = patientController.getAllPatients(new PatientFilterDTO());

        // Assertions
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(patientsList, response.getEntity());
    }

    @Test
    public void testGetPatient() throws SQLException {
        // Mock data
        int patientId = 1;
        Patients patient = new Patients(patientId, "John Doe", "john.doe@example.com", "password", "1234567890", LocalDate.of(1990, 5, 15));

        // Mock behavior
        when(patientDAO.selectPatientById(patientId)).thenReturn(patient);

        // Test the controller method
        Response response = patientController.getPatient(patientId);

        // Assertions
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(new PatientDTO(patient), response.getEntity());
    }

    @Test
    public void testUpdatePatient() throws SQLException {
        // Mock data
        int patientId = 1;
        Patients updatedPatient = new Patients(patientId, "John Doe", "john.doe@example.com", "password", "1234567890", LocalDate.of(1990, 5, 15));

        // Mock behavior
        doNothing().when(patientDAO).updatePatient(updatedPatient);

        // Test the controller method
        Response response = patientController.updatePatient(patientId, updatedPatient);

        // Assertions
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("Patient updated", response.getEntity());
    }

    @Test
    public void testDeletePatient() throws SQLException {
        // Mock data
        int patientId = 1;

        // Mock behavior
        doNothing().when(patientDAO).deletePatient(patientId);

        // Test the controller method
        Response response = patientController.deletePatient(patientId);

        // Assertions
        Assertions.assertEquals(204, response.getStatus());
        Assertions.assertEquals("Patient deleted", response.getEntity());
    }

    @Test
    public void testCreatePatient() throws SQLException, ClassNotFoundException {
        // Mock data
        Patients patientToCreate = new Patients(1, "John Doe", "john.doe@example.com", "password", "1234567890", LocalDate.of(1990, 5, 15));

        // Mock behavior
        doNothing().when(patientDAO).INSERT_PATIENT(patientToCreate);

        // Test the controller method
        Assertions.assertDoesNotThrow(() -> patientController.createPatient(patientToCreate));
    }

    // Add more tests for other methods as needed
}
*/