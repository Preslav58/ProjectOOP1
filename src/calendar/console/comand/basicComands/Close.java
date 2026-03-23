package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;

public class Close implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        String file = context.getFileName();

        context.setCurentCalendar(null);
        context.setFileName(null);
        context.setHasUnsavedChanges(false);

        return "Successfully closed " + file;
    }
}
