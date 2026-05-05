package calendar.file_manager;

import calendar.model.Calendar;

/**
 * Интерфейсът {@code FileManager} дефинира основните операции за четене и запис на календарни данни.
 * Предоставя абстракция (Storage Layer), която позволява лесна смяна на формата за съхранение
 * (например текстови файлове, XML, база данни), без да се променя бизнес логиката на програмата.
 */
public interface FileManeger {

    /**
     * Зарежда календар от посочения файл.
     *
     * @param fileName името или пътят до файла
     * @return зареден обект от тип {@code Calendar}
     * @throws Exception при грешка по време на четене или ако данните са в невалиден формат
     */
    Calendar load(String fileName) throws Exception;

    /**
     * Записва съдържанието на подадения календар в посочения файл.
     *
     * @param calendar календарът, който трябва да бъде запазен
     * @param fileName името или пътят до файла, в който ще се записва
     * @throws Exception при проблем с достъпа до файла или грешка при запис
     */
    void save(Calendar calendar, String fileName) throws Exception;
}
