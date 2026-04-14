package calendar.exception;

public class FileNotOpenedException extends CalendarException {
    public FileNotOpenedException() {
        super("Error. File not opened. Please open file with 'open'.");
    }
}
