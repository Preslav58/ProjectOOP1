package calendar;

import calendar.console.Context;
import calendar.console.Engine;
import calendar.console.comand.CommandFactory;
import calendar.fileManeger.TextStorage;


public class Main {
    public static void main(String[] args) {
        TextStorage textStorage = new TextStorage();

        Context context = new Context(textStorage);
        CommandFactory commandFactory = new CommandFactory();

        Engine engine = new Engine(context, commandFactory);
        engine.run();
    }
}
