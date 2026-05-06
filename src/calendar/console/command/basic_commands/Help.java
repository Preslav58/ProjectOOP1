package calendar.console.command.basic_commands;

import calendar.console.Context;
import calendar.console.command.Command;

/**
 * Команда за извеждане на помощна информация.
 * Показва на екрана списък с всички поддържани команди и техния синтаксис.
 */
public class Help implements Command {
    /**
     * @return {@code false}, тъй като командата отваря файл и не изисква такъв да е вече отворен
     */
    @Override
    public boolean requiresOpenedFile() {
        return false;
    }

    /**
     * Извежда списък с всички налични команди и тяхното описание.
     *
     * @param args    масив от аргументи (не се очакват аргументи за тази команда)
     * @param context текущият контекст на приложението
     * @return форматиран текст с помощна информация
     * @throws Exception ако възникне непредвидена грешка
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        return """
                The following commands are supported:
                open <file>                                     - opens <file>
                close                                           - closes currently opened file
                save                                            - saves the currently open file
                save as <file>                                  - saves the currently open file in <file>
                help                                            - prints this information
                exit                                            - exits the program
                book <date> <starttime> <endtime> <name> <note> - Запазва час за среща
                unbook <date> <starttime> <endtime>             - Отменя час за среща
                agenda <date>                                   - Извежда хронологичен списък с ангажименти за деня
                change <date> <starttime> <option> <newvalue>   - Променя събитие (option: date, starttime, endtime, name, note)
                find <string>                                   - Търси среща по име или бележка
                holiday <date>                                  - Отбелязва дата като неработна
                busydays <from> <to>                            - Извежда статистика за натовареност
                findslot <fromdate> <hours>                     - Намира свободно място за среща
                findslotwith <fromdate> <hours> <calendar>      - Намира общо свободно място с друг календар
                merge <calendar>                                - Обединява събитията от външен календар с текущия
                """;
    }
}
