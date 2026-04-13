package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Event;

import java.time.LocalDate;
import java.util.List;

public class Agenda implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 1) {
            throw new InvalidCommandArgumentsException("Error. Please use agenda <date>");
        }

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

        return builder.toString();
    }
}
