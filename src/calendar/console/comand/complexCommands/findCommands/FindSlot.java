package calendar.console.comand.complexCommands.findCommands;

import calendar.console.Context;
import calendar.exception.InvalidCommandArgumentsException;
import java.time.LocalDate;
import java.util.Collections;

/**
 * Команда за намиране на свободно място за среща с определена продължителност.
 * Търси дупка в графика само в рамките на текущия (активния) календар.
 */
public class FindSlot extends BaseFindSlot {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 2) {
            throw new InvalidCommandArgumentsException("Error. Wrong number of arguments. Please use findslot <fromdate> <hours>");
        }

        LocalDate searchDate = LocalDate.parse(args[0]);
        int hours = Integer.parseInt(args[1]);

        if (hours > 9) {
            throw new IllegalArgumentException("Error. Hours cannot be greater than 9");
        }

        return findCommonSlot(searchDate, hours, Collections.singletonList(context.getCurentCalendar()));
    }
}
