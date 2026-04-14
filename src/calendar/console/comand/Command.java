package calendar.console.comand;

import calendar.console.Context;

/**
 * Интерфейсът {@code Command} дефинира общ договор за всички конзолни команди в приложението.
 * Това е класическа реализация на шаблона за дизайн "Command Pattern".
 * Всяка специфична команда (напр. open, book, save) имплементира този интерфейс
 * и съдържа собствена независима логика.
 */
public interface Command {
    /**
     * Изпълнява същинската логика на командата.
     *
     * @param args    масив от аргументи, въведени от потребителя след името на командата
     * @param context текущото състояние на приложението (календар, хранилище, скенер)
     * @return текстово съобщение с резултата от изпълнението, което ще бъде отпечатано на конзолата
     * @throws Exception ако възникне грешка по време на изпълнението (напр. невалидни данни или застъпване)
     */
    String execute(String[] args, Context context) throws Exception;

    /**
     * Казва дали е нужно да има отворен файл.
     * @return True по подразбиране
     */
    default boolean requiresOpenedFile() {
        return true;
    }
}
