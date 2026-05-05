package calendar.console.command.complex_commands;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.InvalidCommandArgumentsException;

import java.time.LocalDate;

/**
 * Команда за отбелязване на определена дата като неработен (почивен) ден.
 * Делегира логиката на календара, който намира съответния обект {@code Day}
 * и променя неговия статус. Празниците се вземат предвид при търсенето на свободни слотове.
 */
public class Holiday implements Command {
    @Override
    public String execute(String[] args, Context context) throws Exception {
        LocalDate date = LocalDate.parse(args[0]);

        context.getCurentCalendar().addHoliday(date);
        context.setHasUnsavedChanges(true);

        return date.toString() + " successfully marked as a holiday.";
    }

    @Override
    public int getRequiredArgsCount() {
        return 1;
    }

}
