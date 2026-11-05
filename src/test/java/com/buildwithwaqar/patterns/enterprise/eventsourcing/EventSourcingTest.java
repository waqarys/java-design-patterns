package com.buildwithwaqar.patterns.enterprise.eventsourcing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventSourcingTest {
    @Test void replaysToReadModel() {
        var account = new Account();
        account.deposit(100);
        account.withdraw(40);
        assertEquals(60, account.balance());
        var view = new BalanceView();
        account.events().forEach(view::project);
        assertEquals(60, view.balance());
    }
}
