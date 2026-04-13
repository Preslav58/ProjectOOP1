package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.InvalidCommandArgumentsException;

import java.time.LocalDate;
import java.time.LocalTime;

public class UnBook implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length != 3) {
            throw new InvalidCommandArgumentsException("Error. Wrong number of arguments");
        }

        LocalDate date = LocalDate.parse(args[0]);
        LocalTime startTime = LocalTime.parse(args[1]);
        LocalTime endTime = LocalTime.parse(args[2]);

        boolean isDeleted = context.getCurentCalendar().deleteEvent(date, startTime, endTime);

        if (isDeleted) {
            context.setHasUnsavedChanges(true);
            return String.format("Event %s from %s to %s has been deleted", date.toString(), startTime.toString(), endTime.toString());
        } else {
            return "Event on this date or hour hasn't been found";
        }
    }
}
