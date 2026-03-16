package calendar.console;

import calendar.filemaneger.TextStorage;
import calendar.model.Calendar;

public class Context {
    private Calendar curentCalendar;
    private String fileName;
    private TextStorage textStorage;
    private boolean hasUnsavedChanges;

    public Context(TextStorage storage) {
        this.textStorage = storage;
        this.curentCalendar = null;
        this.fileName = null;
        this.hasUnsavedChanges = false;
    }

    public Calendar getCurentCalendar() {return curentCalendar;}
    public void setCurentCalendar(Calendar curentCalendar) {this.curentCalendar = curentCalendar;}
    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}
    public TextStorage getTextStorage() {return textStorage;}
    public void setTextStorage(TextStorage textStorage) {this.textStorage = textStorage;}
    public boolean isHasUnsavedChanges() {return hasUnsavedChanges;}
    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {this.hasUnsavedChanges = hasUnsavedChanges;}

    public boolean isFileOpen(){
        return curentCalendar != null && fileName != null;
    }
}
