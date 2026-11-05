package com.buildwithwaqar.patterns.gof.behavioral.state.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

enum Phase { NEW, PAID, SHIPPED, CANCELLED }
final class Order {
  private Phase phase=Phase.NEW;
  Phase phase(){ return phase; }
  void pay(){ if(phase!=Phase.NEW) throw new IllegalStateException("pay from "+phase); phase=Phase.PAID; }
  void ship(){ if(phase!=Phase.PAID) throw new IllegalStateException("ship from "+phase); phase=Phase.SHIPPED; }
  void cancel(){ if(phase==Phase.SHIPPED) throw new IllegalStateException("cancel shipped"); phase=Phase.CANCELLED; }
}
