package com.buildwithwaqar.patterns.functional.trampoline.problem;

import java.util.*;
import java.util.function.*;

sealed interface Trampoline<A> permits Done, More {
  A run();
  static <A> Trampoline<A> done(A a){ return new Done<>(a); }
  static <A> Trampoline<A> more(Supplier<Trampoline<A>> next){ return new More<>(next); }
}
record Done<A>(A value) implements Trampoline<A> { public A run(){ return value; } }
record More<A>(Supplier<Trampoline<A>> next) implements Trampoline<A> {
  public A run(){ Trampoline<A> cur=this; while(cur instanceof More<A> m) cur=m.next.get(); return ((Done<A>)cur).value(); }
}
final class SafeSum {
  static Trampoline<Long> sum(long n, long acc){
    return n<=0? Trampoline.done(acc): Trampoline.more(() -> sum(n-1, acc+n));
  }
}
