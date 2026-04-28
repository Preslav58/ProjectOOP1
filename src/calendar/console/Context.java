package calendar.console;

import calendar.fileManeger.FileManeger;
import calendar.fileManeger.TextStorage;
import calendar.model.Calendar;
import java.util.Scanner;

/**
 * Класът {@code Context} съхранява текущото състояние на приложението.
 * Съдържа информация за текущо отворения календар, файл,
 * използваното хранилище и състоянието на промените.
 */
public class Context {
    private Calendar curentCalendar;
    private String fileName;
    private FileManeger fileManeger;
    private boolean hasUnsavedChanges;
    private Scanner scanner;

    /**
     * Създава нов контекст с подадено хранилище.
     *
     * @param fileManeger обект за запис и четене на данни
     */
    public Context(FileManeger fileManeger) {
        this.fileManeger = fileManeger;
        this.curentCalendar = null;
        this.fileName = null;
        this.hasUnsavedChanges = false;
    }

    public Calendar getCurentCalendar() {return curentCalendar;}
    public void setCurrentCalendar(Calendar curentCalendar) {this.curentCalendar = curentCalendar;}

    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public FileManeger getFileManeger() {return fileManeger;}
    public void setFileManeger(FileManeger fileManeger) {this.fileManeger = fileManeger;}

    public boolean hasUnsavedChanges() {return hasUnsavedChanges;}
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {this.hasUnsavedChanges = hasUnsavedChanges;}

    public Scanner getScanner() {return scanner;}
    public void setScanner(Scanner scanner) {this.scanner = scanner;}

    /**
     * Проверява дали има отворен файл.
     * Файлът се счита за отворен, ако има активен календар и име на файл.
     *
     * @return {@code true} ако има отворен файл, иначе {@code false}
     */
    public boolean isFileOpen(){
        return curentCalendar != null && fileName != null;
    }
}
