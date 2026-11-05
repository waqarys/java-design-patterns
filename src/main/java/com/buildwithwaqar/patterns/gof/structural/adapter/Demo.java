package com.buildwithwaqar.patterns.gof.structural.adapter;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Adapter walkthrough ===");
    var adapter = new XmlToJsonLoggerAdapter();
    JsonLogger logger = adapter;
    logger.logJson("{\"ok\":true,\"ep\":\"adapter\"}");
    System.out.println("lastXml=" + adapter.lastXml());
  }
}
