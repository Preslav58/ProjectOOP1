package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;

public class Save implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (!context.isFileOpen()){
            throw new IllegalStateException("Error. No file for saving");
        }

        String file = context.getFileName();

        context.getTextStorage().save(context.getCurentCalendar(), file);
        context.setHasUnsavedChanges(false);

        return "Successfully saved" + file;
    }
}
