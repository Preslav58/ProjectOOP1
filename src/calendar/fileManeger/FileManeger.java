package calendar.fileManeger;

import calendar.model.Calendar;

public interface FileManeger {
    Calendar load(String fileName) throws Exception;
    void save(Calendar calendar, String fileName) throws Exception;
}
