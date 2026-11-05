package com.buildwithwaqar.patterns.gof.behavioral.templatemethod;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Template Method walkthrough ===");
    System.out.println(new CsvMiner().mine("metrics.csv"));
  }
}
