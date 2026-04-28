package calendar.console.comand.complexCommands.changeCommand;

/**
 * Изброим тип (Enum), който дефинира всички възможни опции за промяна на събитие.
 * Гарантира типова безопасност и предпазва от грешки при въвеждане на текст.
 */

public enum ChangeOption {
    DATE, STARTTIME, ENDTIME, NAME, NOTE;

    /**
     * Превръща подаден текст в съответния обект от изброимия тип.
     * Търсенето не прави разлика между малки и главни букви.
     *
     * @param text текстът, въведен от потребителя (напр. "date", "NAME")
     * @return съответната опция за промяна
     * @throws IllegalArgumentException ако текстът не съвпада с нито една валидна опция
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
