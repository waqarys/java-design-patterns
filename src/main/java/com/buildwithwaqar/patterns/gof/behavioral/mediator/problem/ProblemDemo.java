package com.buildwithwaqar.patterns.gof.behavioral.mediator.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Mediator: chat room ===");
    System.out.println("--- naive ---"); NaiveChat.dm("Ada","Bob","hi");
System.out.println("--- mediator ---");
var room=new ChatRoom(); List<String> bob=new ArrayList<>();
room.join("Ada", m->{}); room.join("Bob", bob::add);
room.send("Ada","Bob","hi");
System.out.println("bobInbox="+bob); System.out.println("log="+room.log());
try { room.send("Ada","Cara","x"); } catch (IllegalArgumentException e){ System.out.println("caught="+e.getMessage()); }
  }
}
