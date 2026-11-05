package com.buildwithwaqar.patterns.gof.creational.abstractfactory;

public final class Demo {
    public static void main(String[] args) {
        System.out.println("=== Abstract Factory walkthrough ===");
        System.out.println("light=" + new Dialog(UiFactory.of(Theme.LIGHT)).render());
        System.out.println("dark=" + new Dialog(UiFactory.of(Theme.DARK)).render());
    }
}
