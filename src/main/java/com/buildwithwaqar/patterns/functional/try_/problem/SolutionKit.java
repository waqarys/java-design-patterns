package com.buildwithwaqar.patterns.functional.try_.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

sealed interface Try<A> permits Success, Failure { A getOrElse(A fallback); boolean isSuccess(); }
record Success<A>(A value) implements Try<A> { public A getOrElse(A f){return value;} public boolean isSuccess(){return true;} }
record Failure<A>(Throwable error) implements Try<A> { public A getOrElse(A f){return f;} public boolean isSuccess(){return false;} }
final class Tries {
  static <A> Try<A> of(Callable<A> c){ try { return new Success<>(c.call()); } catch(Exception e){ return new Failure<>(e); } }
}
