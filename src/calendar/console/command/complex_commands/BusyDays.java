package calendar.console.command.complex_commands;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.CalendarException;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Day;
import calendar.model.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Команда за извеждане на статистика за натовареност в даден период.
 * Пресмята общото време (в минути) на всички събития за всеки ден, използвайки
 * {@code HashMap} за групиране. Накрая сортира резултатите в низходящ ред
 * спрямо общия брой заети часове.
 */
public class BusyDays implements Command {
    /**
     * Изчислява натовареността за зададения период и генерира статистика.
     *
     * @param args    масив от аргументи (очаква 2 аргумента - начална и крайна дата)
     * @param context текущият контекст на приложението
     * @return сортиран списък с дните и общите заети часове/минути
     * @throws Exception при невалидни дати или ако крайната е преди началната
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        LocalDate from = LocalDate.parse(args[0]);
        LocalDate to = LocalDate.parse(args[1]);

        if (to.isBefore(from)) {
            throw new CalendarException("Error. To date can't be before from date.");
        }

        Map<LocalDate, Long> dailyBusyMinutes = new HashMap<>();

        for (Day day : context.getCurentCalendar().getDays()) {
            LocalDate eventDate = day.getDate();


            if (!eventDate.isBefore(from) && !eventDate.isAfter(to)) {
                for (Event event : day.getEvents()) {
                    long minutes = event.getTimeInterval().getDurationMinutes();
                    dailyBusyMinutes.put(eventDate, dailyBusyMinutes.getOrDefault(eventDate, 0L) + minutes);
                }
            }
        }

        if (dailyBusyMinutes.isEmpty()) {
            return "No Events for this period.";
        }

        List<Map.Entry<LocalDate, Long>> sortedEntries = new ArrayList<>(dailyBusyMinutes.entrySet());

        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));


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

    /**
     * @return {@code false}, тъй като командата има нужда от 2 или повече параметри
     */
    @Override
    public int getRequiredArgsCount() {
        return 2;
    }

}
