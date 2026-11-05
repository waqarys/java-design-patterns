package com.buildwithwaqar.patterns.gof.behavioral.mediator;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Mediator walkthrough ===");
    var room = new ChatRoom();
    var alice = new User("alice", room);
    var bob = new User("bob", room);
    var cara = new User("cara", room);
    alice.send("stand-up in 5");
    bob.send("ack");
    System.out.println("bobInbox=" + bob.inbox());
    System.out.println("caraInbox=" + cara.inbox());
    System.out.println("aliceInbox=" + alice.inbox());
    System.out.println("log=" + room.log());
  }
}
