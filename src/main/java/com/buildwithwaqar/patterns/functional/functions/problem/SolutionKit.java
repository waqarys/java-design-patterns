package com.buildwithwaqar.patterns.functional.functions.problem;

import java.util.*;
import java.util.function.*;

final class Fns {
  static Function<Integer, Function<Integer, Integer>> curryAdd(){ return a -> b -> a+b; }
  static <A,B,C> Function<A,C> compose(Function<B,C> g, Function<A,B> f){ return a -> g.apply(f.apply(a)); }
  static Function<Integer,Integer> pricePipeline(int pct, int flat){
    Function<Integer,Integer> percent = c -> c*(100-pct)/100;
    Function<Integer,Integer> flatOff = c -> Math.max(0, c-flat);
    return compose(flatOff, percent);
  }
}
