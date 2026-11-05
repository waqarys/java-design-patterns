package com.buildwithwaqar.patterns.gof.creational.factorymethod;

public final class Demo {
    public static void main(String[] args) {
        System.out.println("=== Factory Method walkthrough ===");
        System.out.println(new EmailNotifier().notify("waqar@example.com", "Welcome"));
        System.out.println(new SmsNotifier().notify("+9198", "OTP 4421"));
        System.out.println("modern=" + NotificationChannel.PUSH.notify("device-9", "Build ready"));
        System.out.println("product=" + NotificationChannel.EMAIL.create().getClass().getSimpleName());
    }
}
