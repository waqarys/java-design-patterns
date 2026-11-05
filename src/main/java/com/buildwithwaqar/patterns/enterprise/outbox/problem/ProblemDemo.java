package com.buildwithwaqar.patterns.enterprise.outbox.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Outbox: order + event atomically ===");
    System.out.println("--- naive ---");
try { NaiveOrderPub.place("o1"); } catch(Exception e){ System.out.println("caught="+e.getMessage()); }
System.out.println("--- outbox ---");
var store=new OutboxStore(); store.placeOrder("o1","OrderPlaced:o1");
var relay=new OutboxRelay(store); System.out.println("relayed="+relay.relayOnce()); System.out.println("published="+relay.published());
System.out.println("relayedAgain="+relay.relayOnce());
  }
}
