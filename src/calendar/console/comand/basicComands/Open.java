package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Calendar;

/**
 * Команда за отваряне на съществуващ или създаване на нов текстов файл за календар.
 * Зарежда данните от файла в текущия контекст на приложението.
 */
public class Open implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception{
        if (args.length < 1){
            throw new InvalidCommandArgumentsException("Error. Not enough arguments. Usage: open <file>");
        }
        String file = args[0];

        Calendar loadedCalendar = context.getTextStorage().load(file);

        context.setCurrentCalendar(loadedCalendar);
        context.setFileName(file);
        context.setHasUnsavedChanges(false);

        return "Successfully opened " + file;
    }

    @Override
    public boolean requiresOpenedFile() {
        return false;
    }
}
