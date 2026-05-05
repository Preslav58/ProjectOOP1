package calendar.model;

import calendar.exception.EventConflictException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Класът {@code Calendar} служи като контейнер за всички събития и неработни дни.
 * Осигурява операции за добавяне, изтриване, търсене и извличане на събития,
 * като делегира същинската логика на обектите от тип {@code Day}.
 */
public class Calendar {
    private final Map<LocalDate, Day> days;

    /**
     * Създава празен календар без събития и почивни дни.
     * Използва {@code TreeMap}, за да поддържа дните сортирани хронологично.
     */
    public Calendar() {
        this.days = new TreeMap<>();
    }

    /**
     * Помощен метод, който връща обекта {@code Day} за подадената дата.
     * Ако денят не съществува в календара, той бива създаден.
     *
     * @param date датата, за която търсим деня
     * @return обектът {@code Day}, отговарящ на датата
     */
    private Day getOrCreateDay(LocalDate date) {
        return days.computeIfAbsent(date, d -> new Day(date));
    }

    /**
     * Добавя ново събитие в календара.
     * Проверява дали събитието не се застъпва с вече съществуващо в същия ден.
     *
     * @param nEvent събитието за добавяне (не трябва да е null)
     * @throws EventConflictException ако има времево застъпване с друго събитие
     */
    public void addEvent(LocalDate date, Event nEvent) {
        Day day = getOrCreateDay(date);
        day.addEvent(nEvent);
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
        Day day = days.get(date);

        if (day != null) {
            return day.deleteEvent(startTime, endTime);
        }

        return false;
    }

    /**
     * Добавя нов почивен ден в календара.
     *
     * @param date дата, която ще бъде маркирана като неработен ден
     */
    public void addHoliday(LocalDate date) {
       Day day = getOrCreateDay(date);
       day.setHoliday(true);
    }

    /**
     * Премахва дата от списъка с почивни дни.
     *
     * @param date датата, чийто статус на почивен ден ще бъде премахнат
     */
    public void removeHoliday(LocalDate date) {
        Day day = days.get(date);
        if (day != null) {
            day.setHoliday(false);
        }
    }

    /**
     * Търси събитие по дата и начален час.
     *
     * @param date дата на събитието
     * @param startTime начален час
     * @return намереното събитие или {@code null}, ако няма такова
     */
    public Event getEvent(LocalDate date, LocalTime startTime) {
       Day day = days.get(date);

       if (day != null) {
           for (Event event : day.getEvents()) {
               if (event.getStartTime().equals(startTime)) {
                   return event;
               }
           }
       }

       return null;
    }

    /**
     * Връща абсолютно всички събития от всички дни в календара.
     *
     * @return списък със всички събития
     */
    public Collection<Day> getDays() {
        return Collections.unmodifiableCollection(days.values());
    }

    /**
     * Връща всички почивни дни от календара.
     *
     * @return множество от дати, отбелязани като почивни
     */
    public Set<LocalDate> getHolidays() {
        Set<LocalDate> holidays = new HashSet<>();

        for (Day day : days.values()) {
            if (day.isHoliday()) {
                holidays.add(day.getDate());
            }
        }

        return holidays;
    }

    /**
     * Връща всички събития за конкретна дата.
     *
     * @param date датата, за която се търсят събития
     * @return хронологично подреден списък със събития за деня (празен списък, ако няма)
     */
    public List<Event> getEventsForDate(LocalDate date) {
        Day day = days.get(date);
        return (day != null) ? day.getEvents() : new ArrayList<>();
    }
}
