package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;

public class Close implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (!context.isFileOpen()) {
            throw new IllegalStateException("Error. No file for close command.");
        }

        String file = context.getFileName();

        context.setCurentCalendar(null);
        context.setFileName(null);
        context.setHasUnsavedChanges(false);

        return "Successfully closed" + file;
    }
}
