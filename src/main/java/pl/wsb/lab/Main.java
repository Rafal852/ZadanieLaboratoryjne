package pl.wsb.lab;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
public class Main {
    private static Clinic registry = new Clinic();
    private static MedicalPersonel medicalStaff = new MedicalPersonel();
    //private static DoctorsAppointments schedule = new DoctorsAppointments();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {

        LocalDate testDate = LocalDate.now();
        Patient patient1 = new Patient("Jan","Kowalski","321332109338", "Nowa 1", "123456789", "pacjent1@gmail.com", testDate);
        Doctor doctor1 = new Doctor("123", "Kamil", "Nowak", "321332109339", "Stara 1", "123123123", "doktor1", testDate, new ArrayList<>());

        registry.addPatient(patient1);
        medicalStaff.addDoctor(doctor1);

        while (true) {
            System.out.println("1. Zarządzaj pacjentami");
            System.out.println("2. Zarządzaj personelem medycznym");
            System.out.println("3. Zarządzaj wizytami");
            System.out.println("4. Wyjście");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    managePatients();
                    break;
                case 2:
                    manageDoctors();
                    break;
                case 3:
                    manageSchedule();
                    break;
                case 4:
                    System.out.println("Zamykanie programu.");
                    return;
                default:
                    System.out.println("Nieprawidłowa opcja.");
            }
        }
    }

    private static void managePatients() {
        while (true) {
            System.out.println("\nZarządzanie pacjentami:");
            System.out.println("1. Dodaj pacjenta");
            System.out.println("2. Wyszukaj pacjenta po PESEL");
            System.out.println("3. Wyszukaj pacjentów po nazwisku");
            System.out.println("4. Powrót do menu głównego");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    searchByPesel();
                    break;
                case 3:
                    searchByLastName();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Nieprawidłowa opcja.");
            }
        }
    }

    private static void manageDoctors() {
        while (true) {
            System.out.println("\nZarządzanie personelem medycznym:");
            System.out.println("1. Dodaj lekarza");
            System.out.println("2. Dodaj specjalizację lekarzowi");
            System.out.println("3. Znajdź lekarza po ID");
            System.out.println("4. Znajdź lekarzy po specjalizacji");
            System.out.println("5. Powrót do menu głównego");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    addSpecToDoctor();
                    break;
                case 3:
                    findDoctorById();
                    break;
                case 4:
                    findDoctorsBySpec();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Nieprawidłowa opcja.");
            }
        }
    }
    private static void manageSchedule() {
        System.out.println("\nZarządzanie harmonogramem lekarzy:");
        System.out.println("1. Dodaj wizyte");
        System.out.println("3. Powrót do menu głównego");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addVisit();
                break;
            case 3:
                return;
            default:
                System.out.println("Nieprawidłowa opcja.");
        }
    }

    private static void addPatient() {
        System.out.print("Imię: ");
        String firstName = scanner.nextLine();
        System.out.print("Nazwisko: ");
        String lastName = scanner.nextLine();
        System.out.print("PESEL: ");
        String pesel = scanner.nextLine();

        // check poprawność pesela
        if (pesel.length() != 11 || !pesel.matches("\\d+")) {
            System.out.println("Nieprawidłowy PESEL. Musi zawierać 11 cyfr.");
            return;
        }

        // check czy jest pesel powtarzalny
        if (registry.findPatientByPesel(pesel) != null) {
            System.out.println("Pacjent o podanym PESEL już istnieje.");
            return;
        }

        System.out.print("Adres: ");
        String address = scanner.nextLine();
        System.out.print("Numer telefonu: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Data urodzenia (dd-MM-yyyy): ");
        String birthDateString = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(birthDateString, dateFormatter);

        Patient patient = new Patient(firstName, lastName, pesel, address, phoneNumber, email, birthDate);
        registry.addPatient(patient);
    }

    private static void addVisit() {
        System.out.print("Podaj PESEL pacjenta: ");
        String pesel = scanner.nextLine();
        Patient patient = registry.findPatientByPesel(pesel);
        System.out.println("Pacjent: " + patient.getFirstName() + " " + patient.getLastName());

        System.out.print("Podaj Id lekarza: ");
        String id = scanner.nextLine();
        Doctor doctor = medicalStaff.findDoctorById(id);
        System.out.println("Lekarz: " + doctor.getFirstName() + " " + doctor.getLastName());

        System.out.print("Podaj date: ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter);

        System.out.print("Podaj godzine poczatku wizyty: ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine());

        LocalTime endTime = startTime.plusMinutes(15);

        //schedule.AddVisit(date, doctor, patient, startTime, endTime);
    }

    private static void searchByPesel() {
        System.out.print("Podaj PESEL: ");
        String pesel = scanner.nextLine();
        Patient patient = registry.findPatientByPesel(pesel);
        if (patient != null) {
            System.out.println("Znaleziono pacjenta: " + patient);
        } else {
            System.out.println("Nie znaleziono pacjenta o podanym PESEL.");
        }
    }

    private static void searchByLastName() {
        System.out.print("Podaj nazwisko: ");
        String lastName = scanner.nextLine();
        List<Patient> patients = registry.findPatientsByLastName(lastName);
        if (!patients.isEmpty()) {
            System.out.println("Znaleziono pacjentów:");
            patients.forEach(System.out::println);
        } else {
            System.out.println("Nie znaleziono pacjentów o podanym nazwisku.");
        }
    }

    private static void addDoctor() {
        System.out.print("ID lekarza: ");
        String id = scanner.nextLine();
        System.out.print("Imię: ");
        String firstName = scanner.nextLine();
        System.out.print("Nazwisko: ");
        String lastName = scanner.nextLine();
        System.out.print("PESEL: ");
        String pesel = scanner.nextLine();
        System.out.print("Adres: ");
        String address = scanner.nextLine();
        System.out.print("Numer telefonu: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Data urodzenia (dd-MM-yyyy): ");
        String birthDateString = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(birthDateString, dateFormatter);

        System.out.println("Wprowadź specjalizacje (wpisz 'koniec' aby zakończyć):");
        List<String> specializations = new ArrayList<>();
        while (true) {
            String specialization = scanner.nextLine();
            if (specialization.equalsIgnoreCase("koniec")) break;
            specializations.add(specialization);
        }

        Doctor doctor = new Doctor(id, firstName, lastName, pesel, address, phoneNumber, email, birthDate, specializations);
        medicalStaff.addDoctor(doctor);
    }

    private static void addSpecToDoctor() {
        System.out.print("Podaj ID lekarza: ");
        String id = scanner.nextLine();
        System.out.print("Nowa specjalizacja: ");
        String specialization = scanner.nextLine();
        medicalStaff.addSpecToDoctor(id, specialization);
    }

    private static void findDoctorById() {
        System.out.print("Podaj ID lekarza: ");
        String id = scanner.nextLine();
        Doctor doctor = medicalStaff.findDoctorById(id);
        if (doctor != null) {
            System.out.println("Znaleziono lekarza: " + doctor);
        } else {
            System.out.println("Nie znaleziono lekarza o podanym ID.");
        }
    }

    private static void findDoctorsBySpec() {
        System.out.print("Podaj specjalizację: ");
        String specialization = scanner.nextLine();
        List<Doctor> doctors = medicalStaff.findDoctorsBySpec(specialization);
        if (!doctors.isEmpty()) {
            System.out.println("Znaleziono lekarzy:");
            doctors.forEach(System.out::println);
        } else {
            System.out.println("Nie znaleziono lekarzy o podanej specjalizacji.");
        }
    }
}
*/
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        DoctorsSchedule schedule = new DoctorsSchedule();

        Doctor doctor = new Doctor("123", "Jan", "Kowalski", "321332109339", "Stara 1", "123123123", "doktor1", LocalDate.of(1980, 5, 20), new ArrayList<>());
        schedule.addSchedule(doctor, LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(16, 0));

        DoctorsAppointments appointments = new DoctorsAppointments(schedule);
        Patient patient = new Patient("Anna", "Nowak", "12345678901", "Testowa 1", "123456789", "anna.nowak@example.com", LocalDate.of(1990, 1, 1));
        try {
            // Próba rezerwacji wizyty na godzinę xx:10
            appointments.bookAppointment(LocalDate.now(), doctor, patient, LocalTime.of(9, 10));
            System.out.println("Test: Błąd! Wizyta została umówiona na godzinę inną niż xx:15.");
        } catch (Exception e) {
            System.out.println("Test: Sukces! " + e.getMessage());
        }


        // Dodanie harmonogramu
       /* LocalDate date = LocalDate.of(2024, 11, 24);
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        schedule.addSchedule(doctor, date, startTime, endTime);

        // Pobranie godzin pracy
        DoctorsSchedule.WorkingHours workingHours = schedule.getWorkingHours(doctor, date);

        // Sprawdzenie
        if (workingHours != null && workingHours.getStartTime().equals(startTime) && workingHours.getEndTime().equals(endTime)) {
            System.out.println("Test 1: Harmonogram został poprawnie dodany!");
        } else {
            System.out.println("Test 1: Błąd podczas dodawania harmonogramu!");
        }
        /*try {
            // Próba dodania nieprawidłowego harmonogramu
            schedule.addSchedule(doctor, LocalDate.of(2024, 11, 24), LocalTime.of(16, 0), LocalTime.of(8, 0));
            System.out.println("Test 2: Błąd! Harmonogram z nieprawidłowymi godzinami został dodany.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 2: Sukces! Wykryto nieprawidłowe godziny pracy: " + e.getMessage());
        }
*/
       /* schedule.addSchedule(doctor, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(15, 0)); // Dziś
        schedule.addSchedule(doctor, LocalDate.now().plusDays(3), LocalTime.of(10, 0), LocalTime.of(14, 0)); // Za 3 dni
        schedule.addSchedule(doctor, LocalDate.now().plusDays(10), LocalTime.of(8, 0), LocalTime.of(12, 0)); // Poza tygodniem

        // Pobranie tygodniowego grafiku
        System.out.println("Test 3: Grafik na najbliższy tydzień:");
        schedule.loadWeeklySchedule(doctor).forEach(System.out::println);*/


    }
}

