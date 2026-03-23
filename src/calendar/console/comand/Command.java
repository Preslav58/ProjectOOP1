package calendar.console.comand;

import calendar.console.Context;

public interface Command {
    String execute(String[] args, Context context) throws Exception;

    default boolean requiresOpenedFile() {
        return true;
    }
}
