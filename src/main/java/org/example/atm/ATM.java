package org.example.atm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Банкомат.
 * Инициализируется набором купюр и умеет выдавать купюры для заданной суммы, либо отвечать отказом.
 * При выдаче купюры списываются с баланса банкомата.
 * Допустимые номиналы: 50₽, 100₽, 500₽, 1000₽, 5000₽.
 *
 * Другие валюты и номиналы должны легко добавляться разработчиками в будущем.
 * Многопоточные сценарии могут быть добавлены позже (например резервирование).
 */
public class ATM {
    public AtmResponse withdraw(int amount) {
        Map<Integer, Integer> withdrawalMap = new HashMap<>();
        // нужно проверить что мы можем набрать amount из имеющихся купюр
        for (Integer nominal : availableMoney.keySet()) {
            int availableNominalCount = availableMoney.get(nominal);
            int desiredNominalCount  = amount / nominal;

            int withdrawNominalCount = Math.min(availableNominalCount, desiredNominalCount);
            withdrawalMap.put(nominal, withdrawNominalCount);
            amount -= withdrawNominalCount * nominal;
        }
        if (amount == 0) {
            for (Map.Entry<Integer, Integer> withdrawal : withdrawalMap.entrySet()) {
                // NB not thread-safe!
                this.availableMoney.compute(
                        withdrawal.getKey(),
                        (k, previousAmount) -> previousAmount - withdrawal.getValue()
                );
            }
            return new AtmResponse(true, withdrawalMap);
        }
        return new AtmResponse(false, null);
    }

    // номинал -> доступное количество купюр
    // предполагаем порядок! сначала самые большие купюры
    // FIXME: вынести в отдельный класс
    private final LinkedHashMap<Integer, Integer> availableMoney;

    public ATM(LinkedHashMap<Integer, Integer> availableMoney) {
        this.availableMoney = availableMoney;
    }

    public LinkedHashMap<Integer, Integer> moneyAudit() {
        return this.availableMoney;
    }
}
