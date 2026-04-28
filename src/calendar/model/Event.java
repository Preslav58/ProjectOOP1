package calendar.model;

import calendar.exception.InvalidEventTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Клас, който представя единично събитие в календара.
 * Съдържа информация за дата, времеви интервал, име и допълнителни бележки.
 */
public class Event {
    private LocalDate date;
    private TimeInterval timeInterval;
    private String name;
    private String notes;

    /**
     * Създава ново събитие с посочените параметри.
     *
     * @param date         датата на събитието
     * @param timeInterval времевият интервал (начален и краен час)
     * @param name         името на събитието
     * @param notes        допълнителни бележки (може да бъде празен низ)
     * @throws NullPointerException ако някой от задължителните параметри е null
     */
    public Event(LocalDate date, TimeInterval timeInterval, String name, String notes) {
        setDate(date);
        setTimeInterval(timeInterval);
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

    public TimeInterval getTimeInterval() {return timeInterval;}
    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = Objects.requireNonNull(timeInterval, "TimeInterval cannot be null");
    }

    public LocalTime getStartTime() {return timeInterval.getStartTime();}
    public LocalTime getEndTime() {return timeInterval.getEndTime();}

    public String getName() {return name;}
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public String getNotes() {return notes;}
    public void setNotes(String notes) {
        this.notes = (notes == null) ? "" : notes;
    }

    /**
     * Проверява дали текущото събитие се застъпва времево с друго подадено събитие.
     * Логиката за сравнение е делегирана на обекта {@code TimeInterval}.
     *
     * @param otherEvent другото събитие, с което сравняваме
     * @return {@code true} ако има времево застъпване, иначе {@code false}
     */
    public boolean overlap(Event otherEvent) {
        if (!this.date.equals(otherEvent.date)) {
            return false;
        }
        return this.timeInterval.overlapsWith(otherEvent.timeInterval);
    }

    /**
     * Equals, hashcode и toString.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event event)) return false;
        return Objects.equals(date, event.date) &&
                Objects.equals(timeInterval, event.timeInterval) &&
                Objects.equals(name, event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, timeInterval, name);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", date, timeInterval.toString() , name);
    }
}
