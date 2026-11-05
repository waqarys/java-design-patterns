package com.buildwithwaqar.patterns.enterprise.specification;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Specification walkthrough ===");
    Specification<Product> rule = new PriceSpec(1000).and(new InStockSpec());
    for (Product p : new Product[]{new Product("mug", 500, true), new Product("mug", 500, false), new Product("tv", 50000, true)}) {
      System.out.println(p + " => " + rule.test(p));
    }
  }
}
