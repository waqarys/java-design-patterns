package com.buildwithwaqar.patterns.gof.behavioral.state.problem;

import java.util.*;

public final class NaiveOrder {
  public String state="NEW";
  public void pay(){ state="PAID"; }
  public void ship(){ state="SHIPPED"; } // allows ship from NEW
}
