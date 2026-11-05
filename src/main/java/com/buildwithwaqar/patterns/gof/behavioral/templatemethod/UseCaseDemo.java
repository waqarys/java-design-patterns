package com.buildwithwaqar.patterns.gof.behavioral.templatemethod;

import com.buildwithwaqar.patterns.gof.behavioral.templatemethod.*;
/** Problem: shared ETL skeleton with format-specific parse steps. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Template Method problem: CSV mining ===");
    System.out.println(new CsvMiner().mine("report.csv"));
  }
}
