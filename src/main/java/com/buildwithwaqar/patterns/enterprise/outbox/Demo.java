package com.buildwithwaqar.patterns.enterprise.outbox;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Outbox walkthrough ===");
    var outbox = new Outbox();
    outbox.placeOrder("o1", "{\"sku\":\"course-1\"}");
    System.out.println("unpublished=" + outbox.unpublished().size());
    outbox.publishDue();
    System.out.println("unpublishedAfter=" + outbox.unpublished().size());
    System.out.println("orders=" + outbox.orders());
  }
}
