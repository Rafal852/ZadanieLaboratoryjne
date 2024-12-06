package pl.wsb.lab;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DoctorsSchedule {
    public DoctorsSchedule() {
        this.visits = new ArrayList<>();
        this.schedules = new HashMap<>();

    }


    class Visit {
        private LocalDate date;
        private Doctor doctor;
        private Patient patient;
        private LocalTime startTime;
        private LocalTime endTime;


        public Visit(LocalDate date, Doctor doctor, Patient patient, LocalTime startTime, LocalTime endTime) {
            this.date = date;
            this.doctor = doctor;
            this.patient = patient;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalDate getDate() {
            return date;
        }

        public Doctor getDoctor() {
            return doctor;
        }

        public Patient getPatient() {
            return patient;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

    }

    class WorkSchedule {
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;

        public WorkSchedule(LocalDate date, LocalTime startTime, LocalTime endTime) {
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalDate getDate() {
            return date;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }
    }

    private Map<String, List<WorkSchedule>> schedules;
    private List<Visit> visits;

    // Dodanie grafiku pracy lekarza
    public void addDoctorSchedule(String doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        schedules.putIfAbsent(doctorId, new ArrayList<>());

        WorkSchedule newSchedule = new WorkSchedule(date, startTime, endTime);

        // Sprawdzanie konfliktu z istniejącymi grafikami
        for (WorkSchedule schedule : schedules.get(doctorId)) {
            if (schedule.getDate().equals(date) &&
                    (!endTime.isBefore(schedule.getStartTime()) && !startTime.isAfter(schedule.getEndTime()))) {
                System.out.println("Konflikt w grafiku pracy lekarza na dzień " + date + ". Godziny pracy już istnieją.");
                return;
            }
        }

        schedules.get(doctorId).add(newSchedule);
        System.out.println("Dodano grafik pracy dla lekarza " + doctorId + ": " + date + " " + startTime + "-" + endTime);
    }

    // Sprawdzenie, czy lekarz jest dostępny w danym dniu i godzinie
    private boolean isDoctorAvailable(String doctorId, LocalDate date, LocalTime startTime) {
        if (!schedules.containsKey(doctorId)) {
            return false;
        }

        for (WorkSchedule schedule : schedules.get(doctorId)) {
            if (schedule.getDate().equals(date) &&
                    !startTime.isBefore(schedule.getStartTime()) &&
                    !startTime.isAfter(schedule.getEndTime().minusMinutes(15))) {
                return true;
            }
        }
        return false;
    }

    // Pobieranie pełnego grafiku pracy lekarzy na następny tydzień
    public List<WorkSchedule> getWorkScheduleForDoctor(String doctorId, LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(7);
        return schedules.getOrDefault(doctorId, new ArrayList<>()).stream()
                .filter(schedule -> !schedule.getDate().isBefore(startDate) && !schedule.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public void AddVisit(LocalDate date, Doctor doctor, Patient patient, LocalTime startTime) {
        startTime = roundToQuarterHour(startTime);
        LocalTime endTime = startTime.plusMinutes(15);

        if (!isDoctorAvailable(doctor.getId(), date, startTime)) {
            System.out.println("Lekarz nie jest dostępny w tym terminie! Wybierz inną datę lub godzinę.");
            return;
        }

        Visit newVisit = new Visit(date, doctor, patient, startTime, endTime);

        if (checkSchedule(newVisit)) {
            this.visits.add(newVisit);
            System.out.println("Wizyta dodana: " +
                    "Data: " + date +
                    ", Lekarz: " + doctor.getFirstName() + " " + doctor.getLastName() +
                    ", Pacjent: " + patient.getFirstName() + " " + patient.getLastName() +
                    ", Godzina: " + startTime);
        } else {
            System.out.println("W tym czasie lekarz jest zajęty! Wybierz inną godzinę.");
        }
    }

    public List<Visit> getScheduleForDoctor(String doctorId, LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(7); // Zakres na tydzień
        return visits.stream()
                .filter(visit -> visit.getDoctor().getId().equals(doctorId))
                .filter(visit -> !visit.getDate().isBefore(startDate) && !visit.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }


    private boolean checkSchedule(Visit newVisit) {
        for (Visit visit : visits) {

            if (visit.getDate().equals(newVisit.getDate()) &&
                    visit.getDoctor().equals(newVisit.getDoctor())) {

                if (newVisit.getStartTime().equals(visit.getEndTime())) {
                    continue;
                }

                if (!newVisit.getStartTime().isAfter(visit.getEndTime()) &&
                        !newVisit.getEndTime().isBefore(visit.getStartTime())) {
                    return false;
                }
            }
        }
        return true;
    }

    private LocalTime roundToQuarterHour(LocalTime time) {
        int minutes = time.getMinute();
        int mod = minutes % 15;
        if (mod == 0) return time;
        if (mod < 8) {
            return time.minusMinutes(mod);
        } else {
            return time.plusMinutes(15 - mod);
        }
    }
}
