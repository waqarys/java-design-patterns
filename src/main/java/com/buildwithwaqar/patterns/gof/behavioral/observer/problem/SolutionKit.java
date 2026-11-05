package com.buildwithwaqar.patterns.gof.behavioral.observer.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

interface Listener { void on(String event, String id); }
final class EventBus {
  private final Map<String, List<Listener>> subs = new HashMap<>();
  void on(String event, Listener l){ subs.computeIfAbsent(event,k->new ArrayList<>()).add(l); }
  void publish(String event, String id){
    for (var l: subs.getOrDefault(event, List.of())) l.on(event, id);
  }
}
