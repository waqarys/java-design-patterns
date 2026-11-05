package com.buildwithwaqar.patterns.enterprise.specification;

import com.buildwithwaqar.patterns.enterprise.specification.*;
/** Problem: compose catalog filters (price AND stock) without nested ifs. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Specification problem: catalog filter ===");
    Specification<Product> rule = new PriceSpec(1000).and(new InStockSpec());
    for (var p : new Product[]{new Product("mug",500,true), new Product("tv",50000,true), new Product("mug",500,false)})
      System.out.println(p + " => " + rule.test(p));
  }
}
