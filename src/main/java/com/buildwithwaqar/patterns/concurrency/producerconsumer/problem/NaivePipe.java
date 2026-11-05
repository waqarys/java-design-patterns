package com.buildwithwaqar.patterns.concurrency.producerconsumer.problem;

import java.util.*;

public final class NaivePipe {
  public static void run(List<String> jobs){ for(String j: jobs) System.out.println("upload "+j); }
}
