package com.buildwithwaqar.patterns.gof.behavioral.mediator.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

final class ChatRoom {
  private final Map<String, Consumer<String>> users = new HashMap<>();
  private final List<String> log = new ArrayList<>();
  void join(String name, Consumer<String> inbox){ users.put(name, inbox); }
  void send(String from, String to, String msg){
    var inbox = users.get(to);
    if (inbox==null) throw new IllegalArgumentException("missing:"+to);
    String line = from+"->"+to+":"+msg; log.add(line); inbox.accept(line);
  }
  List<String> log(){ return List.copyOf(log); }
}
