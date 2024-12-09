package pl.wsb.lab;
import java.time.LocalDate;
import java.time.Period;

public class Patient {
    private String firstName;
    private String lastName;
    private String pesel;
    private String address;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private int age;

    public Patient(String firstName, String lastName, String pesel, String address, String phoneNumber, String email, LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data urodzenia nie może być w przyszłości. Spróbuj ponownie.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
        this.age = calculateAge(birthDate);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String getPesel() {
        return pesel;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + age +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }
}
