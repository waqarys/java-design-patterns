package com.buildwithwaqar.patterns.gof.creational.builder.problem;

import java.util.*;

final class HttpRequest {
  private final String url, method, body; private final boolean auth;
  private HttpRequest(Builder b){ url=b.url; method=b.method; body=b.body; auth=b.auth; }
  public String summarize(){ return method+" "+url+" body="+body+" auth="+auth; }
  public static final class Builder {
    private String url, method="GET", body=""; private boolean auth;
    public Builder url(String u){ this.url=u; return this; }
    public Builder method(String m){ this.method=m; return this; }
    public Builder body(String b){ this.body=b; return this; }
    public Builder auth(boolean a){ this.auth=a; return this; }
    public HttpRequest build(){
      if (url==null || url.isBlank()) throw new IllegalArgumentException("url required");
      return new HttpRequest(this);
    }
  }
}
