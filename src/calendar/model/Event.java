package calendar.model;

import calendar.exception.InvalidEventTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Клас, който представя събитие в календар.
 * Съдържа дата, начален и краен час, име и допълнителни бележки.
 */
public class Event {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;
    private String notes;

    /**
     * Създава ново събитие с посочените параметри.
     *
     * @param date      датата на събитието
     * @param startTime началният час на събитието
     * @param endTime   крайният час на събитието
     * @param name      името на събитието (не може да бъде null)
     * @param notes     допълнителни бележки (може да бъде празен низ)
     * @throws InvalidEventTimeException ако крайният час е преди началния
     * @throws NullPointerException      ако някой от задължителните параметри е null
     */
    public Event(LocalDate date, LocalTime startTime, LocalTime endTime, String name, String notes) {
        setDate(date);
        setStartTime(startTime);
        setEndTime(endTime);
        setName(name);
        setNotes(notes);
    }

    /**
     * гетъри  и сетари @param date @param startTime @param EndTime @param name cannot be null
     */
    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {
       this.date = Objects.requireNonNull(date, "date cannot be null");
    }

    public LocalTime getStartTime() {return startTime;}
    public void setStartTime(LocalTime startTime) {
        this.startTime = Objects.requireNonNull(startTime, "startTime cannot be null");
        validateTimes();
    }

    public LocalTime getEndTime() {return endTime;}
    public void setEndTime(LocalTime endTime) {
        this.endTime = Objects.requireNonNull(endTime, "endTime cannot be null");
        validateTimes();
    }

    public String getName() {return name;}
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public String getNotes() {return notes;}
    public void setNotes(String notes) {
        this.notes = (notes == null) ? "" : notes;
    }

    /**
     * Проверява дали часовете на събитието са валидни (краят да е след началото).
     */
    private void validateTimes() {
        if (startTime != null && endTime != null) {
            if (!endTime.isAfter(startTime)) {
                throw new InvalidEventTimeException("Error. End time must be after start time");
            }
        }
    }

    /**
     * Проверява дали текущото събитие се застъпва времево с друго подадено събитие.
     *
     * @param otherEvent другото събитие, с което сравняваме
     * @return {@code true} ако има времево застъпване, иначе {@code false}
     */
    public boolean overlap(Event otherEvent) {
        if (!this.date.equals(otherEvent.date)) {
            return false;
        }
        return this.startTime.isBefore(otherEvent.endTime) &&
                this.endTime.isAfter(otherEvent.startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event event)) return false;
        return Objects.equals(date, event.date) &&
                Objects.equals(startTime, event.startTime) &&
                Objects.equals(endTime, event.endTime) &&
                Objects.equals(name, event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, startTime, endTime, name);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s)", date, startTime, endTime, name);
    }
}
