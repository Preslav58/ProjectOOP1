package calendar.console.command.basic_commands;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Calendar;

/**
 * Команда за отваряне на съществуващ или създаване на нов текстов файл за календар.
 * Зарежда данните от файла в текущия контекст на приложението.
 */
public class Open implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception{
        String file = args[0];

        Calendar loadedCalendar = context.getFileManeger().load(file);

        context.setCurrentCalendar(loadedCalendar);
        context.setFileName(file);
        context.setHasUnsavedChanges(false);

        return "Successfully opened " + file;
    }

    @Override
    public boolean requiresOpenedFile() {
        return false;
    }

    @Override
    public int getRequiredArgsCount() {
        return 1;
    }

}
