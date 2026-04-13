package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.InvalidCommandArgumentsException;

import java.time.LocalDate;

public class Holiday implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 1) {
            throw new InvalidCommandArgumentsException("Error. No date. Please use holiday <date>");
        }

        LocalDate date = LocalDate.parse(args[0]);

        context.getCurentCalendar().addHoliday(date);
        context.setHasUnsavedChanges(true);

        return date.toString() + " successfully marked as a holiday.";
    }
}
