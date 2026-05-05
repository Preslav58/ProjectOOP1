package calendar.console.command.complex_commands;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.InvalidCommandArgumentsException;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Команда за отмяна (изтриване) на събитие от календара.
 * Търси събитие по точна дата, начален и краен час и делегира изтриването
 * на обекта {@code Calendar}, който от своя страна го премахва от съответния ден.
 */
public class UnBook implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception {
        LocalDate date = LocalDate.parse(args[0]);
        LocalTime startTime = LocalTime.parse(args[1]);
        LocalTime endTime = LocalTime.parse(args[2]);

        boolean isDeleted = context.getCurentCalendar().deleteEvent(date, startTime, endTime);

        if (isDeleted) {
            context.setHasUnsavedChanges(true);
            return String.format("Event %s from %s to %s has been deleted", date.toString(), startTime.toString(), endTime.toString());
        } else {
            return "Event on this date or hour hasn't been found";
        }
    }

    @Override
    public int getRequiredArgsCount() {
        return 3;
    }

}
