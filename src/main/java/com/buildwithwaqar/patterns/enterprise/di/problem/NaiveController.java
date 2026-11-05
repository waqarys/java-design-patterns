package com.buildwithwaqar.patterns.enterprise.di.problem;

import java.util.*;

public final class NaiveController {
  public String hello(String name){ return new GreeterHard().greet(name); }
}
final class GreeterHard { String greet(String n){ return "hi "+n; } }

