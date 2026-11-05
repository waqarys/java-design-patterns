package com.buildwithwaqar.patterns.gof.creational.factorymethod;

/** Creator hierarchy — subclasses decide which product to instantiate. */
public abstract class Notifier {
    protected abstract Notification create();

    public final String notify(String to, String body) {
        return create().deliver(to, body);
    }
}

final class EmailNotifier extends Notifier {
    @Override protected Notification create() { return new EmailNotification(); }
}

final class SmsNotifier extends Notifier {
    @Override protected Notification create() { return new SmsNotification(); }
}

final class PushNotifier extends Notifier {
    @Override protected Notification create() { return new PushNotification(); }
}
