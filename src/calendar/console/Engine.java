package calendar.console;

import calendar.console.comand.Command;
import calendar.console.comand.CommandFactory;
import calendar.exception.FileNotOpenedException;
import java.util.Scanner;

/**
 * Класът {@code Engine} управлява основния цикъл на приложението.
 * Отговаря за четене на вход от потребителя, разпознаване на команди
 * и тяхното изпълнение чрез {@code CommandFactory}.
 */
public class Engine {
    private final Context context;
    private final CommandFactory commandFactory;

    /**
     * Създава нов Engine с подаден контекст и фабрика за команди.
     *
     * @param context текущото състояние на приложението
     * @param commandFactory фабрика за създаване на команди
     */
    public Engine(Context context, CommandFactory commandFactory) {
        this.context = context;
        this.commandFactory = commandFactory;
    }

    /**
     * Стартира основния цикъл на програмата.
     * Чете команди от конзолата и ги изпълнява,
     * докато потребителят не въведе "exit".
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        context.setScanner(scanner);
        System.out.println("Welcome to calendar! Please enter command: ");

        // Основен цикъл за обработка на команди
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // Пропускаме празни входове
            if (input.isEmpty()) {
                continue;
            }

            // Изход от програмата
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            try {
                String commandName;
                String[] args;

                // Специална обработка за команда "save as"
                if (input.toLowerCase().startsWith("save as")) {
                    commandName = "save as";
                    String rest = input.substring(7).trim();
                    args = rest.isEmpty() ? new String[0] : new String [] {rest.replace("\"","")};
                } else {
                    // Разделяне на входа на команда и аргументи
                    String[] parts = input.split("\\s+");
                    commandName = parts[0].toLowerCase();

                    args = new String [parts.length - 1];
                    System.arraycopy(parts, 1, args, 0, parts.length - 1);
                }

                // Създаване на команда чрез фабрика
                Command command = commandFactory.getCommand(commandName);

                // Проверка дали е отворен файл при нужда
                if (command.requiresOpenedFile() && !context.isFileOpen()) {
                    throw new FileNotOpenedException();
                }

                // Изпълнение на командата
                String result = command.execute(args, context);
                System.out.println(result);

            } catch (Exception e) {
                System.out.println("Invalid command: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
