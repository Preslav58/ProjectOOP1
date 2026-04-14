package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;

public class Save implements Command {

    /**
     * Команда за запазване на текущия календар.
     * Записва всички промени обратно във файла, от който е зареден календарът.
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        String file = context.getFileName();

        context.getTextStorage().save(context.getCurentCalendar(), file);
        context.setHasUnsavedChanges(false);

        return "Successfully saved " + file;
    }
}
