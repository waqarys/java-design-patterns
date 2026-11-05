package com.buildwithwaqar.patterns.functional.monoid.problem;

import java.util.*;
import java.util.function.*;

interface Monoid<A> { A empty(); A combine(A a, A b); }
final class Monoids {
  static final Monoid<Integer> INT_ADD = new Monoid<>(){ public Integer empty(){return 0;} public Integer combine(Integer a,Integer b){return a+b;} };
  static final Monoid<String> STRING = new Monoid<>(){ public String empty(){return "";} public String combine(String a,String b){return a+b;} };
  static <A> A fold(Monoid<A> m, List<A> xs){ A acc=m.empty(); for(A x: xs) acc=m.combine(acc,x); return acc; }
}
