package calendar.model;

import calendar.exception.EventConflictException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Обект, представляващ един конкретен ден от календара.
 * Съхранява информация дали денят е почивен и управлява списъка със събития за него.
 */
public class Day {
    private final LocalDate date;
    private boolean isHoliday;
    private final List<Event> events;

    /**
     * Създава нов ден със зададена дата. По подразбиране денят не е почивен.
     *
     * @param date датата на този ден
     */
    public Day(LocalDate date) {
        this.date = date;
        this.events = new ArrayList<>();
        this.isHoliday = false;
    }

    public LocalDate getDate() {return date;}
    public boolean isHoliday() {return isHoliday;}
    public void setHoliday(boolean holiday) {
        this.isHoliday = holiday;
    }

    /**
     * Добавя ново събитие към този ден.
     * Извършва се проверка за застъпване на часовете и списъкът се поддържа сортиран.
     *
     * @param newEvent събитието, което да бъде добавено
     * @throws EventConflictException ако събитието се застъпва с вече съществуващо в този ден
     */
    public void addEvent(Event newEvent) {
        for(Event existingEvent : events) {
            if(existingEvent.overlap(newEvent)){
                throw new EventConflictException("Event overlaps with existing event: " + existingEvent.getName());
            }
        }
        events.add(newEvent);
        events.sort(Comparator.comparing(Event::getStartTime));
    }

    /**
     * Изтрива събитие от този ден по зададен начален и краен час.
     *
     * @param startTime начален час на събитието
     * @param endTime краен час на събитието
     * @return {@code true} ако събитието е намерено и изтрито, иначе {@code false}
     */
    public boolean deleteEvent(LocalTime startTime,  LocalTime endTime) {
        return events.removeIf(event -> event.getStartTime().equals(startTime) && event.getEndTime().equals(endTime));
    }

    /**
     * Връща събитията за този ден като колекция само за четене.
     *
     * @return неизменяем списък със събития
     */
    public List<Event> getEvents() {return Collections.unmodifiableList(events);}
}
