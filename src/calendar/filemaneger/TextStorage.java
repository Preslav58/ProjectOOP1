package calendar.filemaneger;

import calendar.model.Calendar;
import calendar.model.Event;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class TextStorage implements fileManeger {
    private static final String DELIMITER = "\\|";
    private static final String DELIMITER_CHAR = "|";

    @Override
    public void save(Calendar calendar, String fileName) throws Exception{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Event event : calendar.getEvents()) {
                String notes = event.getNotes().isEmpty() ? "" : event.getNotes();

                String line = String.join(DELIMITER_CHAR, "EVENT",
                        event.getName().toString(),
                        event.getStartTime().toString(),
                        event.getEndTime().toString(),
                        event.getName(), notes);

                writer.write(line);
                writer.newLine();
            }

            for (LocalDate holiday : calendar.getHolidays()) {
                String line = String.join(DELIMITER_CHAR, "HOLIDAY", holiday.toString());

                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public Calendar load(String fileName) throws Exception{
        Calendar calendar = new Calendar();
        File file = new File(fileName);

        if (!file.exists()) { return calendar;}

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(DELIMITER);
                String type = fields[0];

                if ("EVENT".equals(type)) {
                    LocalDate date = LocalDate.parse(fields[1]);
                    LocalTime startTime = LocalTime.parse(fields[2]);
                    LocalTime endTime = LocalTime.parse(fields[3]);
                    String name = fields[4];
                    String notes = fields.length > 5 ? fields[5] : "";

                    if (notes.equals("")) { notes = ""; }

                    calendar.addEvent(new Event(date, startTime, endTime, name, notes));
                } else if ("HOLIDAY".equals(type)) {
                    LocalDate date = LocalDate.parse(fields[1]);
                    calendar.addHoliday(date);
                }
            }
        }
        return calendar;
    }
}
