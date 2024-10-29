package pl.wsb.lab;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Clinic registry = new Clinic();
    private static MedicalPersonel medicalStaff = new MedicalPersonel();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Zarządzaj pacjentami");
            System.out.println("2. Zarządzaj personelem medycznym");
            System.out.println("3. Wyjście");

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
