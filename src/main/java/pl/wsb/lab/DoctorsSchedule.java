package pl.wsb.lab;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorsSchedule {
    public DoctorsSchedule() {
        this.visits = new ArrayList<>();
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

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Doctor getDoctor() {
            return doctor;
        }

        public void setDoctor(Doctor doctor) {
            this.doctor = doctor;
        }

        public Patient getPatient() {
            return patient;
        }

        public void setPatient(Patient patient) {
            this.patient = patient;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }
    }

    private List<Visit> visits;

    public void AddVisit(LocalDate date, Doctor doctor, Patient patient, LocalTime startTime, LocalTime endTime) {
        Visit visit = new Visit(date, doctor, patient, startTime, endTime);

        if (checkSchedule(visit)) {
            this.visits.add(visit);
            System.out.println("Wizyta dodana");
        } else {
            System.out.println("W tym czasie lekarz jest zajety!\n");
        }
    }

    private boolean checkSchedule(Visit newVisit) {
        for (Visit visit1 : visits) {
            if (visit1.getDate().equals(newVisit.getDate()) && visit1.getDoctor().equals(newVisit.getDoctor())) {

                boolean isStaredDuringOtherVisit = (newVisit.getStartTime().isBefore(visit1.getEndTime()) && newVisit.getStartTime().isAfter(visit1.getStartTime()));
                boolean isFinishDuringOtherVisit = (newVisit.getEndTime().isBefore(visit1.getEndTime()) && newVisit.getEndTime().isAfter(visit1.getStartTime()));

                if (isStaredDuringOtherVisit || isFinishDuringOtherVisit) {
                    return false;
                }
            }
        }
        return true;
    }
}
