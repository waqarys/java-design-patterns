package com.buildwithwaqar.patterns.enterprise.nullobject.problem;

/** Naive: logger may be null — every call site null-checks. */
public final class NaiveOrderService {
  private final Logger log;
  public NaiveOrderService(Logger log){ this.log=log; }
  public void place(String id){ if(log!=null) log.info("placed "+id); }
}
