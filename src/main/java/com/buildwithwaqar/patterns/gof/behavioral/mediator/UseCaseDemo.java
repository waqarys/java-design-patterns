package com.buildwithwaqar.patterns.gof.behavioral.mediator;

import com.buildwithwaqar.patterns.gof.behavioral.mediator.*;
/** Problem: users chat without holding references to each other. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Mediator problem: chat room ===");
    var room = new ChatRoom();
    var alice = new User("alice", room);
    var bob = new User("bob", room);
    alice.send("ship it");
    System.out.println("bob=" + bob.inbox());
    System.out.println("alice=" + alice.inbox());
  }
}
