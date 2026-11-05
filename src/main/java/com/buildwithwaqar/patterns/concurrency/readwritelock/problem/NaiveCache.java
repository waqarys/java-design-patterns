package com.buildwithwaqar.patterns.concurrency.readwritelock.problem;

import java.util.*;

public final class NaiveCache {
  private String v="v1"; public String read(){ return v; } public void write(String x){ v=x; }
}
