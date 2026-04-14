package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Event;

/**
 * Команда за търсене на събития по ключова дума.
 * Търси подадения низ в името или бележките на всички събития
 * без да прави разлика между малки и главни букви (case-insensitive).
 */
public class Find implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 1) {
            throw new InvalidCommandArgumentsException("Error. Not enough arguments. Please use find <string> ");
        }

        String keyword = String.join(" ", args).toLowerCase();

        StringBuilder builder = new StringBuilder();
        for(Event event : context.getCurentCalendar().getEvents()) {
            String name = event.getName().toLowerCase();
            String note = event.getNotes().toLowerCase();

            if(name.contains(keyword) || note.contains(keyword)) {
                builder.append(event.toString()).append("\n");
            }
        }

        if (builder.isEmpty()) {
            return "No events found";
        }

        return builder.toString();
    }
}
