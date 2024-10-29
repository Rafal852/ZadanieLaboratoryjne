package pl.wsb.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class ClinicTest {
    private Clinic clinic;

    @BeforeEach
    void setUp() {
        clinic = new Clinic();
    }

    @Test
    void testAddPatient() {

        Patient patient = new Patient("Jan", "Kowalski", "12345678901", "Warszawa", "123456789", "jan@xyz.com", LocalDate.of(1990, 1, 1));

        clinic.addPatient(patient);

        assertEquals(1, clinic.findPatientsByLastName("Kowalski").size());
    }



    @Test
    void testFindPatientByPesel() {

        Patient patient = new Patient("Jan", "Kowalski", "12345678901", "Warszawa", "123456789", "jan@xyz.com", LocalDate.of(1990, 1, 1));
        clinic.addPatient(patient);

        Patient foundPatient = clinic.findPatientByPesel("12345678901");

        assertNotNull(foundPatient, "Pacjent powinien być znaleziony po PESEL.");
        assertEquals("Kowalski", foundPatient.getLastName());
    }

    @Test
    void testFindPatientsByLastName() {

        Patient patient1 = new Patient("Jan", "Kowalski", "12345678901", "Warszawa", "123456789", "jan@xyz.com", LocalDate.of(1990, 1, 1));
        Patient patient2 = new Patient("Anna", "Kowalski", "12345678902", "Krakow", "987654321", "anna@xyz.com", LocalDate.of(1985, 5, 20));
        clinic.addPatient(patient1);
        clinic.addPatient(patient2);

        var foundPatients = clinic.findPatientsByLastName("Kowalski");

        assertEquals(2, foundPatients.size(), "Powinno być dwóch pacjentów o nazwisku Kowalski.");
    }
}
