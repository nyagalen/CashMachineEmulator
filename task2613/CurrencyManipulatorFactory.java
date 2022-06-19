package com.javarush.task.task26.task2613;



import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyManipulatorFactory {
    private CurrencyManipulatorFactory() {
    }

    private static Map<String, CurrencyManipulator> map=new HashMap<>();
    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){
        currencyCode = currencyCode.toUpperCase();
        if(!map.containsKey(currencyCode)){
            CurrencyManipulator manipulator = new CurrencyManipulator(currencyCode);
            map.put(manipulator.getCurrencyCode(), manipulator);
        }
        return map.get(currencyCode);
    }
    public static Collection<CurrencyManipulator> getAllCurrencyManipulators(){
        return map.values();
    }

}
