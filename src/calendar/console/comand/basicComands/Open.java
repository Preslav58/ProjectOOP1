package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.model.Calendar;

public class Open implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception{
        if (args.length < 1){
            throw new IllegalArgumentException("Error. Not enough arguments. Usage: open <file>");
        }
        String file = args[0];

        Calendar loadedCalendar = context.getTextStorage().load(file);

        context.setCurentCalendar(loadedCalendar);
        context.setFileName(file);
        context.setHasUnsavedChanges(false);

        return "Successfully opened " + file;
    }
}
