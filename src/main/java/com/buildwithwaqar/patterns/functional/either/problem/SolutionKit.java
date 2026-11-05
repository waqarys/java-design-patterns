package com.buildwithwaqar.patterns.functional.either.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

sealed interface Either<L,R> permits Left, Right { boolean isRight(); L left(); R right(); }
record Left<L,R>(L value) implements Either<L,R> { public boolean isRight(){return false;} public L left(){return value;} public R right(){throw new IllegalStateException();} }
record Right<L,R>(R value) implements Either<L,R> { public boolean isRight(){return true;} public L left(){throw new IllegalStateException();} public R right(){return value;} }
final class UserPipeline {
  static Either<String,Integer> parseInt(String s){ try { return new Right<>(Integer.parseInt(s)); } catch(Exception e){ return new Left<>("parse"); } }
  static Either<String,Integer> requirePositive(int n){ return n>0? new Right<>(n): new Left<>("positive"); }
  static Either<String,String> label(int n){ return new Right<>("user:"+n); }
  static Either<String,String> run(String raw){
    var a=parseInt(raw); if(!a.isRight()) return new Left<>(a.left());
    var b=requirePositive(a.right()); if(!b.isRight()) return new Left<>(b.left());
    return label(b.right());
  }
}
