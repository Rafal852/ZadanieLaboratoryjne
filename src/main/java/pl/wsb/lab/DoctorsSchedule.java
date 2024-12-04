package pl.wsb.lab;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsSchedule {
    public static class WorkingHours {
        private LocalTime startTime;
        private LocalTime endTime;

        public WorkingHours(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        @Override
        public String toString() {
            return startTime + " - " + endTime;
        }
    }

    private Map<Doctor, Map<LocalDate, WorkingHours>> schedules;

    public DoctorsSchedule() {
        this.schedules = new HashMap<>();
    }

    // Tworzenie grafiku
    public void addSchedule(Doctor doctor, LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Godzina rozpoczęcia pracy nie może być późniejsza niż godzina zakończenia.");
        }
        schedules.putIfAbsent(doctor, new HashMap<>());
        schedules.get(doctor).put(date, new WorkingHours(startTime, endTime));
    }

    // Pobieranie grafiku lekarza na najbliższy tydzień
    public List<String> loadWeeklySchedule(Doctor doctor) {
        List<String> scheduleList = new ArrayList<>();
        Map<LocalDate, WorkingHours> doctorSchedule = schedules.get(doctor);

        if (doctorSchedule == null) {
            return scheduleList; // Brak grafiku dla lekarza
        }

        LocalDate today = LocalDate.now();
        LocalDate inAWeek = today.plusDays(7);

        for (Map.Entry<LocalDate, WorkingHours> entry : doctorSchedule.entrySet()) {
            LocalDate date = entry.getKey();
            if (!date.isBefore(today) && !date.isAfter(inAWeek)) {
                scheduleList.add(date + ": " + entry.getValue());
            }
        }
        return scheduleList;
    }

    // Pobieranie godzin pracy lekarza w danym dniu
    public WorkingHours getWorkingHours(Doctor doctor, LocalDate date) {
        Map<LocalDate, WorkingHours> doctorSchedule = schedules.get(doctor);
        return doctorSchedule != null ? doctorSchedule.get(date) : null;
    }
}

