package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;

import com.buildwithwaqar.patterns.gof.creational.factorymethod.NotificationChannel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactoryMethodProblemTest {
    @Test void eachChannelDelivers() {
        assertTrue(NotificationChannel.EMAIL.notify("a", "x").startsWith("email"));
        assertTrue(NotificationChannel.SMS.notify("+1", "x").startsWith("sms"));
        assertTrue(NotificationChannel.PUSH.notify("d", "x").startsWith("push"));
    }
}
