package com.buildwithwaqar.patterns.functional.tagless;
import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Tagless Final walkthrough ===");
    var console = new TaglessFinal.OptionConsole("Waqar");
    var result = TaglessFinal.greet(OptionKind.MONAD, console);
    System.out.println("name=" + OptionKind.lower(result).get());
  }
}
