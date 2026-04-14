package calendar.console.comand.complexCommands.findCommands;

import calendar.console.Context;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Calendar;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Команда за намиране на общо свободно място, синхронизирано с външен календар.
 * Зарежда графика на колега от друг файл и търси време, в което и двамата са свободни.
 */
public class FindSlotWith extends BaseFindSlot {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 3) {
            throw new InvalidCommandArgumentsException("Error. Wrong number of arguments. Please use: findslotwith <fromdate> <hours> <calendar>");
        }

        LocalDate searchDate = LocalDate.parse(args[0]);
        int hours = Integer.parseInt(args[1]);
        String secondCalendarFile = args[2];

        if (hours > 9) {
            throw new IllegalArgumentException("Error. Hours cannot be greater than 9");
        }

        Calendar secondCalendar = context.getTextStorage().load(secondCalendarFile);

        return findCommonSlot(searchDate, hours, Arrays.asList(context.getCurentCalendar(), secondCalendar));
    }
}
