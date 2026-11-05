package com.buildwithwaqar.patterns.functional.free.problem;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.*;

class FreeProblemTest {
    @Test void transferMovesFunds() {
        var ledger = new LinkedHashMap<String, Integer>();
        ledger.put("a", 100); ledger.put("b", 0);
        BankDsl.interpretMap(BankDsl.Free.transfer("a", "b", 40), ledger);
        assertEquals(60, ledger.get("a"));
        assertEquals(40, ledger.get("b"));
    }

    @Test void insufficientFails() {
        var ledger = new LinkedHashMap<String, Integer>();
        ledger.put("a", 10);
        assertThrows(IllegalStateException.class,
                () -> BankDsl.interpretMap(BankDsl.Free.withdraw("a", 50), ledger));
    }

    @Test void loggingInterpreterRecordsInstr() {
        var ledger = new LinkedHashMap<String, Integer>();
        ledger.put("a", 100);
        var log = new ArrayList<String>();
        BankDsl.interpretMap(BankDsl.Free.deposit("a", 5), ledger, log);
        assertEquals(1, log.size());
        assertTrue(log.get(0).contains("Deposit"));
    }

    @Test void optimizerExpandsTransfer() {
        var opt = BankDsl.optimize(BankDsl.Free.transfer("a", "b", 1));
        var ledger = new LinkedHashMap<String, Integer>();
        ledger.put("a", 10); ledger.put("b", 0);
        var log = new ArrayList<String>();
        BankDsl.interpretMap(opt, ledger, log);
        assertEquals(2, log.size()); // Withdraw + Deposit
        assertTrue(log.get(0).contains("Withdraw"));
        assertTrue(log.get(1).contains("Deposit"));
    }
}
