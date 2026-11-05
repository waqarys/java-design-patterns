package com.buildwithwaqar.patterns.functional.lazy.problem;

import java.util.*;
import java.util.function.*;

final class Lazy<A> {
  private final Supplier<A> thunk; private A value; private boolean ready;
  Lazy(Supplier<A> thunk){ this.thunk=thunk; }
  static <A> Lazy<A> of(Supplier<A> s){ return new Lazy<>(s); }
  synchronized A get(){ if(!ready){ value=thunk.get(); ready=true; } return value; }
}
final class IO<A> {
  private final Supplier<A> effect; IO(Supplier<A> effect){ this.effect=effect; }
  static <A> IO<A> delay(Supplier<A> s){ return new IO<>(s); }
  <B> IO<B> map(Function<A,B> f){ return new IO<>(() -> f.apply(effect.get())); }
  A unsafeRun(){ return effect.get(); }
}
