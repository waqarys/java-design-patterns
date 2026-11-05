package com.buildwithwaqar.patterns.gof.behavioral.strategy.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

interface Pricing { int apply(int cents); }
final class PercentOff implements Pricing { private final int p; PercentOff(int p){this.p=p;} public int apply(int c){ return c*(100-p)/100; } }
final class FlatOff implements Pricing { private final int f; FlatOff(int f){this.f=f;} public int apply(int c){ return Math.max(0,c-f); } }
final class HalfOff implements Pricing { public int apply(int c){ return c/2; } }
final class Cart {
  private final Pricing pricing; Cart(Pricing p){ pricing=p; }
  int total(int cents){ return pricing.apply(cents); }
}
