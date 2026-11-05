package com.buildwithwaqar.patterns.functional.option.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class Port {
  static OptionalInt parse(String s){
    try { int p=Integer.parseInt(s); return p>0? OptionalInt.of(p): OptionalInt.empty(); }
    catch(NumberFormatException e){ return OptionalInt.empty(); }
  }
  static String describe(OptionalInt p){ return p.isPresent()? "port="+p.getAsInt(): "missing"; }
}
