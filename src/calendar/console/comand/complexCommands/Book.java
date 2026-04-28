package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Event;
import calendar.model.TimeInterval;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Команда за запазване на час за нова среща (събитие).
 * Създава обект {@code TimeInterval} за управление на времето и го подава
 * за валидация към календара. Календарът отговаря за проверката за застъпване
 * (overlap) с вече съществуващи ангажименти в рамките на съответния ден.
 */
public class Book implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 4) {
            throw new InvalidCommandArgumentsException("Error. Too few arguments. Please use book <date> <starttime> <endtime> <name> (<note>)");
        }

        LocalDate date = LocalDate.parse(args[0]);
        LocalTime startTime = LocalTime.parse(args[1]);
        LocalTime endTime = LocalTime.parse(args[2]);
        String name = args[3];

        String note = "";
        if (args.length > 4) {
            StringBuilder noteBuilder = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                noteBuilder.append(args[i]).append(" ");
            }
            note = noteBuilder.toString().trim();
        }

        TimeInterval timeInterval = new TimeInterval(startTime, endTime);
        Event newEvent = new Event(date, timeInterval, name, note);
        context.getCurentCalendar().addEvent(newEvent);
        context.setHasUnsavedChanges(true);

        return "Successfully booked hour for: " + newEvent.toString();
    }
}
