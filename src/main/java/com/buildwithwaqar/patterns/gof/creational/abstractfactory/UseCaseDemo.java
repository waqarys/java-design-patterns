package com.buildwithwaqar.patterns.gof.creational.abstractfactory;

import com.buildwithwaqar.patterns.gof.creational.abstractfactory.*;

/** Problem: themed dialog must not mix light buttons with dark checkboxes. */
public final class UseCaseDemo {
    public static void main(String[] args) {
        System.out.println("=== Abstract Factory problem: consistent UI theme ===");
        System.out.println("naive: new LightButton() + new DarkCheckbox() // mismatch");
        System.out.println("light=" + new Dialog(UiFactory.of(Theme.LIGHT)).render());
        System.out.println("dark=" + new Dialog(UiFactory.of(Theme.DARK)).render());
    }
}
