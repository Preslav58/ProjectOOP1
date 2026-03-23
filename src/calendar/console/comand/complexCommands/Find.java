package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.model.Event;

public class Find implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 1) {
            throw new Exception("Error. Not enough arguments");
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
