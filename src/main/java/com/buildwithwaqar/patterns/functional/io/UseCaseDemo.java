package com.buildwithwaqar.patterns.functional.io;

import com.buildwithwaqar.patterns.functional.io.IO;
import java.util.concurrent.atomic.AtomicInteger;
/** Problem: describe a side-effecting report pipeline; run only at the edge. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== IO / Effect Interpreter problem ===");
    var hits = new AtomicInteger();
    IO<String> report = IO.delay(() -> { hits.incrementAndGet(); return "rows"; })
        .map(s -> s + "=" + 42);
    System.out.println("described hits=" + hits.get());
    System.out.println("run=" + report.unsafeRun() + " hits=" + hits.get());
  }
}
