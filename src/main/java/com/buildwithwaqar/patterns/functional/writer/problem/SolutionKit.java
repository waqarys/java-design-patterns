package com.buildwithwaqar.patterns.functional.writer.problem;

import java.util.*;
import java.util.function.*;

record Writer<W,A>(A value, List<W> log){
  static <W,A> Writer<W,A> pure(A a){ return new Writer<>(a, List.of()); }
  <B> Writer<W,B> map(Function<A,B> f){ return new Writer<>(f.apply(value), log); }
  <B> Writer<W,B> flatMap(Function<A, Writer<W,B>> f){
    var next=f.apply(value); List<W> merged=new ArrayList<>(log); merged.addAll(next.log); return new Writer<>(next.value, merged);
  }
  static <W,A> Writer<W,A> tell(W w, A a){ return new Writer<>(a, List.of(w)); }
}
final class Audited {
  static Writer<String,Integer> addLine(int cents){ return Writer.tell("line="+cents, cents); }
  static Writer<String,Integer> total(List<Integer> lines){
    Writer<String,Integer> acc=Writer.pure(0);
    for(int c: lines){ acc = acc.flatMap(sum -> addLine(c).map(v -> sum+v)); }
    return acc;
  }
}
