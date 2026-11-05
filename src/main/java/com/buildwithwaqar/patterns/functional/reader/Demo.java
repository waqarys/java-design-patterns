package com.buildwithwaqar.patterns.functional.reader;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Reader walkthrough ===");
    Reader<Config, String> program = Reader.<Config>ask()
      .flatMap(cfg -> Reader.pure(cfg.greeting() + " " + cfg.name() + "!"));
    System.out.println(program.run(new Config("Namaste", "Waqar")));
  }
}
