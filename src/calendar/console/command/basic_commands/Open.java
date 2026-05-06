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
    /**
     * Изпълнява логиката по зареждане на файла.
     *
     * @param args    масив от аргументи (очаква се 1 аргумент - име на файл)
     * @param context текущият контекст на приложението
     * @return съобщение за успешно отваряне
     * @throws Exception при грешка в четенето на файла
     */
    @Override
    public String execute(String[] args, Context context) throws Exception{
        String file = args[0];

        Calendar loadedCalendar = context.getFileManeger().load(file);

        context.setCurrentCalendar(loadedCalendar);
        context.setFileName(file);
        context.setHasUnsavedChanges(false);

        return "Successfully opened " + file;
    }

    /**
     * @return {@code false}, тъй като командата отваря файл и не изисква такъв да е вече отворен
     */
    @Override
    public boolean requiresOpenedFile() {
        return false;
    }

    /**
     * @return {@code false}, тъй като командата има нужда от един или повече параметри
     */
    @Override
    public int getRequiredArgsCount() {
        return 1;
    }

}
