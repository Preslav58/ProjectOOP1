package calendar.console.comand.complexCommands;

import calendar.console.Context;
import calendar.console.comand.Command;
import calendar.exception.EventConflictException;
import calendar.exception.InvalidCommandArgumentsException;
import calendar.model.Calendar;
import calendar.model.Event;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Команда за обединяване на събития от външен календар към текущия.
 * При откриване на времеви конфликт между две събития, предоставя на потребителя
 * интерактивно меню за избор (запазване на едното, изтриване или преместване).
 */
public class Merge implements Command {

    @Override
    public String execute(String[] args, Context context) throws Exception {
        if (args.length < 1) {
            throw new InvalidCommandArgumentsException("Error. Too few arguments. Please use: merge <calendar>");
        }

        String externalFileName = args[0];
        Calendar externalCalendar = context.getTextStorage().load(externalFileName);
        Scanner scanner = context.getScanner();
        int addedCount = 0;

        for (Event externalEvet : externalCalendar.getEvents()) {
            try {
                context.getCurentCalendar().addEvent(externalEvet);
                addedCount++;

            } catch (EventConflictException e) {
                System.out.println("\n Conflicting events! You are trying to merge: ");
                System.out.println("External event: " + externalEvet.toString());
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
                        Event conflictEvent = findConflictingEvent(context.getCurentCalendar(), externalEvet);

                        if (conflictEvent != null) {
                            context.getCurentCalendar().deleteEvent(conflictEvent.getDate(), conflictEvent.getStartTime(), conflictEvent.getEndTime());
                        }

                        context.getCurentCalendar().addEvent(externalEvet);
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

                        Event modifiedEvent = new Event(newDate, newStart, newEnd, externalEvet.getName(), externalEvet.getNotes());

                        try {
                            context.getCurentCalendar().addEvent(modifiedEvent);
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

        for (LocalDate holiday : externalCalendar.getHolidays()) {
            context.getCurentCalendar().addHoliday(holiday);
        }

        context.setHasUnsavedChanges(true);
        return String.format("\n Merge finish! Events added %s from external calendar", addedCount);
    }

    private Event findConflictingEvent(Calendar myCalendar, Event externalEvet) {
        for (Event myEvent : myCalendar.getEvents()) {
            if (myEvent.overlap(externalEvet)) {
                return myEvent;
            }
        }
        return null;
    }
}
