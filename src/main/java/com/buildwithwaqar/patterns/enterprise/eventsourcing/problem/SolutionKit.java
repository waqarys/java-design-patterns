package com.buildwithwaqar.patterns.enterprise.eventsourcing.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

sealed interface Event permits Deposited, Withdrawn {}
record Deposited(int cents) implements Event {}
record Withdrawn(int cents) implements Event {}
final class Account {
  private final List<Event> events=new ArrayList<>();
  void deposit(int c){ if(c<=0) throw new IllegalArgumentException("deposit"); events.add(new Deposited(c)); }
  void withdraw(int c){ if(c<=0 || balance()<c) throw new IllegalStateException("funds"); events.add(new Withdrawn(c)); }
  int balance(){ int b=0; for(Event e: events){ if(e instanceof Deposited d) b+=d.cents(); else if(e instanceof Withdrawn w) b-=w.cents(); } return b; }
  List<Event> history(){ return List.copyOf(events); }
}
