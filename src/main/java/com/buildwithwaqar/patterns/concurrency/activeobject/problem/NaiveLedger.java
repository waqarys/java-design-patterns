package com.buildwithwaqar.patterns.concurrency.activeobject.problem;

import java.util.*;

public final class NaiveLedger {
  private int bal; public void deposit(int c){ bal+=c; } public int bal(){ return bal; }
}
