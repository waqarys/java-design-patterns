package com.buildwithwaqar.patterns.functional.collections.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Persistent collections: playlist edits ===");
    System.out.println("--- naive ---"); var n=new NaivePlaylist(); n.add("a"); System.out.println(n.songs);
System.out.println("--- persistent ---");
var p=new Playlist(List.of("a")); var p2=p.add("b");
System.out.println(p.songs()); System.out.println(p2.songs());
  }
}
