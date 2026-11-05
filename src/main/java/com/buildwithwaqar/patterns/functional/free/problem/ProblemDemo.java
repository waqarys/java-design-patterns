package com.buildwithwaqar.patterns.functional.free.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/** Problem: bank-transfer DSL — Map interpreter + logging + Transfer optimizer. */
public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Free: bank-transfer DSL ===");
        System.out.println("--- naive ---");
        var naive = new NaiveBank();
        naive.seed("payroll", 10_000);
        naive.seed("alice", 0);
        System.out.println("toBal=" + naive.transfer("payroll", "alice", 2500));
        System.out.println("ledger=" + naive.ledger());

        System.out.println("--- Free program + Map interpreter ---");
        var ledger = new LinkedHashMap<String, Integer>();
        ledger.put("payroll", 10_000);
        ledger.put("alice", 0);
        var prog = BankDsl.paydayBonus("payroll", "alice", 2500);
        int bal = BankDsl.interpretMap(prog, ledger);
        System.out.println("toBal=" + bal + " ledger=" + ledger);

        System.out.println("--- logging interpreter ---");
        var ledger2 = new LinkedHashMap<String, Integer>();
        ledger2.put("payroll", 10_000);
        ledger2.put("bob", 0);
        List<String> log = new ArrayList<>();
        BankDsl.interpretMap(BankDsl.Free.transfer("payroll", "bob", 100), ledger2, log);
        System.out.println("log=" + log);

        System.out.println("--- optimized Transfer → Withdraw+Deposit ---");
        var opt = BankDsl.optimize(BankDsl.Free.transfer("payroll", "bob", 50));
        var ledger3 = new LinkedHashMap<String, Integer>();
        ledger3.put("payroll", 10_000);
        ledger3.put("bob", 100);
        List<String> log3 = new ArrayList<>();
        BankDsl.interpretMap(opt, ledger3, log3);
        System.out.println("optLog=" + log3);
        System.out.println("ledger=" + ledger3);

        System.out.println("--- failure: insufficient funds ---");
        try {
            var bad = new LinkedHashMap<String, Integer>();
            bad.put("empty", 0);
            BankDsl.interpretMap(BankDsl.Free.withdraw("empty", 1), bad);
        } catch (IllegalStateException e) {
            System.out.println("caught=" + e.getMessage());
        }
    }
}
