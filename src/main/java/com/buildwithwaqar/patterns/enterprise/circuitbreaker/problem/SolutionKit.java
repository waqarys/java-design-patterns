package com.buildwithwaqar.patterns.enterprise.circuitbreaker.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

enum CbState { CLOSED, OPEN, HALF_OPEN }
final class CircuitBreaker {
  private final int threshold; private int failures; private CbState state=CbState.CLOSED; private long openedAt;
  private final long resetMs; CircuitBreaker(int threshold, long resetMs){ this.threshold=threshold; this.resetMs=resetMs; }
  CbState state(){ return state; }
  String call(Supplier<String> action){
    if (state==CbState.OPEN){
      if (System.currentTimeMillis()-openedAt < resetMs) throw new IllegalStateException("circuit open");
      state=CbState.HALF_OPEN;
    }
    try {
      String r=action.get(); failures=0; state=CbState.CLOSED; return r;
    } catch (RuntimeException e){
      failures++;
      if (failures>=threshold || state==CbState.HALF_OPEN){ state=CbState.OPEN; openedAt=System.currentTimeMillis(); }
      throw e;
    }
  }
}
