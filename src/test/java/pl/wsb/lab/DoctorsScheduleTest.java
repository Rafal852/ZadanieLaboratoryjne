package pl.wsb.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DoctorsScheduleTest {

    private DoctorsSchedule doctorsSchedule;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        doctorsSchedule = new DoctorsSchedule();
        doctor = new Doctor("123", "Jan", "Kowalski","11111101111","Nowa 2","666666666","test@gmail.com", LocalDate.now(), List.of("Kardiolog"));
        patient = new Patient("Anna", "Nowak", "12345678901", "Warszawa", "123456789", "jan@xyz.com", LocalDate.of(1990, 1, 1));
    }

    @Test
    public void testGetWorkScheduleForDoctor() {
        LocalDate date1 = LocalDate.of(2024, 12, 19);
        LocalDate date2 = LocalDate.of(2024, 12, 20);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        doctorsSchedule.addDoctorSchedule(doctor.getId(), date1, startTime, endTime);
        doctorsSchedule.addDoctorSchedule(doctor.getId(), date2, startTime, endTime);

        List<DoctorsSchedule.WorkSchedule> schedule = doctorsSchedule.getWorkScheduleForDoctor(doctor.getId(), LocalDate.of(2024, 12, 19));

        assertEquals(2, schedule.size(), "Powinny być dwa dni w grafiku na najbliższy tydzień");
}

    @Test
    public void testAddDoctorSchedule() {
        LocalDate date = LocalDate.of(2024, 12, 19);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        doctorsSchedule.addDoctorSchedule(doctor.getId(), date, startTime, endTime);

        List<DoctorsSchedule.WorkSchedule> schedule = doctorsSchedule.getWorkScheduleForDoctor(doctor.getId(), LocalDate.of(2024, 12, 19));
        assertEquals(1, schedule.size(), "Powinien być jeden wpis w grafiku lekarza.");
        assertEquals(date, schedule.get(0).getDate(), "Data grafiku nie jest zgodna.");
        assertEquals(startTime, schedule.get(0).getStartTime(), "Godzina rozpoczęcia grafiku nie jest zgodna.");
        assertEquals(endTime, schedule.get(0).getEndTime(), "Godzina zakończenia grafiku nie jest zgodna.");
    }

    @Test
    public void testAddDoctorScheduleWithConflict() {
        LocalDate date = LocalDate.of(2024, 12, 19);
        LocalTime startTime1 = LocalTime.of(9, 0);
        LocalTime endTime1 = LocalTime.of(12, 0);
        LocalTime startTime2 = LocalTime.of(11, 30);
        LocalTime endTime2 = LocalTime.of(14, 0);

        doctorsSchedule.addDoctorSchedule(doctor.getId(), date, startTime1, endTime1); // Pierwszy grafik
        doctorsSchedule.addDoctorSchedule(doctor.getId(), date, startTime2, endTime2); // Konflikt

        List<DoctorsSchedule.WorkSchedule> schedule = doctorsSchedule.getWorkScheduleForDoctor(doctor.getId(), date);
        assertEquals(1, schedule.size(), "Grafik nie powinien zostać dodany z powodu konfliktu.");
    }


    @Test
    public void testAddVisit() {
        LocalDate date = LocalDate.of(2024, 12, 19);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(9, 15);

        doctorsSchedule.addDoctorSchedule(doctor.getId(), date, startTime, endTime);

        doctorsSchedule.AddVisit(date, doctor, patient, startTime);

        List<DoctorsSchedule.Visit> visits = doctorsSchedule.getScheduleForDoctor(doctor.getId(), date);
        assertEquals(1, visits.size(), "Wizyta powinna zostać dodana.");
        assertEquals(patient, visits.get(0).getPatient(), "Pacjent w wizycie nie jest zgodny.");
        assertEquals(startTime, visits.get(0).getStartTime(), "Czas rozpoczęcia wizyty nie jest zgodny.");
    }

    @Test
    public void testAddVisitWhenDoctorIsNotAvailable() {
        LocalDate date = LocalDate.of(2024, 12, 19);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(9, 15);

        // Dodanie grafiku pracy lekarza
        doctorsSchedule.addDoctorSchedule(doctor.getId(), date, startTime, endTime);

        // Próba dodania wizyty w czasie, gdy lekarz jest zajęty
        doctorsSchedule.AddVisit(date, doctor, patient, LocalTime.of(9, 10)); // Konflikt w czasie

        List<DoctorsSchedule.Visit> visits = doctorsSchedule.getScheduleForDoctor(doctor.getId(), date);
        assertEquals(0, visits.size(), "Wizyta nie powinna zostać dodana, ponieważ lekarz jest zajęty.");
    }

    @Test
    public void testGetScheduleForDoctor() {
        LocalDate date = LocalDate.of(2024, 12, 19);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        doctorsSchedule.addDoctorSchedule(doctor.getId(), date, startTime, endTime);

        List<DoctorsSchedule.WorkSchedule> doctorSchedule = doctorsSchedule.getWorkScheduleForDoctor(doctor.getId(), LocalDate.of(2024, 12, 19));

        assertEquals(1, doctorSchedule.size(), "Powinien być tylko jeden grafik na 19 grudnia.");
        assertEquals(date, doctorSchedule.get(0).getDate(), "Data grafiku nie jest zgodna.");
        assertEquals(startTime, doctorSchedule.get(0).getStartTime(), "Godzina rozpoczęcia grafiku nie jest zgodna.");
        assertEquals(endTime, doctorSchedule.get(0).getEndTime(), "Godzina zakończenia grafiku nie jest zgodna.");
    }

}