package calendar.console.command.complex_commands;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Event;

import java.time.LocalDate;
import java.util.List;

/**
 * Команда за преглед на дневната програма.
 * Извежда списък с всички ангажименти за посочена от потребителя дата.
 * Данните се извличат директно от съответния обект {@code Day} в календара,
 * което гарантира, че те вече са подредени в хронологичен ред.
 */
public class Agenda implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception {
        LocalDate date = LocalDate.parse(args[0]);

        List<Event> dailyEvents = context.getCurentCalendar().getEventsForDate(date);

        if (dailyEvents.isEmpty()) {
            return "No daily events found for given date";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Events for ").append(date.toString()).append("\n");

        for (Event event : dailyEvents) {
            builder.append(event.toString()).append("\n");
        }

        return builder.toString().trim();
    }

    @Override
    public int getRequiredArgsCount() {
        return 1;
    }

}
