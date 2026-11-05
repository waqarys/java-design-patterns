package com.buildwithwaqar.patterns.functional.kleisli.problem;

import java.util.*;
import java.util.function.*;

final class Kleisli<A,B> {
  private final Function<A, Optional<B>> run;
  Kleisli(Function<A, Optional<B>> run){ this.run=run; }
  static <A,B> Kleisli<A,B> of(Function<A, Optional<B>> f){ return new Kleisli<>(f); }
  <C> Kleisli<A,C> andThen(Kleisli<B,C> next){ return new Kleisli<>(a -> run.apply(a).flatMap(next.run)); }
  Optional<B> apply(A a){ return run.apply(a); }
}
final class AuthPipe {
  static final Kleisli<String,Integer> parse = Kleisli.of(s -> { try { return Optional.of(Integer.parseInt(s)); } catch(Exception e){ return Optional.empty(); } });
  static final Kleisli<Integer,Integer> enrich = Kleisli.of(id -> id>0? Optional.of(id+1000): Optional.empty());
  static final Kleisli<Integer,String> authorize = Kleisli.of(id -> Optional.of("allow:"+id));
  static final Kleisli<String,String> pipeline = parse.andThen(enrich).andThen(authorize);
}
