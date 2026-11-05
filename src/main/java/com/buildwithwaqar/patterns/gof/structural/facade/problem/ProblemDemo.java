package com.buildwithwaqar.patterns.gof.structural.facade.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Facade: placeOrder over inventory+payment+shipping ===");
    System.out.println("--- naive ---");
System.out.println(NaiveCheckout.place("SKU", 100));
System.out.println("--- facade ---");
var f=new OrderFacade();
System.out.println(f.placeOrder("SKU", 100, "X"));
System.out.println(f.placeOrder("SKU", 0, "X"));
  }
}
