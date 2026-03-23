package calendar.console;

import calendar.console.comand.Command;
import calendar.console.comand.CommandFactory;

import java.util.Scanner;

public class Engine {
    private Context context;

    public Engine(Context context) {
        this.context = context;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to calendar! Please enter command: ");

        while (true) {
            System.out.println("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            try {
                String commandName;
                String[] args;

                if (input.toLowerCase().startsWith("save as")) {
                    commandName = "save as";
                    String rest = input.substring(7).trim();
                    args = rest.isEmpty() ? new String[0] : new String [] {rest.replace("\"","")};
                } else {
                    String[] parts = input.split("\\s+");
                    commandName = parts[0].toLowerCase();

                    args = new String [parts.length - 1];
                    System.arraycopy(parts, 1, args, 0, parts.length - 1);
                }

                Command command = CommandFactory.getCommand(commandName);

                if (command.requiresOpenedFile() && !context.isFileOpen()) {
                    throw new IllegalStateException("No file is currently open.");
                }

                String result = command.execute(args, context);
                System.out.println(result);

            } catch (Exception e) {
                System.out.println("Invalid command: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
