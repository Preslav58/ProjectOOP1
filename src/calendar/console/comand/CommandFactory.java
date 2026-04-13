package calendar.console.comand;

import calendar.console.comand.basicComands.*;
import calendar.console.comand.complexCommands.*;
import calendar.console.comand.complexCommands.findCommands.FindSlot;
import calendar.console.comand.complexCommands.findCommands.FindSlotWith;
import calendar.exception.InvalidCommandArgumentsException;

public class CommandFactory {
    public static Command getCommand(String command) {
        return switch (command.toLowerCase()) {
            case "help" -> new Help();
            case "open" -> new Open();
            case "close" -> new Close();
            case "save" -> new Save();
            case "save as" -> new SaveAs();
            case "book" -> new Book();
            case "unbook" -> new UnBook();
            case "agenda" -> new Agenda();
            case "change" -> new Change();
            case "find" -> new Find();
            case "holiday" -> new Holiday();
            case "busydays" -> new BusyDays();
            case "findslot" -> new FindSlot();
            case "findslotwith" -> new FindSlotWith();
            case "merge" -> new Merge();

            default -> throw new InvalidCommandArgumentsException("Invalid command. Type 'help' for help.");
        };
    }
}
