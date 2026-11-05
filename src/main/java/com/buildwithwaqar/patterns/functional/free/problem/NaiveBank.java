package com.buildwithwaqar.patterns.functional.free.problem;

import java.util.LinkedHashMap;
import java.util.Map;

/** Naive: transfer logic inlined; no program to optimize or log separately. */
public final class NaiveBank {
    private final Map<String, Integer> ledger = new LinkedHashMap<>();
    public void seed(String acct, int cents) { ledger.put(acct, cents); }
    public int transfer(String from, String to, int cents) {
        int bal = ledger.getOrDefault(from, 0);
        if (bal < cents) throw new IllegalStateException("insufficient:" + from);
        ledger.put(from, bal - cents);
        ledger.put(to, ledger.getOrDefault(to, 0) + cents);
        return ledger.get(to);
    }
    public Map<String, Integer> ledger() { return Map.copyOf(ledger); }
}
