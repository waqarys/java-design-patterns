package com.buildwithwaqar.patterns.gof.creational.prototype.problem;

import java.util.*;

public final class NaiveTemplate {
  public static String clone(String title){ return title + " (copy)"; } // shallow, loses attendees
}
