package calendar.model;

import calendar.exception.EventConflictException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Класът {@code Calendar} служи като контейнер за всички събития и неработни дни.
 * Осигурява операции за добавяне, изтриване, търсене и извличане на събития,
 * както и управление на почивни дни.
 */
public class Calendar {
    private final List<Event> events;
    private final Set<LocalDate> holidays;

    /**
     * Създава празен календар без събития и почивни дни.
     */
    public Calendar() {
        this.events = new ArrayList<>();
        this.holidays = new HashSet<>();
    }

    /**
     * Добавя ново събитие в календара.
     * Проверява дали събитието не се застъпва с вече съществуващо.
     * След добавяне, списъкът се сортира по дата и начален час.
     *
     * @param nEvent събитието за добавяне (не трябва да е null)
     * @throws EventConflictException ако има застъпване с друго събитие
     */
    public void addEvent(Event nEvent) {
        for (Event existingEvent : events) {
            if (existingEvent.overlap(nEvent)) {
                throw new EventConflictException("Event overlaps with existing Event" +  existingEvent.getName());
            }
        }
        events.add(nEvent);
        events.sort(Comparator.comparing(Event::getDate).thenComparing(Event::getStartTime));
    }

    /**
     * Изтрива събитие по зададени дата, начален и краен час.
     *
     * @param date дата на събитието
     * @param startTime начален час
     * @param endTime краен час
     * @return {@code true} ако е намерено и изтрито събитие, иначе {@code false}
     */
    public boolean deleteEvent(LocalDate date,  LocalTime startTime, LocalTime endTime) {
        return events.removeIf(event -> event.getDate().equals(date) &&
                event.getStartTime().equals(startTime) &&
                event.getEndTime().equals(endTime));
    }

    /**
     * Добавя нов почивен ден.
     *
     * @param date дата, която ще бъде маркирана като неработен ден
     */
    public void addHoliday(LocalDate date) {
        holidays.add(date);
    }

    /**
     * Премахва дата от списъка с почивни дни.
     *
     * @param date датата, която ще бъде премахната
     */
    public void removeHoliday(LocalDate date) {
        holidays.remove(date);
    }

    /**
     * Проверява дали дадена дата е почивен ден.
     *
     * @param date проверяваната дата
     * @return {@code true} ако е почивен ден, иначе {@code false}
     */
    public boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    /**
     * Връща всички събития като неизменяем списък.
     * Промените върху върнатата колекция не са позволени.
     *
     * @return списък със всички събития
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Връща всички почивни дни като неизменяемо множество.
     *
     * @return множество от почивни дни
     */
    public Set<LocalDate> getHolidays() {
        return Collections.unmodifiableSet(holidays);
    }

    /**
     * Търси събитие по дата и начален час.
     *
     * @param date дата на събитието
     * @param startTime начален час
     * @return намереното събитие или {@code null}, ако няма такова
     */
    public Event getEvent(LocalDate date, LocalTime startTime) {
        for (Event event : events) {
            if (event.getDate().equals(date) && event.getStartTime().equals(startTime)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Връща всички събития за дадена дата.
     *
     * @param date датата, за която се търсят събития
     * @return списък със събития за деня (в хронологичен ред)
     */
    public List<Event> getEventsForDate(LocalDate date) {
        List<Event> dailyEvents = new ArrayList<>();
        for (Event E : events) {
            if (E.getDate().equals(date)) {
                dailyEvents.add(E);
            }
        }
        return dailyEvents;
    }
}
