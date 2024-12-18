package pl.wsb.lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class MedicalPersonelTest {

    @Test
    public void testAddDoctor() {
        MedicalPersonel personnel = new MedicalPersonel();
        Doctor doctor = new Doctor("123", "Jan", "Kowalski", "321332109338", "Nowa 1", "123456789", "jan@kowalski.com", LocalDate.now(), List.of("Kardiolog"));
        personnel.addDoctor(doctor);

        Doctor found = personnel.findDoctorById("123");
        assertNotNull(found, "Lekarz powinien zostać dodany do systemu.");
        assertEquals("Jan", found.getFirstName(), "Imię lekarza powinno się zgadzać.");
        assertEquals(1, found.getSpecializations().size(), "Lekarz powinien mieć jedną specjalizację.");
        assertTrue(found.getSpecializations().contains("Kardiolog"), "Lekarz powinien mieć specjalizację Kardiolog.");
    }

    @Test
    public void testAddSpecToDoctor() {
        MedicalPersonel personnel = new MedicalPersonel();
        Doctor doctor = new Doctor("123", "Anna", "Nowak", "321332109339", "Stara 1", "123456789", "anna@nowak.com", LocalDate.now(), List.of());
        personnel.addDoctor(doctor);

        personnel.addSpecToDoctor("123", "Dermatolog");

        Doctor found = personnel.findDoctorById("123");
        assertNotNull(found, "Lekarz powinien być w systemie.");
        assertEquals(1, found.getSpecializations().size(), "Lekarz powinien mieć jedną specjalizację.");
        assertTrue(found.getSpecializations().contains("Dermatolog"), "Specjalizacja Dermatolog powinna zostać dodana.");
    }

    @Test
    public void testFindDoctorById() {
        MedicalPersonel personnel = new MedicalPersonel();
        Doctor doctor = new Doctor("123", "Adam", "Nowak", "321332109340", "Morska 1", "987654321", "adam@nowak.com", LocalDate.now(), List.of("Neurolog"));
        personnel.addDoctor(doctor);

        Doctor found = personnel.findDoctorById("123");
        assertNotNull(found, "Lekarz o podanym ID powinien istnieć.");
        assertEquals("Adam", found.getFirstName(), "Imię znalezionego lekarza powinno się zgadzać.");
        assertTrue(found.getSpecializations().contains("Neurolog"), "Specjalizacja Neurolog powinna być przypisana do lekarza.");
    }

    @Test
    public void testFindDoctorsBySpec() {
        MedicalPersonel personnel = new MedicalPersonel();
        Doctor doctor1 = new Doctor("123", "Kamil", "Ziemniak", "321332109341", "Polna 1", "123123123", "kamil@ziemniak.com", LocalDate.now(), List.of("Chirurg"));
        Doctor doctor2 = new Doctor("124", "Ewa", "Ziemia", "321332109342", "Leśna 2", "321321321", "ewa@ziemia.com", LocalDate.now(), List.of("Chirurg", "Ortopeda"));
        Doctor doctor3 = new Doctor("125", "Tomasz", "Kot", "321332109343", "Szara 3", "456456456", "tomasz@kot.com", LocalDate.now(), List.of("Ortopeda"));
        personnel.addDoctor(doctor1);
        personnel.addDoctor(doctor2);
        personnel.addDoctor(doctor3);

        List<Doctor> surgeons = personnel.findDoctorsBySpec("Chirurg");
        assertEquals(2, surgeons.size(), "Powinno zostać znalezionych dwóch chirurgów.");
        assertTrue(surgeons.stream().anyMatch(d -> d.getId().equals("123")), "Lista chirurgów powinna zawierać lekarza o ID 123.");
        assertTrue(surgeons.stream().anyMatch(d -> d.getId().equals("124")), "Lista chirurgów powinna zawierać lekarza o ID 124.");
    }
    @Test
    public void testFindDoctorWithMultipleSpecializations() {
        MedicalPersonel personnel = new MedicalPersonel();
        Doctor doctor = new Doctor("127", "Tomasz", "Kowal", "321332109341", "Górska 6", "987654321", "tomasz@kowal.com", LocalDate.now(), List.of("Kardiolog", "Chirurg"));
        personnel.addDoctor(doctor);

        List<Doctor> cardiologists = personnel.findDoctorsBySpec("Kardiolog");
        assertEquals(1, cardiologists.size(), "Powinien zostać znaleziony jeden kardiolog.");
        assertTrue(cardiologists.stream().anyMatch(d -> d.getId().equals("127")), "Kardiolog o ID 127 powinien zostać znaleziony.");

        List<Doctor> surgeons = personnel.findDoctorsBySpec("Chirurg");
        assertEquals(1, surgeons.size(), "Powinien zostać znaleziony jeden chirurg.");
        assertTrue(surgeons.stream().anyMatch(d -> d.getId().equals("127")), "Chirurg o ID 127 powinien zostać znaleziony.");
    }

    @Test
    public void testAddDoctorWithoutSpecialization() {
        MedicalPersonel personnel = new MedicalPersonel();
        Doctor doctor = new Doctor("126", "Ewa", "Nowak", "321332109340", "Zielona 5", "123456789", "ewa@nowak.com", LocalDate.now(), List.of());
        personnel.addDoctor(doctor);

        Doctor found = personnel.findDoctorById("126");
        assertNotNull(found, "Lekarz powinien zostać dodany do systemu.");
        assertTrue(found.getSpecializations().isEmpty(), "Lekarz powinien początkowo nie mieć specjalizacji.");

        personnel.addSpecToDoctor("126", "Okulista");
        assertTrue(found.getSpecializations().contains("Okulista"), "Specjalizacja powinna zostać poprawnie dodana.");
    }
    @Test
    public void testFindDoctorByIdEmptyList() {
        MedicalPersonel personnel = new MedicalPersonel();

        Doctor found = personnel.findDoctorById("123");
        assertNull(found, "Nie powinno być żadnego lekarza w pustej liście.");
    }

}
