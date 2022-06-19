package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Collection;
import java.util.ResourceBundle;

class InfoCommand implements Command{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+
            "info_en");
     @Override
     public void execute() throws InterruptOperationException {
         Collection<CurrencyManipulator> manCollection =
                 CurrencyManipulatorFactory.getAllCurrencyManipulators();
         int count = 0;
         for(CurrencyManipulator cm : manCollection){
             if(cm.hasMoney()){
                 count++;
                 ConsoleHelper.writeMessage(res.getString("before")+cm.getCurrencyCode()
                 + " - "+cm.getTotalAmount());
             }

         }
         if(count==0) ConsoleHelper.writeMessage(res.getString("no.money"));

     }
 }
