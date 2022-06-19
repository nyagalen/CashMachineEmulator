package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +
            "common_en");
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }

    public static String readString() {

        try {
            String s = bis.readLine();
            if (s.toLowerCase().contains("exit")) {
               // printExitMessage();
                throw new InterruptOperationException();
            }
            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptOperationException e) {
            printExitMessage();
        }
        return null;
    }

    public static String askCurrencyCode()  {
        String cur = null;
        writeMessage(res.getString("choose.currency.code"));

        cur = readString();
        while (cur.length() != 3) {
            writeMessage(res.getString("choose.currency.code"));
            cur = readString();
        }
        return cur.toUpperCase();


    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        try {
            String digits = readString();
            while (!digits.matches("\\d+\\s\\d+")) {
                writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
                digits = readString();
            }

            int a = Integer.parseInt(digits.split(" ")[0]);
            int b = Integer.parseInt(digits.split(" ")[1]);
            return new String[]{String.valueOf(a), String.valueOf(b)};

        } catch (Exception e) {
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
        }
        return null;
    }

    public static Operation askOperation() throws InterruptOperationException {

        Operation operation = null;
        while (true) {
            try {
                writeMessage(res.getString("choose.operation"));
                operation = Operation.getAllowableOperationByOrdinal(Integer.parseInt(readString()));

                return operation;
            } catch (NumberFormatException e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            } catch (IllegalArgumentException e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
        }

    }
}
