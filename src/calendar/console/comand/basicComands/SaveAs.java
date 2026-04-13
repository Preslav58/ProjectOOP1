package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.InvalidCommandArgumentsException;

public class SaveAs implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 1) {
            throw new InvalidCommandArgumentsException("Error. No name for saving");
        }

        String newFile = args[0];

        context.getTextStorage().save(context.getCurentCalendar(), newFile);
        context.setFileName(newFile);
        context.setHasUnsavedChanges(false);

        return "Successfully saved " + newFile;
    }
}
