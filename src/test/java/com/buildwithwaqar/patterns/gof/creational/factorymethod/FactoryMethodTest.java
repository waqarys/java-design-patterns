package com.buildwithwaqar.patterns.gof.creational.factorymethod;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactoryMethodTest {
    @Test void classicHierarchy() {
        assertEquals("email -> a@b.c: hi", new EmailNotifier().notify("a@b.c", "hi"));
        assertEquals("sms -> +1: hi", new SmsNotifier().notify("+1", "hi"));
    }

    @Test void modernEnumFactory() {
        assertEquals("push -> device: hi", NotificationChannel.PUSH.notify("device", "hi"));
        assertInstanceOf(EmailNotification.class, NotificationChannel.EMAIL.create());
    }
}
