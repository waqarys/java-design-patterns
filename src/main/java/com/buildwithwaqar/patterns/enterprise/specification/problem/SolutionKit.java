package com.buildwithwaqar.patterns.enterprise.specification.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

record Product(String id, int priceCents, int stock){}
interface Spec { boolean isSatisfiedBy(Product p); default Spec and(Spec o){ return p -> this.isSatisfiedBy(p) && o.isSatisfiedBy(p); } }
final class CheapSpec implements Spec { private final int max; CheapSpec(int max){this.max=max;} public boolean isSatisfiedBy(Product p){ return p.priceCents()<max; } }
final class InStockSpec implements Spec { public boolean isSatisfiedBy(Product p){ return p.stock()>0; } }
