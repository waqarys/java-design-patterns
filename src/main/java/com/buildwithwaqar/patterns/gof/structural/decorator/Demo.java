package com.buildwithwaqar.patterns.gof.structural.decorator;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Decorator walkthrough ===");
    DataSource ds = DataSourceDecorators.encryptedCompressedMemory();
    ds.write("hello world");
    System.out.println("readBack=" + ds.read());
    DataSource raw = new InMemoryDataSource();
    raw.write("plain");
    System.out.println("raw=" + raw.read());
  }
}
