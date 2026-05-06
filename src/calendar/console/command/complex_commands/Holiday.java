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
    /**
     * Отбелязва подадената дата като неработен (почивен) ден.
     *
     * @param args    масив от аргументи (очаква се 1 аргумент - дата)
     * @param context текущият контекст на приложението
     * @return съобщение за успешно добавяне на празник
     * @throws Exception при невалиден формат на датата
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        LocalDate date = LocalDate.parse(args[0]);

        context.getCurentCalendar().addHoliday(date);
        context.setHasUnsavedChanges(true);

        return date.toString() + " successfully marked as a holiday.";
    }

    /**
     * @return {@code false}, тъй като командата има нужда от един или повече параметри
     */
    @Override
    public int getRequiredArgsCount() {
        return 1;
    }

}
