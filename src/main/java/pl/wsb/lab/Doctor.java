package pl.wsb.lab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Doctor {
    private String id;
    private String firstName;
    private String lastName;
    private String pesel;
    private String address;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private List<String> specializations;

    public Doctor(String id, String firstName, String lastName, String pesel, String address,
                  String phoneNumber, String email, LocalDate birthDate, List<String> specializations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
        this.specializations = new ArrayList<>(specializations);
    }

    public String getId() {
        return id;
    }

    public List<String> getSpecializations() {
        return specializations;
    }

    public void addSpecialization(String specialization) {
        if (!specializations.contains(specialization)) {
            specializations.add(specialization);
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", specializations=" + specializations +
                '}';
    }
}
