package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
    /*final static String cardNumber = "123456789012";
    final static String pin = "1234";*/
    private ResourceBundle validCreditCards =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH
                    + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +
            "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        String userCardNumber;
        String userPin;
        ConsoleHelper.writeMessage(res.getString("before"));
        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            userCardNumber = ConsoleHelper.readString();
            //  ConsoleHelper.writeMessage(res.getString("specify.data"));
            userPin = ConsoleHelper.readString();

            if (userCardNumber.isEmpty() || userPin.isEmpty() ||
                    userCardNumber == "" || userPin == "" || !userCardNumber.matches("\\d{12}") || !userPin.matches("\\d{4}")) {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));

            }
            if (!userCardNumber.isEmpty() && !userPin.isEmpty()
                    && userCardNumber.matches("\\d{12}") && userPin.matches("\\d{4}")

                    && validCreditCards.containsKey(userCardNumber) && validCreditCards.getString(userCardNumber).equals(userPin)) {
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), userCardNumber));
                break;
            }else{
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), userCardNumber));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            }


        }

    }
}
