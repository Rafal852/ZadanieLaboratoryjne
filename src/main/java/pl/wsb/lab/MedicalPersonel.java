package pl.wsb.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedicalPersonel {
    private List<Doctor> doctors = new ArrayList<>();

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        System.out.println("Lekarz dodany: " + doctor);
    }

    public Doctor findDoctorById(String id) {
        return doctors.stream()
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addSpecToDoctor(String id, String specialization) {
        Doctor doctor = findDoctorById(id);
        if (doctor != null) {
            doctor.addSpecialization(specialization);
            System.out.println("Dodano specjalizację " + specialization + " dla lekarza: " + doctor.getId());
        } else {
            System.out.println("Lekarz o podanym ID nie istnieje.");
        }
    }

    public List<Doctor> findDoctorsBySpec(String specialization) {
        return doctors.stream()
                .filter(doctor -> doctor.getSpecializations().contains(specialization))
                .collect(Collectors.toList());
    }
}
