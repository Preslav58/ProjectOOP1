package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.model.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class Change implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 4) {
            throw new IllegalArgumentException("Error. Use change <date> <starttime> <option> <newvalue>");
        }

        LocalDate targetDate = LocalDate.parse(args[0]);
        LocalTime targetStartTime = LocalTime.parse(args[1]);
        String option = args[2].toLowerCase();

        StringBuilder newBuilder = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            newBuilder.append(args[i]).append(" ");
        }
        String newValue = newBuilder.toString().trim();

        Event oldEvent = context.getCurentCalendar().getEvent(targetDate, targetStartTime);
        if (oldEvent == null) {
            throw new IllegalArgumentException("Event is null.");
        }

        LocalDate newDate = oldEvent.getDate();
        LocalTime newStartTime = oldEvent.getStartTime();
        LocalTime newEndTime = oldEvent.getEndTime();
        String newName = oldEvent.getName();
        String newNote = oldEvent.getNotes();

        switch (option) {
            case "date" : newDate = LocalDate.parse(newValue); break;
            case "starttime" : newStartTime = LocalTime.parse(newValue); break;
            case "endtime" : newEndTime = LocalTime.parse(newValue); break;
            case "name" : newName = newValue; break;
            case "note" : newNote = newValue; break;

            default : throw new IllegalArgumentException("Error. Invalid option." + option + "Choose from : date, starttime, endtime, name, note.");
        }

        context.getCurentCalendar().deleteEvent(oldEvent.getDate(), oldEvent.getStartTime(), oldEvent.getEndTime());

        try {
            Event updatedEvent = new Event(newDate, newStartTime, newEndTime, newName, newNote);
            context.getCurentCalendar().addEvent(updatedEvent);

            context.setHasUnsavedChanges(true);
            return "Successfully changed Event " + updatedEvent.toString();

        } catch (Exception e) {
            context.getCurentCalendar().addEvent(oldEvent);
            throw new IllegalArgumentException("Change failed: " + e.getMessage());
        }
    }
}
