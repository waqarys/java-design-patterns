package com.buildwithwaqar.patterns.gof.creational.factorymethod;

/** Modern Java 25: sealed enum + switch expression replaces the class hierarchy. */
public enum NotificationChannel {
    EMAIL, SMS, PUSH;

    public Notification create() {
        return switch (this) {
            case EMAIL -> new EmailNotification();
            case SMS -> new SmsNotification();
            case PUSH -> new PushNotification();
        };
    }

    public String notify(String to, String body) {
        return create().deliver(to, body);
    }
}
