package com.buildwithwaqar.patterns.gof.behavioral.iterator.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

final class RoundRobin implements Iterable<String> {
  private final List<String> workers; private int i;
  RoundRobin(List<String> workers){ this.workers=List.copyOf(workers); }
  public Iterator<String> iterator(){
    return new Iterator<>(){
      public boolean hasNext(){ return !workers.isEmpty(); }
      public String next(){ if(workers.isEmpty()) throw new NoSuchElementException();
        String w=workers.get(i % workers.size()); i++; return w; }
    };
  }
}
