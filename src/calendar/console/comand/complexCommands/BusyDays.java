package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.CalendarException;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Event;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusyDays implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 2) {
            throw new InvalidCommandArgumentsException("Error. Not enough arguments. Please use busydays <from> <to>");
        }

        LocalDate from = LocalDate.parse(args[0]);
        LocalDate to = LocalDate.parse(args[1]);

        if (to.isBefore(from)) {
            throw new CalendarException("Error. To date can't be before from date.");
        }

        Map<LocalDate, Long> dailyBusyMinutes = new HashMap<>();

        for (Event event : context.getCurentCalendar().getEvents()) {
            LocalDate eventDate = event.getDate();

            boolean isAfterOrEqual = !eventDate.isBefore(from);
            boolean isBeforeOrEqual = !eventDate.isAfter(to);

            if (isAfterOrEqual && isBeforeOrEqual) {
                long minutes = ChronoUnit.MINUTES.between(event.getStartTime(), event.getEndTime());
                dailyBusyMinutes.put(eventDate, dailyBusyMinutes.getOrDefault(eventDate, 0L) + minutes);
            }
        }

        if (dailyBusyMinutes.isEmpty()) {
            return "No Events for this period.";
        }

        List<Map.Entry<LocalDate, Long>> sortedEntries = new ArrayList<>(dailyBusyMinutes.entrySet());

        sortedEntries.sort((entry1, entry2) -> {
            return entry2.getValue().compareTo(entry1.getValue());
        });

        StringBuilder result = new StringBuilder();
        result.append("Load statistics (").append(from).append(" to ").append(to).append(")\n");

        for (Map.Entry<LocalDate, Long> entry : sortedEntries) {
            LocalDate date = entry.getKey();
            long totalMinutes = entry.getValue();

            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;

            result.append(String.format("%s (%s) - %d hours and %d minutes\n", date, date.getDayOfWeek() , hours, minutes));
        }

        return result.toString().trim();
    }
}
