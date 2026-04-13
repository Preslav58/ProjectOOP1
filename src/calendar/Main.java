package calendar;

import calendar.console.Context;
import calendar.console.Engine;
import calendar.fileManeger.TextStorage;


public class Main {
    public static void main(String[] args) {
        TextStorage textStorage = new TextStorage();

        Context context = new Context(textStorage);

        Engine engine = new Engine(context);
        engine.run();
    }
}
