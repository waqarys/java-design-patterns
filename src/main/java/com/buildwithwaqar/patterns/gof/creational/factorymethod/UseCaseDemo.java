package com.buildwithwaqar.patterns.gof.creational.factorymethod;

import com.buildwithwaqar.patterns.gof.creational.factorymethod.*;

/** Problem: notify users on signup without hard-coding Email/SMS/Push in controllers. */
public final class UseCaseDemo {
    public static void main(String[] args) {
        System.out.println("=== Factory Method problem: channel-agnostic notify ===");
        System.out.println("naive would: new EmailNotification().deliver(...) everywhere");
        for (var ch : NotificationChannel.values()) {
            System.out.println(ch + " -> " + ch.notify("user@x", "Welcome to Build with Waqar"));
        }
    }
}
