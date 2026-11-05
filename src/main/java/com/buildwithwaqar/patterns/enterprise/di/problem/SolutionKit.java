package com.buildwithwaqar.patterns.enterprise.di.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

interface Greeter { String greet(String name); }
final class FormalGreeter implements Greeter { public String greet(String n){ return "Hello, "+n; } }
final class DiContainer {
  private final Map<Class<?>, Object> beans=new HashMap<>();
  <T> void register(Class<T> type, T impl){ beans.put(type, impl); }
  @SuppressWarnings("unchecked") <T> T get(Class<T> type){
    Object o=beans.get(type); if(o==null) throw new IllegalStateException("missing "+type.getSimpleName()); return (T)o;
  }
}
final class HelloController {
  private final Greeter greeter; HelloController(Greeter g){ greeter=g; }
  String hello(String name){ return greeter.greet(name); }
}
