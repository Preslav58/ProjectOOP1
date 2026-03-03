package calendar.filemaneger;

import calendar.model.Calendar;

import java.io.IOException;

public interface fileManeger {
    Calendar load(String fileName) throws Exception;
    void save(Calendar calendar, String fileName) throws Exception;
}
