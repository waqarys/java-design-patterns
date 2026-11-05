package com.buildwithwaqar.patterns.enterprise.eventsourcing;

import java.util.ArrayList;
import java.util.List;

sealed interface AccountEvent permits Deposited, Withdrawn {}
record Deposited(int amount) implements AccountEvent {}
record Withdrawn(int amount) implements AccountEvent {}

/** Minimal event-sourced aggregate + separate read model (CQRS). */
public final class Account {
    private final List<AccountEvent> events = new ArrayList<>();
    private int balance;

    public void deposit(int amount) {
        apply(new Deposited(amount));
    }

    public void withdraw(int amount) {
        if (amount > balance) throw new IllegalStateException("insufficient");
        apply(new Withdrawn(amount));
    }

    private void apply(AccountEvent e) {
        switch (e) {
            case Deposited(var a) -> balance += a;
            case Withdrawn(var a) -> balance -= a;
        }
        events.add(e);
    }

    public int balance() { return balance; }
    public List<AccountEvent> events() { return List.copyOf(events); }
}

/** Read model projected from events. */
final class BalanceView {
    private int balance;
    void project(AccountEvent e) {
        switch (e) {
            case Deposited(var a) -> balance += a;
            case Withdrawn(var a) -> balance -= a;
        }
    }
    int balance() { return balance; }
}
