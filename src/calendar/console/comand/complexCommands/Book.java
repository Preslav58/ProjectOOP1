package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.model.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class Book implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 4) {
            throw new IllegalArgumentException("Error. Too few arguments.");
        }

        LocalDate date = LocalDate.parse(args[0]);
        LocalTime startTime = LocalTime.parse(args[1]);
        LocalTime endTime = LocalTime.parse(args[2]);
        String name = args[3];

        String note = "";
        if (args.length > 4) {
            StringBuilder noteBuilder = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                noteBuilder.append(args[i]).append(" ");
            }
            note = noteBuilder.toString().trim();
        }

        Event newEvent = new Event(date, startTime, endTime, name, note);
        context.getCurentCalendar().addEvent(newEvent);
        context.setHasUnsavedChanges(true);

        return "Successfully booked hour for: " + newEvent.toString();
    }
}
