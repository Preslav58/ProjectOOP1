package calendar.console.command.complex_commands.change_command;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Event;
import calendar.model.TimeInterval;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Команда за промяна на детайли на съществуващо събитие.
 * Позволява редакция на дата, начален час, краен час, име или бележка.
 * Прилага транзакционен модел (rollback) - при неуспешна промяна
 * (напр. конфликт с друго събитие), възстановява оригиналните данни.
 */
public class Change implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception {
        LocalDate targetDate = LocalDate.parse(args[0]);
        LocalTime targetStartTime = LocalTime.parse(args[1]);
        ChangeOption option = ChangeOption.fromString(args[2].trim());

        StringBuilder newBuilder = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            newBuilder.append(args[i]).append(" ");
        }
        String newValue = newBuilder.toString().trim();

        Event oldEvent = context.getCurentCalendar().getEvent(targetDate, targetStartTime);
        if (oldEvent == null) {
            throw new IllegalArgumentException("Event is null.");
        }

        LocalDate newDate = targetDate;
        LocalTime newStartTime = oldEvent.getStartTime();
        LocalTime newEndTime = oldEvent.getEndTime();
        String newName = oldEvent.getName();
        String newNote = oldEvent.getNotes();

        switch (option) {
            case DATE : newDate = LocalDate.parse(newValue); break;
            case STARTTIME : newStartTime = LocalTime.parse(newValue); break;
            case ENDTIME : newEndTime = LocalTime.parse(newValue); break;
            case NAME : newName = newValue; break;
            case NOTE : newNote = newValue; break;

            default : throw new InvalidCommandArgumentsException("Error. Invalid option." + option + "Choose from : date, starttime, endtime, name, note.");
        }

        // Изтриваме старото събитие
        context.getCurentCalendar().deleteEvent(targetDate, oldEvent.getStartTime(), oldEvent.getEndTime());

        try {
            // Опитваме се да създадем и добавим новото събитие
            TimeInterval updatedTimeInterval = new TimeInterval(newStartTime, newEndTime);
            Event updatedEvent = new Event(updatedTimeInterval, newName, newNote);
            context.getCurentCalendar().addEvent(newDate, updatedEvent);

            context.setHasUnsavedChanges(true);
            return "Successfully changed Event on " + newDate + " " + updatedEvent.toString();

        } catch (Exception e) {
            // Ако гръмне грешка, връщаме старото събитие обратно!
            context.getCurentCalendar().addEvent(targetDate, oldEvent);
            throw new IllegalArgumentException("Change failed: " + e.getMessage());
        }
    }

    @Override
    public int getRequiredArgsCount() {
        return 4;
    }

}
