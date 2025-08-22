package org.example.atm;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestATM {
    @Test
    public void testWithdrawalSuccess() {
        // given
        LinkedHashMap<Integer, Integer> money = new LinkedHashMap<>();
        money.put(5000, 1);
        money.put(1000, 5);
        money.put(500, 6);

        ATM atm = new ATM(money);

        // when
        AtmResponse atmResponse = atm.withdraw(12000); // 5000 + 1000*5 + 500 x 4

        // then
        assertEquals(true, atmResponse.isSuccess());
        assertEquals(1, atmResponse.getWithdrawalMap().get(5000));
    }

    @Test
    public void testWithdrawalFail() {
        // given
        LinkedHashMap<Integer, Integer> money = new LinkedHashMap<>();
        money.put(5000, 1);
        money.put(1000, 5);
        money.put(500, 3);

        ATM atm = new ATM(money);

        // when
        AtmResponse atmResponse = atm.withdraw(12000); // 5000 + 1000*5 + 500 x 4

        // then
        assertEquals(false, atmResponse.isSuccess());
        assertNull(atmResponse.getWithdrawalMap());
    }
}
