package calendar.console.comand;

import calendar.console.comand.basicComands.Close;
import calendar.console.comand.basicComands.Open;
import calendar.console.comand.basicComands.Save;
import calendar.console.comand.basicComands.SaveAs;

public class CommandFactory {
    public static Command getCommand(String command) {
        switch (command.toLowerCase()) {
            case "open": return new Open();
            case "close": return new Close();
            case "save": return new Save();
            case "save as": return new SaveAs();

            default : throw new IllegalArgumentException("Invalid command. Type 'help' for help.");
        }
    }
}
