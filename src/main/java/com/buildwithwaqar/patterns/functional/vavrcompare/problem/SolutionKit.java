package com.buildwithwaqar.patterns.functional.vavrcompare.problem;

import java.util.*;
import java.util.function.*;

final class OptionCompare {
  static String zazrDescribe(String raw){
    var o = raw==null? Optional.<String>empty(): Optional.of(raw);
    return o.map(s -> "zazr-some:"+s).orElse("zazr-none");
  }
  static String vavrStyleDescribe(String raw){
    // educational stand-in without requiring VAVR API in problem package
    return raw==null? "vavr-none": "vavr-some:"+raw;
  }
}
