package com.buildwithwaqar.patterns.gof.behavioral.chain.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

abstract class Handler {
  private Handler next;
  Handler link(Handler n){ next=n; return n; }
  final String handle(String type){
    String r = doHandle(type);
    if (r!=null) return r;
    return next==null? "unhandled": next.handle(type);
  }
  abstract String doHandle(String type);
}
final class BillingHandler extends Handler { String doHandle(String t){ return "billing".equals(t)? "billing-desk": null; } }
final class TechHandler extends Handler { String doHandle(String t){ return "tech".equals(t)? "tech-desk": null; } }
final class GeneralHandler extends Handler { String doHandle(String t){ return "general-desk"; } }
