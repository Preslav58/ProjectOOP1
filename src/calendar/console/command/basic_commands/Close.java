package calendar.console.command.basic_commands;

import calendar.console.Context;
import calendar.console.command.Command;

/**
 * Команда за затваряне на текущия календар.
 * Изчиства данните от паметта на приложението, без да записва незапазените промени.
 */
public class Close implements Command {
    /**
     * Изпълнява логиката по затваряне на текущия файл.
     *
     * @param args    масив от аргументи (не се очакват аргументи за тази команда)
     * @param context текущият контекст на приложението
     * @return съобщение за успешно затваряне
     * @throws Exception ако възникне непредвидена грешка
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        String file = context.getFileName();

        context.setCurrentCalendar(null);
        context.setFileName(null);
        context.setHasUnsavedChanges(false);

        return "Successfully closed " + file;
    }
}
