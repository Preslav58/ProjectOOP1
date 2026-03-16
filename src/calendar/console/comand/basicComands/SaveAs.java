package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;

public class SaveAs implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (!context.isFileOpen()) {
            throw new IllegalStateException("Error. No file for saving");
        }
        if (args.length < 1) {
            throw new IllegalArgumentException("Error. No name for saving");
        }

        String newFile = args[0];

        context.getTextStorage().save(context.getCurentCalendar(), newFile);
        context.setFileName(newFile);
        context.setHasUnsavedChanges(false);

        return "Successfully saved" + newFile;
    }
}
