package pl.wsb.lab;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Clinic {
    private List<Patient> patients = new ArrayList<>();

    public void addPatient(Patient patient) {
        patients.add(patient);
        System.out.println("Pacjent dodany: " + patient);
    }

    public Patient findPatientByPesel(String pesel) {
        return patients.stream()
                .filter(p -> Objects.equals(p.getPesel(), pesel))
                .findFirst()
                .orElse(null);
    }

    public List<Patient> findPatientsByLastName(String lastName) {
        return patients.stream()
                .filter(p -> Objects.equals(p.getLastName(), lastName))
                .collect(Collectors.toList());
    }

}
