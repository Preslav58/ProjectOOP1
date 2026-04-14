package calendar.console.comand;

import calendar.console.comand.basicComands.*;
import calendar.console.comand.complexCommands.*;
import calendar.console.comand.complexCommands.findCommands.FindSlot;
import calendar.console.comand.complexCommands.findCommands.FindSlotWith;
import calendar.exception.InvalidCommandArgumentsException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класът {@code CommandFactory} отговаря за създаването и управлението на всички команди.
 * Реализира шаблона за дизайн "Factory Pattern" (Фабрика).
 * <p>
 * Чрез използването на речник (Map) за регистрация на командите се спазва
 * принципът "Open/Closed" (Отворен за разширяване, затворен за модификация) от SOLID.
 * Добавянето на нови команди не изисква промяна в основната логика на търсене.
 */
public class CommandFactory {
        private final Map<String, Command> commands;

    /**
     * Създава нова фабрика и регистрира всички поддържани команди в приложението.
     * Всяка команда се свързва с нейния текстов идентификатор (име).
     */
        public CommandFactory() {
            this.commands = new HashMap<>();

            commands.put("open", new Open());
            commands.put("close", new Close());
            commands.put("save", new Save());
            commands.put("save as", new SaveAs());
            commands.put("help", new Help());

            commands.put("book", new Book());
            commands.put("unbook", new UnBook());
            commands.put("holiday", new Holiday());
            commands.put("change", new Change());
            commands.put("agenda", new Agenda());

            commands.put("find", new Find());
            commands.put("busydays", new BusyDays());
            commands.put("findslot", new FindSlot());
            commands.put("findslotwith", new FindSlotWith());
            commands.put("merge", new Merge());
        }

    /**
     * Връща инстанция на команда спрямо подаденото име.
     * Търсенето не прави разлика между малки и главни букви (case-insensitive).
     *
     * @param commandName името на командата, въведено от потребителя (напр. "open", "book")
     * @return обект, имплементиращ интерфейса {@code Command}, готов за изпълнение
     * @throws IllegalArgumentException ако подаденото име на команда не е разпознато (не съществува)
     */
        public Command getCommand(String commandName) {
            Command command = commands.get(commandName.toLowerCase());

            if (command == null) {
                throw new InvalidCommandArgumentsException("Invalid command. Please  use 'help'  for help");
            }

            return command;
        }
    }
