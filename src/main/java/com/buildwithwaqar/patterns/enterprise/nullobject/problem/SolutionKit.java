package com.buildwithwaqar.patterns.enterprise.nullobject.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

interface Logger { void info(String m); }
final class ConsoleLogger implements Logger { private final List<String> lines=new ArrayList<>();
  public void info(String m){ lines.add(m); } List<String> lines(){ return List.copyOf(lines); } }
final class NullLogger implements Logger { public void info(String m){ /* no-op */ } }
final class OrderService {
  private final Logger log; OrderService(Logger log){ this.log=Objects.requireNonNull(log); }
  void place(String id){ log.info("placed "+id); }
}
