package pl.wsb.lab;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorsAppointments {
    private static final int DEFAULT_VISIT_DURATION_MINUTES = 15;
    private DoctorsSchedule doctorsSchedule;
    private List<Visit> visits;

    public DoctorsAppointments(DoctorsSchedule doctorsSchedule) {
        this.doctorsSchedule = doctorsSchedule;
        this.visits = new ArrayList<>();
    }

    public static class Visit {
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

    public void bookAppointment(LocalDate date, Doctor doctor, Patient patient, LocalTime startTime) {
        LocalTime endTime = startTime.plusMinutes(DEFAULT_VISIT_DURATION_MINUTES);

        DoctorsSchedule.WorkingHours workingHours = doctorsSchedule.getWorkingHours(doctor, date);
        if (workingHours == null) {
            throw new IllegalArgumentException("Doktor jest na urlopie");
        }

        if (!isWithinDoctorSchedule(workingHours, startTime, endTime)) {
            throw new IllegalArgumentException("Godzina wizyty wykracza poza grafik pracy doktora");
        }

        if (!isTimeSlotAvailable(doctor, date, startTime, endTime)) {
            throw new IllegalArgumentException("Doktor ma już zaplanowaną wizytę w tym czasie.");
        }

        visits.add(new Visit(date, doctor, patient, startTime, endTime));
        System.out.println("Wizyta została pomyślnie umówiona.");
    }

    private boolean isWithinDoctorSchedule(DoctorsSchedule.WorkingHours workingHours, LocalTime startTime, LocalTime endTime) {
        return !startTime.isBefore(workingHours.getStartTime()) && !endTime.isAfter(workingHours.getEndTime());
    }

    private boolean isTimeSlotAvailable(Doctor doctor, LocalDate date, LocalTime startTime, LocalTime endTime) {
        for (Visit visit : visits) {
            if (visit.getDoctor().equals(doctor) && visit.getDate().equals(date)) {
                boolean isStartDuringVisit = startTime.isBefore(visit.getEndTime()) && startTime.isAfter(visit.getStartTime());
                boolean isEndDuringVisit = endTime.isBefore(visit.getEndTime()) && endTime.isAfter(visit.getStartTime());
                boolean isOverlap = isStartDuringVisit || isEndDuringVisit ||
                        (startTime.equals(visit.getStartTime()) && endTime.equals(visit.getEndTime()));

                if (isOverlap) {
                    return false;
                }
            }
        }
        return true;
    }
}

