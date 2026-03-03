package calendar.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Calendar {
    private List<Event> events;
    private Set<LocalDate> holidays;

    public Calendar() {
        this.events = new ArrayList<>();
        this.holidays = new HashSet<>();
    }

    public void addEvent(Event nEvent) {
        for (Event eEvent : events) {
            if (eEvent.overlap(nEvent)) {
                throw new IllegalArgumentException("Event overlaps with existing Event" +  eEvent.getName());
            }
        }
        events.add(nEvent);
        events.sort(Comparator.comparing(Event::getDate).thenComparing(Event::getStartTime));
    }

    public boolean deleteEvent(LocalDate date,  LocalTime startTime, LocalTime endTime) {
        return events.removeIf(event -> event.getDate().equals(date) &&
                event.getStartTime().equals(startTime) &&
                event.getEndTime().equals(endTime));
    }

    public void addHoliday(LocalDate date) {
        holidays.add(date);
    }

    public void removeHoliday(LocalDate date) {
        holidays.remove(date);
    }

    public boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public Set<LocalDate> getHolidays() {
        return Collections.unmodifiableSet(holidays);
    }

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
