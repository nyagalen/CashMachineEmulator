package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;
import java.util.stream.Collectors;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations;

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        denominations = new HashMap<>();
    }

    public int getTotalAmount() {
        int amount = 0;
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            amount += entry.getKey() * entry.getValue();
        }
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    private List<Integer> getDenomReverse() {
        Collection<Integer> denColl = denominations.keySet();
        List<Integer> list = denColl.stream()
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        return list;
    }

    public boolean hasMoney() {
        return getTotalAmount() > 0;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
      //  if(!isAmountAvailable(expectedAmount)) throw new NotEnoughMoneyException();
        Map<Integer, Integer> withdrawMap = new HashMap<>();
        Integer lastDenom = getDenomReverse().get(getDenomReverse().size() - 1);


        int wannaWithdrawItAll = expectedAmount;

        for (Integer denom : getDenomReverse()) {
            int quant = denominations.get(denom);
            if (denom == lastDenom
                    && (denom > wannaWithdrawItAll
                    || denom * quant < wannaWithdrawItAll
                    || wannaWithdrawItAll % denom != 0)) {//if it's the last one on the list
                throw new NotEnoughMoneyException();
            }

            if (denom == wannaWithdrawItAll) {
                withdrawMap.put(denom, 1);
                removeCashFromTheMachine(withdrawMap);
                return withdrawMap;
            }
            if (wannaWithdrawItAll % denom == 0) {
                if (quant >= wannaWithdrawItAll / denom) {//if there are enough bills
                    withdrawMap.put(denom, wannaWithdrawItAll / denom);
                    removeCashFromTheMachine(withdrawMap);
                    return withdrawMap;
                } else if (quant < wannaWithdrawItAll / denom) {//if there aren't enough bills
                    withdrawMap.put(denom, quant);
                    wannaWithdrawItAll = wannaWithdrawItAll - denom * quant;
                }
            } else if (wannaWithdrawItAll / denom == 1) {
                withdrawMap.put(denom, 1);
                wannaWithdrawItAll = wannaWithdrawItAll - denom;

            } else if (wannaWithdrawItAll / denom > 1) {
                int howManyNeededBills = wannaWithdrawItAll / denom;
                if (quant >= howManyNeededBills) {//if there are enough bills in the machine
                    withdrawMap.put(denom, howManyNeededBills);
                    wannaWithdrawItAll = wannaWithdrawItAll - howManyNeededBills * denom;
                } else {//if there are not as many bills
                    withdrawMap.put(denom, quant);// and no more bills in the cash machine
                    wannaWithdrawItAll = wannaWithdrawItAll - denom * quant;
                }
            }
        }
        removeCashFromTheMachine(withdrawMap);

        return withdrawMap;
    }

    private void removeCashFromTheMachine(Map<Integer, Integer> withdrawMap) {
        for (Integer denom : withdrawMap.keySet()) {
            if (withdrawMap.get(denom) == denominations.get(denom)) {
                denominations.remove(denom);
            } else {
                denominations.put(denom, denominations.get(denom) - withdrawMap.get(denom));
            }
        }

    }
}
