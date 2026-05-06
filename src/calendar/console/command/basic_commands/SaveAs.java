package calendar.console.command.basic_commands;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.InvalidCommandArgumentsException;

/**
 * Команда за запазване на текущия календар в нов файл.
 * Потребителят задава ново име или път до файла, където да бъдат записани данните.
 */
public class SaveAs implements Command {
    /**
     * Изпълнява логиката по запазване в нов файл.
     *
     * @param args    масив от аргументи (очаква се 1 аргумент - ново име на файл)
     * @param context текущият контекст на приложението
     * @return съобщение за успешно запазване
     * @throws Exception при проблем с писането във файла
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        String newFile = args[0];

        context.getFileManeger().save(context.getCurentCalendar(), newFile);
        context.setFileName(newFile);
        context.setHasUnsavedChanges(false);

        return "Successfully saved " + newFile;
    }

    /**
     * @return {@code false}, тъй като командата има нужда от един или повече параметри
     */
    @Override
    public int getRequiredArgsCount() {
        return 1;
    }

}
