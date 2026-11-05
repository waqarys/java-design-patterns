package com.buildwithwaqar.patterns.gof.creational.factorymethod;

public sealed interface Notification permits EmailNotification, SmsNotification, PushNotification {
    String deliver(String to, String body);
}

record EmailNotification() implements Notification {
    @Override public String deliver(String to, String body) {
        return "email -> " + to + ": " + body;
    }
}

record SmsNotification() implements Notification {
    @Override public String deliver(String to, String body) {
        return "sms -> " + to + ": " + body;
    }
}

record PushNotification() implements Notification {
    @Override public String deliver(String to, String body) {
        return "push -> " + to + ": " + body;
    }
}
