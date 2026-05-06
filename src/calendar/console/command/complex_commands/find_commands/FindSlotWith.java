package calendar.console.command.complex_commands.find_commands;

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
    /**
     * Търси общ свободен времеви прозорец между текущия и един външен календар.
     *
     * @param args    масив от аргументи (очаква 3 аргумента - начална дата, продължителност и име на външен файл)
     * @param context текущият контекст на приложението
     * @return текстово съобщение с намерените дата и час или съобщение за неуспех
     * @throws Exception при невалидни параметри или грешка при четене на външния файл
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        LocalDate searchDate = LocalDate.parse(args[0]);
        int hours = Integer.parseInt(args[1]);
        String secondCalendarFile = args[2];

        if (hours > 9) {
            throw new InvalidCommandArgumentsException("Error. Hours cannot be greater than 9");
        }

        Calendar secondCalendar = context.getFileManeger().load(secondCalendarFile);

        return findCommonSlot(searchDate, hours, Arrays.asList(context.getCurentCalendar(), secondCalendar));
    }

    /**
     * @return {@code false}, тъй като командата има нужда от 3 или повече параметри
     */
    @Override
    public int getRequiredArgsCount() {
        return 3;
    }

}
