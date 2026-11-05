package com.buildwithwaqar.patterns.functional.lens.problem;

import java.util.*;

public final class NaivePerson {
  public static String move(String personJson, String city){ return personJson.replace("Old", city); }
}
