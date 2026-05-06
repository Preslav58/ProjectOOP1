package calendar.console.command.complex_commands;

import calendar.console.Context;
import calendar.console.command.Command;
import calendar.exception.EventConflictException;
import calendar.model.Calendar;
import calendar.model.Day;
import calendar.model.Event;
import calendar.model.TimeInterval;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Команда за обединяване на събития от външен календар към текущия.
 * Зарежда външния файл чрез {@code FileManager} и се опитва да прехвърли всички събития.
 * При откриване на времеви конфликт (хващане на {@code EventConflictException}),
 * спира изпълнението и предоставя на потребителя интерактивно конзолно меню за разрешаване на спора
 * (запазване, изтриване или ръчно преместване).
 */
public class Merge implements Command {
    /**
     * Изпълнява логиката по обединяване на два календара.
     *
     * @param args    име на външния файл
     * @param context текущият контекст
     * @return статистика за броя добавени събития
     * @throws Exception при невалиден файл
     */
    @Override
    public String execute(String[] args, Context context) throws Exception {
        String externalFileName = args[0];
        Calendar externalCalendar = context.getFileManeger().load(externalFileName);
        Scanner scanner = context.getScanner();
        int addedCount = 0;

        for (Day externalDay : externalCalendar.getDays()) {
            LocalDate extDate = externalDay.getDate();

            for (Event externalEvet : externalDay.getEvents()) {
                try {
                    // Опитваме да добавим външното събитие
                    context.getCurentCalendar().addEvent(extDate, externalEvet);
                    addedCount++;

                } catch (EventConflictException e) {
                    System.out.println("\n Conflicting events! You are trying to merge: ");
                    System.out.println("External event: [" + extDate + "] " + externalEvet.toString());
                    System.out.println("Error: " + e.getMessage());

                    System.out.println("What do you want to do?");
                    System.out.println("1 - Save my event (ignore the external event)");
                    System.out.println("2 - Delete my event and save the external event");
                    System.out.println("3 - Change date and hour of the external event");
                    System.out.println("Choose an option: ");

                    String choice = scanner.nextLine().trim();

                    switch (choice) {
                        case "1" -> {
                            System.out.println("External event is dropped");
                            continue;
                        }

                        case "2" -> {
                            // Намираме с кое събитие имаме конфликт и го трием
                            Event conflictEvent = findConflictingEvent(context.getCurentCalendar(), extDate, externalEvet);

                            if (conflictEvent != null) {
                                context.getCurentCalendar().deleteEvent(extDate, conflictEvent.getStartTime(), conflictEvent.getEndTime());
                            }

                            // Вече е свободно, добавяме външното
                            context.getCurentCalendar().addEvent(extDate, externalEvet);
                            addedCount++;
                            System.out.println("Your Event has been deleted, External event added");
                        }

                        case "3" -> {
                            System.out.println("Please enter a new date  (YYYY-MM-DD): ");
                            LocalDate newDate = LocalDate.parse(scanner.nextLine().trim());

                            System.out.println("Please enter a new start time (HH:MM): ");
                            LocalTime newStart = LocalTime.parse(scanner.nextLine().trim());

                            System.out.println("Please enter a new end time (HH:MM): ");
                            LocalTime newEnd = LocalTime.parse(scanner.nextLine().trim());

                            TimeInterval newTimeInterval = new TimeInterval(newStart, newEnd);
                            Event modifiedEvent = new Event(newTimeInterval, externalEvet.getName(), externalEvet.getNotes());

                            try {
                                context.getCurentCalendar().addEvent(newDate, modifiedEvent);
                                addedCount++;
                                System.out.println("External event has been added with new date/hours");

                            } catch (Exception ex) {
                                System.out.println("Error. This new hour is also busy. Event dropped");
                            }
                        }

                        default -> System.out.println("Invalid choice. Event dropped");
                    }
                }
            }
        }

        // Прехвърляме и всички почивни дни
        for (LocalDate holiday : externalCalendar.getHolidays()) {
            context.getCurentCalendar().addHoliday(holiday);
        }

        context.setHasUnsavedChanges(true);
        return String.format("\n Merge finish! Events added %s from external calendar", addedCount);
    }

    /**
     * Помощен метод, който намира кое от текущите събития се застъпва с външното.
     * Оптимизиран е да търси САМО в рамките на конкретния ден.
     */
        private Event findConflictingEvent(Calendar myCalendar, LocalDate searchDate, Event externalEvent) {
            for (Event myEvent : myCalendar.getEventsForDate(searchDate)) {
                if (myEvent.overlap(externalEvent)) {
                    return myEvent;
                }
            }
        return null;
    }

    /**
     * @return {@code false}, тъй като командата има нужда от един или повече параметри
     */
    @Override
    public int getRequiredArgsCount() {
        return 1;
    }

}
