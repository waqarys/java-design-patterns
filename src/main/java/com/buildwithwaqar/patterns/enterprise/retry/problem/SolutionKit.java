package com.buildwithwaqar.patterns.enterprise.retry.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class Retry {
  static String call(int maxAttempts, Supplier<String> action){
    RuntimeException last=null;
    for (int i=1;i<=maxAttempts;i++){
      try { return action.get(); } catch (RuntimeException e){ last=e; }
    }
    throw last;
  }
}
