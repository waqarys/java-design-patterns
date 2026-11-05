package com.buildwithwaqar.patterns.enterprise.repository;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Repository walkthrough ===");
    var repo = new InMemoryUserRepository();
    repo.save(new User("u1", "waqar@bwq.dev"));
    repo.save(new User("u2", "editor@bwq.dev"));
    System.out.println("find=" + repo.findById("u1").orElseThrow());
    System.out.println("missing=" + repo.findById("nope"));
    System.out.println("count=" + repo.findAll().size());
  }
}
