package com.buildwithwaqar.patterns.functional.tagless;

import com.buildwithwaqar.patterns.functional.tagless.TaglessFinal;
import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void greets() {
    assertEquals("Ada", OptionKind.lower(TaglessFinal.greet(OptionKind.MONAD, new TaglessFinal.OptionConsole("Ada"))).get());
  }
}
