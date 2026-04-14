package calendar.console.comand.basicComands;

import calendar.console.Context;
import calendar.console.comand.Command;

public class Close implements Command {

    /**
     * Команда за затваряне на текущия календар.
     * Изчиства данните от паметта на приложението, без да записва незапазените промени.
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
