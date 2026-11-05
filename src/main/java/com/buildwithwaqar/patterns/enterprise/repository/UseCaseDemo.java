package com.buildwithwaqar.patterns.enterprise.repository;

import com.buildwithwaqar.patterns.enterprise.repository.*;
/** Problem: persist users without leaking Map/SQL into services. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Repository problem: user persistence ===");
    var repo = new InMemoryUserRepository();
    repo.save(new User("1", "waqar@bwq.dev"));
    System.out.println(repo.findById("1").orElseThrow());
    System.out.println("missing=" + repo.findById("x"));
  }
}
