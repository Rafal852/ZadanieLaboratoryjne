package pl.wsb.lab;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void testPatientCreation() {
        //given
        String firstName = "Jan";
        String lastName = "Kowalski";
        String pesel = "12345678901";
        String address = "Wrocław";
        String phoneNumber = "123456789";
        String email = "jankowalski@wp.pl";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        //when
        Patient patient = new Patient(firstName, lastName, pesel, address, phoneNumber, email, birthDate);

        //then
        assertEquals(firstName, patient.getFirstName());
        assertEquals(lastName, patient.getLastName());
        assertEquals(pesel, patient.getPesel());
        assertTrue(patient.toString().contains("age=34")); //jeśli test jest wykonywany w 2024
    }

    @Test
    void testAgeCalculationThroughConstructor() {
        //given
        LocalDate birthDate = LocalDate.of(2000, 12, 8);

        //when
        Patient patient = new Patient(
                "Anna",
                "Nowak",
                "10987654321",
                "Poznań",
                "987654321",
                "annanowak@wp.pl",
                birthDate
        );

        //then
        assertTrue(patient.toString().contains("age=24")); //jeśli test jest wykonywany między 08-12-2024 a 08-12-2025
    }

    @Test
    void testToString() {
        //given
        Patient patient = new Patient(
                "Jan",
                "Kowalski",
                "12345678901",
                "Wrocław",
                "123456789",
                "jankowalski@wp.pl",
                LocalDate.of(1990, 1, 1)
        );

        //when
        String result = patient.toString();

        //then
        assertTrue(result.contains("Jan"));
        assertTrue(result.contains("Kowalski"));
        assertTrue(result.contains("12345678901"));
        assertTrue(result.contains("Wrocław"));
    }

    @Test
    void testInvalidBirthDate() {
        //given
        LocalDate futureDate = LocalDate.now().plusYears(1);

        //when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Patient(
                    "Jan",
                    "Kowalski",
                    "12345678901",
                    "Wrocław",
                    "123456789",
                    "jankowalski@wp.pl",
                    futureDate
            );
        });

        assertEquals("Data urodzenia nie może być w przyszłości. Spróbuj ponownie.", exception.getMessage());
    }
}
