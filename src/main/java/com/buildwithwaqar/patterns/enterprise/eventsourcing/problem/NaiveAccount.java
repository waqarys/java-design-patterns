package com.buildwithwaqar.patterns.enterprise.eventsourcing.problem;

import java.util.*;

public final class NaiveAccount {
  private int bal; void deposit(int c){ bal+=c; } void withdraw(int c){ bal-=c; } int bal(){ return bal; }
}
