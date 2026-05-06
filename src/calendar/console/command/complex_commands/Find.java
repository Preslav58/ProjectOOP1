package calendar.console.command.complex_commands;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Day;
import calendar.model.Event;

/**
 * Команда за търсене на събития по ключова дума.
 * Агрегира всички събития от календара и търси подадения низ в тяхното име
 * или бележки, без да прави разлика между малки и главни букви (case-insensitive).
 */
public class Find implements Command {
    /**
     * Търси по ключова дума във всички събития.
     *
     * @param args    очаква се 1 аргумент (дума или израз за търсене)
     * @param context текущият контекст
     * @return списък с намерените събития или съобщение, че няма такива
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        String keyword = String.join(" ", args).toLowerCase();

        StringBuilder builder = new StringBuilder();

        for(Day day : context.getCurentCalendar().getDays()){
            for(Event event : day.getEvents()) {
                String name = event.getName().toLowerCase();
                String note = event.getNotes().toLowerCase();

                if (name.contains(keyword) || note.contains(keyword)) {
                    builder.append("[").append(day.getDate()).append("] ").append(event.toString()).append("\n");
                }
            }
        }

        if (builder.isEmpty()) {
            return "No events found";
        }

        return builder.toString().trim();
    }

    /**
     * @return {@code false}, тъй като командата има нужда от един или повече параметри
     */
    @Override
    public int getRequiredArgsCount() {
        return 1;
    }

}
