package calendar.console.command.complex_commands.find_commands;

import calendar.console.command.Command;
import calendar.model.Calendar;
import calendar.model.Event;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Абстрактен базов клас, съдържащ алгоритъма за намиране на свободно време (слот).
 * Преизползва се от командите, които търсят свободни прозорци в един или няколко календара,
 * като съблюдава работното време (08:00 - 17:00) и пропуска почивните дни.
 */
public abstract class BaseFindSlot implements Command {
    /**
     * Търси общ свободен времеви прозорец във всички подадени календари.
     *
     * @param searchDate датата, от която започва търсенето
     * @param hours      желаната продължителност на срещата в часове
     * @param calendars  списък от календари, с които трябва да се синхронизира срещата
     * @return текстово съобщение с намерения резултат или съобщение за неуспех
     */
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

            // Проверяваме дали денят е празник, в който и да е от календарите
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

            // Обединяваме събитията от всички календари за съответния ден
            List<Event> combinedEvents = new ArrayList<>();
            for (Calendar cal : calendars) {
                combinedEvents.addAll(cal.getEventsForDate(searchDate));
            }

            // Сортираме обединения списък хронологично по начален час
            combinedEvents.sort(Comparator.comparing(Event::getStartTime));

            LocalTime currentSlotstart = workStart;
            boolean slotFound = false;

            for (Event event : combinedEvents) {
                if (!event.getEndTime().isAfter(workStart)) continue;
                if (!event.getStartTime().isBefore(workEnd)) continue;

                // Проверяваме дали има достатъчно място преди текущото събитие
                if (currentSlotstart.isBefore(event.getStartTime())) {
                    long gapMinutes = ChronoUnit.MINUTES.between(currentSlotstart, event.getStartTime());
                    if (gapMinutes >= minutes) {
                        slotFound = true;
                        break;
                    }
                }

                // Преместваме началото на свободното време след текущото събитие
                if (event.getEndTime().isAfter(currentSlotstart)) {
                    currentSlotstart = event.getEndTime();
                }
            }

            // Проверяваме дали има достатъчно място след последното събитие за деня
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
