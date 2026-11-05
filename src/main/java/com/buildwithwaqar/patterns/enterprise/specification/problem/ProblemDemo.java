package com.buildwithwaqar.patterns.enterprise.specification.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Specification: cheap AND in-stock ===");
    System.out.println("--- naive ---"); System.out.println(NaiveFilter.filter(List.of(new int[]{500,1}, new int[]{2000,5}, new int[]{100,0})));
System.out.println("--- specification ---");
var catalog=List.of(new Product("a",500,1), new Product("b",2000,5), new Product("c",100,0));
Spec spec=new CheapSpec(1000).and(new InStockSpec());
System.out.println(catalog.stream().filter(spec::isSatisfiedBy).map(Product::id).toList());
  }
}
