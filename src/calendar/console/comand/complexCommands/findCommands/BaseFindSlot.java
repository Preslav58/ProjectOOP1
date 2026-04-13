package calendar.console.comand.complexCommands.findCommands;

import calendar.console.comand.Command;
import calendar.model.Calendar;
import calendar.model.Event;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class BaseFindSlot implements Command {

    protected String findCommonSlot(LocalDate searchDate, int hours, List<Calendar> calendars) {
        long minutes = hours * 60L;
        LocalTime workStart = LocalTime.of(8, 0);
        LocalTime workEnd = LocalTime.of(17, 0);
        int limitDays = 365;

        while (limitDays > 0) {
            DayOfWeek day = searchDate.getDayOfWeek();

            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                searchDate = searchDate.plusDays(1);
                limitDays--;
                continue;
            }

            boolean isHoliday = false;

            for (Calendar cal : calendars) {
                if (cal.getHolidays().contains(searchDate)) {
                    isHoliday = true;
                    break;
                }
            }

            if (isHoliday) {
                searchDate = searchDate.plusDays(1);
                limitDays--;
                continue;
            }

            List<Event> combinedEvents = new ArrayList<>();

            for (Calendar cal : calendars) {
                combinedEvents.addAll(cal.getEventsForDate(searchDate));
            }

            combinedEvents.sort(Comparator.comparing(Event::getStartTime));

            LocalTime currentSlotstart = workStart;
            boolean slotFound = false;

            for (Event event : combinedEvents) {
                if (!event.getEndTime().isAfter(workStart)) continue;
                if (!event.getStartTime().isBefore(workEnd)) continue;

                if (currentSlotstart.isBefore(event.getStartTime())) {
                    long gapMinutes = ChronoUnit.MINUTES.between(currentSlotstart, event.getStartTime());
                    if (gapMinutes >= minutes) {
                        slotFound = true;
                        break;
                    }
                }

                if (event.getEndTime().isAfter(currentSlotstart)) {
                    currentSlotstart = event.getEndTime();
                }
            }

            if (!slotFound && currentSlotstart.isBefore(workEnd)) {
                long gapMinutes = ChronoUnit.MINUTES.between(currentSlotstart, workEnd);
                if (gapMinutes >= minutes) {
                    slotFound = true;
                }
            }

            if (slotFound) {
                LocalTime slotEnd = currentSlotstart.plusMinutes(minutes);
                return String.format("Slot found at %s from %s to %s", searchDate, currentSlotstart, slotEnd);
            }

            searchDate = searchDate.plusDays(1);
            limitDays--;
        }

        return "No slot found for this year";
    }
}
