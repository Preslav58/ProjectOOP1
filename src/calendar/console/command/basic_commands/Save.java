package calendar.console.command.basic_commands;

import calendar.console.Context;
import calendar.console.command.Command;

/**
 * Команда за запазване на текущия календар.
 * Записва всички промени обратно във файла, от който е зареден календарът.
 */
public class Save implements Command {
    /**
     * Изпълнява логиката по запазване на календара в текущия файл.
     *
     * @param args    масив от аргументи (не се очакват аргументи за тази команда)
     * @param context текущият контекст на приложението
     * @return съобщение за успешно запазване
     * @throws Exception при проблем с писането във файла
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        String file = context.getFileName();

        context.getFileManeger().save(context.getCurentCalendar(), file);
        context.setHasUnsavedChanges(false);

        return "Successfully saved " + file;
    }
}
