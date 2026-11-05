package com.buildwithwaqar.patterns.gof.structural.decorator.problem;

import java.util.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Decorator: compress+encrypt store ===");
    System.out.println("--- naive ---");
    var n=new NaiveStore(); n.write("secret"); System.out.println(n.read());
    System.out.println("--- decorator stack ---");
    var mem = new InMemoryDataSource();
    DataSource ds = new EncryptionDecorator(new CompressionDecorator(mem));
    ds.write("secret");
    System.out.println("stored-view=" + mem.read());
    System.out.println("read=" + ds.read());
  }
}
