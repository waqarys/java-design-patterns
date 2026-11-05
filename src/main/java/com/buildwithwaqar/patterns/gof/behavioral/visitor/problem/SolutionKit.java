package com.buildwithwaqar.patterns.gof.behavioral.visitor.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

sealed interface Expr permits Num, Add, Mul { <R> R accept(Visitor<R> v); }
record Num(int v) implements Expr { public <R> R accept(Visitor<R> v){ return v.visit(this); } }
record Add(Expr l, Expr r) implements Expr { public <R> R accept(Visitor<R> v){ return v.visit(this); } }
record Mul(Expr l, Expr r) implements Expr { public <R> R accept(Visitor<R> v){ return v.visit(this); } }
interface Visitor<R> { R visit(Num n); R visit(Add a); R visit(Mul m); }
final class Pretty implements Visitor<String> {
  public String visit(Num n){ return Integer.toString(n.v()); }
  public String visit(Add a){ return "("+a.l().accept(this)+"+"+a.r().accept(this)+")"; }
  public String visit(Mul m){ return "("+m.l().accept(this)+"*"+m.r().accept(this)+")"; }
}
final class Eval implements Visitor<Integer> {
  public Integer visit(Num n){ return n.v(); }
  public Integer visit(Add a){ return a.l().accept(this)+a.r().accept(this); }
  public Integer visit(Mul m){ return m.l().accept(this)*m.r().accept(this); }
}
