package calendar.console.comand.complexCommands;

public enum ChangeOption {
    DATE, STARTTIME, ENDTIME, NAME, NOTE;

     /**
     * Метод, който превръща текст в обект
     */
    public static ChangeOption fromString(String text) {
        for (ChangeOption option : ChangeOption.values()) {
            if (option.name().equalsIgnoreCase(text)) {
                return option;
            }
        }

        throw new IllegalArgumentException("Error " + text + " Requires one of: date, starttime, endtime, name, note");
    }
}
