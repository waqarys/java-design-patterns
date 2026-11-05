package com.buildwithwaqar.patterns.gof.creational.builder.problem;

import java.util.*;

public final class NaiveHttp {
  public static String build(String url, String method, String body, boolean auth) {
    if (url == null || url.isBlank()) throw new IllegalArgumentException("url");
    return method + " " + url + " body=" + body + " auth=" + auth;
  }
}
