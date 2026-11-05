package com.buildwithwaqar.patterns.functional.state.problem;

import java.util.*;
import java.util.function.*;

record State<S,A>(Function<S, Map.Entry<A,S>> runF){
  Map.Entry<A,S> run(S s){ return runF.apply(s); }
  static <S,A> State<S,A> pure(A a){ return new State<>(s -> Map.entry(a,s)); }
  <B> State<S,B> map(Function<A,B> f){ return flatMap(a -> State.pure(f.apply(a))); }
  <B> State<S,B> flatMap(Function<A, State<S,B>> f){
    return new State<>(s -> { var r=runF.apply(s); return f.apply(r.getKey()).runF.apply(r.getValue()); });
  }
  static State<Integer,Integer> nextInt(){ return new State<>(s -> { int n=Math.abs((s*1103515245+12345)%100); return Map.entry(n, n==0?1:n); }); }
  static State<Integer,Integer> deposit(int cents){ return new State<>(bal -> Map.entry(bal+cents, bal+cents)); }
}
