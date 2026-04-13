package calendar.exception;

public class FileNotOpenedException extends CalendarException {
    public FileNotOpenedException(String message) {
        super("Error. File not opened. Please open file with 'open'.");
    }
}
