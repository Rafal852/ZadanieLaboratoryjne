package pl.wsb.lab;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Clinic registry = new Clinic();
    private static MedicalPersonel medicalStaff = new MedicalPersonel();
    private static DoctorsSchedule schedule = new DoctorsSchedule();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {

        LocalDate testDate = LocalDate.now();
        Patient patient1 = new Patient("Jan","Kowalski","321332109338", "Nowa 1", "123456789", "pacjent1@gmail.com", testDate);
        Patient patient2 = new Patient("Rafał","Perfikowski","11111111111", "Krzyżacka 5", "999666333", "aaa@aaa.com", testDate);
        Doctor doctor1 = new Doctor("123", "Kamil", "Nowak", "321332109339", "Stara 1", "123123123", "doktor1", testDate, new ArrayList<>());
        Doctor doctor2 = new Doctor("1", "Konrad", "Ziemniak", "12345678921", "Twoja 1", "444444444", "doktor2", testDate, new ArrayList<>());

        registry.addPatient(patient1);
        registry.addPatient(patient2);
        medicalStaff.addDoctor(doctor1);
        medicalStaff.addDoctor(doctor2);

        while (true) {
            try {
                System.out.println("1. Zarządzaj pacjentami");
                System.out.println("2. Zarządzaj personelem medycznym");
                System.out.println("3. Zarządzaj wizytami");
                System.out.println("4. Wyjście");

                int mainChoice = Integer.parseInt(scanner.nextLine());

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
                        System.out.println("Nieprawidłowa opcja. Wybierz liczbę od 1 do 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowe dane wejściowe. Proszę wprowadzić poprawną liczbę.");
            } catch (Exception e) {
                System.out.println("Wystąpił błąd: " + e.getMessage());
            }
        }
    }

    private static void addPatient() {
        try {
            System.out.print("Imię: ");
            String firstName = scanner.nextLine();

            System.out.print("Nazwisko: ");
            String lastName = scanner.nextLine();

            String pesel;
            while (true) {
                System.out.print("PESEL: ");
                pesel = scanner.nextLine();
                if (pesel.length() == 11 && pesel.matches("\\d+")) {
                    if (registry.findPatientByPesel(pesel) == null) {
                        break;
                    } else {
                        System.out.println("Pacjent o podanym PESEL już istnieje. Spróbuj ponownie.");
                    }
                } else {
                    System.out.println("Nieprawidłowy PESEL. Musi zawierać 11 cyfr.");
                }
            }

            System.out.print("Adres: ");
            String address = scanner.nextLine();

            String phoneNumber;
            while (true) {
                System.out.print("Numer telefonu: ");
                phoneNumber = scanner.nextLine();
                if (phoneNumber.matches("\\d{9}")) {
                    break;
                } else {
                    System.out.println("Nieprawidłowy numer telefonu. Musi zawierać 9 cyfr.");
                }
            }

            String email;
            while (true) {
                System.out.print("Email: ");
                email = scanner.nextLine();
                if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    break;
                } else {
                    System.out.println("Nieprawidłowy adres email. Spróbuj ponownie.");
                }
            }

            LocalDate birthDate;
            while (true) {
                System.out.print("Data urodzenia (dd-MM-yyyy): ");
                String birthDateString = scanner.nextLine();
                try {
                    birthDate = LocalDate.parse(birthDateString, dateFormatter);
                    if (birthDate.isAfter(LocalDate.now())) {
                        System.out.println("Data urodzenia nie może być w przyszłości. Spróbuj ponownie.");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Nieprawidłowy format daty. Wprowadź datę w formacie dd-MM-yyyy.");
                }
            }

            // Create and add the patient
            Patient patient = new Patient(firstName, lastName, pesel, address, phoneNumber, email, birthDate);
            registry.addPatient(patient);
            System.out.println("Dodano pacjenta: " + firstName + " " + lastName);

        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas dodawania pacjenta. Spróbuj ponownie.");
        }
    }


    private static void managePatients() {
        while (true) {
            try {
                System.out.println("\nZarządzanie pacjentami:");
                System.out.println("1. Dodaj pacjenta");
                System.out.println("2. Wyszukaj pacjenta po PESEL");
                System.out.println("3. Wyszukaj pacjentów po nazwisku");
                System.out.println("4. Powrót do menu głównego");

                int choice = Integer.parseInt(scanner.nextLine());

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
                        System.out.println("Nieprawidłowa opcja. Wprowadź liczbę od 1 do 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowe dane wejściowe. Wprowadź poprawną liczbę.");
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

    private static void viewDoctorSchedule() {
        System.out.print("Podaj ID lekarza: ");
        String doctorId = scanner.nextLine();
        Doctor doctor = medicalStaff.findDoctorById(doctorId);

        if (doctor == null) {
            System.out.println("Nie znaleziono lekarza o podanym ID.");
            return;
        }

        LocalDate today = LocalDate.now();
        List<DoctorsSchedule.Visit> scheduleForWeek = schedule.getScheduleForDoctor(doctorId, today);

        if (scheduleForWeek.isEmpty()) {
            System.out.println("Brak zaplanowanych wizyt dla tego lekarza w najbliższym tygodniu.");
        } else {
            System.out.println("Grafik wizyt dla lekarza " + doctor.getFirstName() + " " + doctor.getLastName() + " na najbliższy tydzień:");
            for (DoctorsSchedule.Visit visit : scheduleForWeek) {
                System.out.println("- Data: " + visit.getDate() + ", Pacjent: " + visit.getPatient().getFirstName() + " " + visit.getPatient().getLastName() +
                        ", Od: " + visit.getStartTime() + ", Do: " + visit.getEndTime());
            }
        }
    }

    private static void manageSchedule() {
        while (true) {
            System.out.println("\nZarządzanie harmonogramem lekarzy:");
            System.out.println("1. Dodaj wizytę");
            System.out.println("2. Pokaż wizyty lekarza na najbliższy tydzień");
            System.out.println("3. Dodaj grafik pracy lekarza");
            System.out.println("4. Wyświetl grafik pracy i wizyty lekarza na najbliższy tydzień");
            System.out.println("5. Powrót do menu głównego");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addVisit();
                    break;
                case 2:
                    viewDoctorSchedule();
                    break;
                case 3:
                    addDoctorSchedule();
                    break;
                case 4:
                    viewWorkScheduleAndVisits();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Nieprawidłowa opcja.");
            }
        }
    }

    // Dodanie grafiku pracy lekarza
    private static void addDoctorSchedule() {
        System.out.print("Podaj ID lekarza: ");
        String doctorId = scanner.nextLine();

        System.out.print("Podaj datę (dd-MM-yyyy): ");
        LocalDate date;
        try {
            date = LocalDate.parse(scanner.nextLine(), dateFormatter);
        } catch (Exception e) {
            System.out.println("Nieprawidłowy format daty.");
            return;
        }

        System.out.print("Podaj godzinę rozpoczęcia pracy (HH:mm): ");
        LocalTime startTime;
        try {
            startTime = LocalTime.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Nieprawidłowy format godziny.");
            return;
        }

        System.out.print("Podaj godzinę zakończenia pracy (HH:mm): ");
        LocalTime endTime;
        try {
            endTime = LocalTime.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Nieprawidłowy format godziny.");
            return;
        }

        schedule.addDoctorSchedule(doctorId, date, startTime, endTime);
    }

    // Wyświetlanie grafiku pracy i wizyt lekarza
    private static void viewWorkScheduleAndVisits() {
        System.out.print("Podaj ID lekarza: ");
        String doctorId = scanner.nextLine();

        LocalDate today = LocalDate.now();

        List<DoctorsSchedule.WorkSchedule> workSchedules = schedule.getWorkScheduleForDoctor(doctorId, today);

        System.out.println("\nGrafik pracy lekarza na najbliższy tydzień:");
        if (workSchedules.isEmpty()) {
            System.out.println("Brak zaplanowanego grafiku pracy.");
        } else {
            for (DoctorsSchedule.WorkSchedule ws : workSchedules) {
                System.out.println("- Data: " + ws.getDate() + ", Od: " + ws.getStartTime() + ", Do: " + ws.getEndTime());
            }
        }
    }

    private static void addVisit() {
        try {
            System.out.print("Podaj PESEL pacjenta: ");
            String pesel = scanner.nextLine();
            Patient patient = registry.findPatientByPesel(pesel);

            if (patient == null) {
                System.out.println("Nie znaleziono pacjenta o podanym PESEL.");
                return;
            }

            System.out.println("Pacjent: " + patient.getFirstName() + " " + patient.getLastName());

            System.out.print("Podaj ID lekarza: ");
            String id = scanner.nextLine();
            Doctor doctor = medicalStaff.findDoctorById(id);

            if (doctor == null) {
                System.out.println("Nie znaleziono lekarza o podanym ID.");
                return;
            }

            System.out.println("Lekarz: " + doctor.getFirstName() + " " + doctor.getLastName());

            System.out.print("Podaj datę wizyty (dd-MM-yyyy): ");
            LocalDate date;
            try {
                date = LocalDate.parse(scanner.nextLine(), dateFormatter);
            } catch (Exception e) {
                System.out.println("Nieprawidłowy format daty. Wprowadź datę w formacie dd-MM-yyyy.");
                return;
            }

            System.out.print("Podaj godzinę wizyty (w formacie HH:mm, np. 14:00): ");
            LocalTime startTime;
            try {
                startTime = LocalTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Nieprawidłowy format godziny. Wprowadź godzinę w formacie HH:mm.");
                return;
            }

            schedule.AddVisit(date, doctor, patient, startTime);
        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas dodawania wizyty. Spróbuj ponownie.");
        }
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
        try {
            String id;
            while (true) {
                System.out.print("ID lekarza: ");
                id = scanner.nextLine();
                if (!id.isEmpty()) {
                    break;
                } else {
                    System.out.println("ID lekarza nie może być puste. Spróbuj ponownie.");
                }
            }

            System.out.print("Imię: ");
            String firstName = scanner.nextLine();

            System.out.print("Nazwisko: ");
            String lastName = scanner.nextLine();

            String pesel;
            while (true) {
                System.out.print("PESEL: ");
                pesel = scanner.nextLine();
                if (pesel.matches("\\d{11}")) {
                    break;
                } else {
                    System.out.println("Nieprawidłowy PESEL. Musi składać się z 11 cyfr. Spróbuj ponownie.");
                }
            }

            System.out.print("Adres: ");
            String address = scanner.nextLine();

            String phoneNumber;
            while (true) {
                System.out.print("Numer telefonu: ");
                phoneNumber = scanner.nextLine();
                if (phoneNumber.matches("\\d{9}")) {
                    break;
                } else {
                    System.out.println("Nieprawidłowy numer telefonu. Musi zawierać 9.");
                }
            }

            String email;
            while (true) {
                System.out.print("Email: ");
                email = scanner.nextLine();
                if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    break;
                } else {
                    System.out.println("Nieprawidłowy adres email. Spróbuj ponownie.");
                }
            }

            LocalDate birthDate;
            while (true) {
                System.out.print("Data urodzenia (dd-MM-yyyy): ");
                String birthDateString = scanner.nextLine();
                try {
                    birthDate = LocalDate.parse(birthDateString, dateFormatter);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Nieprawidłowy format daty. Spróbuj ponownie (format: dd-MM-yyyy).");
                }
            }

            System.out.println("Wprowadź specjalizacje (wpisz 'koniec' aby zakończyć):");
            List<String> specializations = new ArrayList<>();
            while (true) {
                String specialization = scanner.nextLine();
                if (specialization.equalsIgnoreCase("koniec")) break;
                if (!specialization.isEmpty()) {
                    specializations.add(specialization);
                } else {
                    System.out.println("Specjalizacja nie może być pusta. Wpisz specjalizację lub 'koniec'.");
                }
            }

            // Create and add the doctor
            Doctor doctor = new Doctor(id, firstName, lastName, pesel, address, phoneNumber, email, birthDate, specializations);
            medicalStaff.addDoctor(doctor);
            System.out.println("Dodano lekarza: " + firstName + " " + lastName);

        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas dodawania lekarza. Spróbuj ponownie.");
        }
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
