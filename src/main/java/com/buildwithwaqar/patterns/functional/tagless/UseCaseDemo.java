package com.buildwithwaqar.patterns.functional.tagless;

import com.buildwithwaqar.patterns.functional.tagless.TaglessFinal;
import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
/** Problem: greet via Console algebra; swap test vs prod interpreters. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Tagless Final problem: swappable Console ===");
    var console = new TaglessFinal.OptionConsole("Waqar");
    System.out.println("name=" + OptionKind.lower(TaglessFinal.greet(OptionKind.MONAD, console)).get());
  }
}
