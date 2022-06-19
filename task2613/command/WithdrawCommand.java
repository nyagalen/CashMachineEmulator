package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;
import java.util.stream.Collectors;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +
            "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        //  ConsoleHelper.writeMessage("Enter currency code");
        String curCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator =
                CurrencyManipulatorFactory.getManipulatorByCurrencyCode(curCode);
        int amount;
        ConsoleHelper.writeMessage(res.getString("before"));
        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            try {
                amount = Integer.parseInt(ConsoleHelper.readString());

                if (!manipulator.isAmountAvailable(amount)) {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                    continue;
                } else {

                    Map<Integer, Integer> map = manipulator.withdrawAmount(amount);
                    Collection<Integer> intColl = map.keySet();
                    List<Integer> list = intColl.stream()
                            .sorted(Comparator.reverseOrder())
                            .collect(Collectors.toList());
                    list.stream().forEach(integer ->
                            ConsoleHelper.writeMessage("\t" + integer + " - " + map.get(integer)));
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, curCode));
                    break;
                }
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
        }

    }
}
