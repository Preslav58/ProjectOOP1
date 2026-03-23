package calendar.console.comand;

import calendar.console.comand.basicComands.*;
import calendar.console.comand.complexCommands.*;

public class CommandFactory {
    public static Command getCommand(String command) {
        switch (command.toLowerCase()) {
            case "open": return new Open();
            case "close": return new Close();
            case "save": return new Save();
            case "save as": return new SaveAs();
            case "book": return new Book();
            case "unbook" : return new UnBook();
            case "agenda": return new Agenda();
            case "change": return new Change();
            case "find": return new Find();
            case "holiday": return new Holiday();
            case "busydays": return new BusyDays();

            default : throw new IllegalArgumentException("Invalid command. Type 'help' for help.");
        }
    }
}
