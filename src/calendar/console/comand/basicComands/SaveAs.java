package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.InvalidCommandArgumentsException;

/**
 * Команда за запазване на текущия календар в нов файл.
 * Потребителят задава ново име или път до файла, където да бъдат записани данните.
 */
public class SaveAs implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 1) {
            throw new InvalidCommandArgumentsException("Error. No name for saving. Please use save as <file>");
        }

        String newFile = args[0];

        context.getFileManeger().save(context.getCurentCalendar(), newFile);
        context.setFileName(newFile);
        context.setHasUnsavedChanges(false);

        return "Successfully saved " + newFile;
    }
}
