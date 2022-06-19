package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

public class CashMachine {
    public static final String RESOURCE_PATH = CashMachine.class.getPackage().getName() + ".resources.";
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        try{
            CommandExecutor.execute(Operation.LOGIN);
        } catch (InterruptOperationException e) {
           // ConsoleHelper.printExitMessage();
        }
        try {
            Operation operation = null;
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);

            } while (operation != Operation.EXIT);
           // ConsoleHelper.printExitMessage();
        }catch (InterruptOperationException e){
           /// ConsoleHelper.printExitMessage();
        }
       ConsoleHelper.printExitMessage();


    }
}
