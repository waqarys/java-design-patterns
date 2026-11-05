package com.buildwithwaqar.patterns.enterprise.outbox;

import com.buildwithwaqar.patterns.enterprise.outbox.Outbox;
/** Problem: never lose domain events when the broker is down — persist then relay. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Outbox problem: reliable publish ===");
    var o = new Outbox();
    o.placeOrder("ord-1", "{\"sku\":\"course\"}");
    System.out.println("unpublished=" + o.unpublished().size());
    o.publishDue();
    System.out.println("afterRelay=" + o.unpublished().size() + " orders=" + o.orders());
  }
}
