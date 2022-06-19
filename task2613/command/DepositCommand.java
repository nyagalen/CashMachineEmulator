package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");
    @Override
    public void execute() throws InterruptOperationException {
        String cCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator
                = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(cCode);
        String[] denominationCount = ConsoleHelper.getValidTwoDigits(cCode);
        ConsoleHelper.writeMessage(res.getString("before"));
        currencyManipulator.addAmount(Integer.parseInt(denominationCount[0]),
                Integer.parseInt(denominationCount[1]));
        int a = Integer.parseInt(denominationCount[0]);
        int b = Integer.parseInt(denominationCount[1]);
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), a*b, cCode));

      //  System.out.println(currencyManipulator.getTotalAmount());
    }
}
