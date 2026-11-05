package com.buildwithwaqar.patterns.enterprise.outbox.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

record OutboxRow(String id, String payload, boolean published){}
final class OutboxStore {
  private final List<OutboxRow> rows=new ArrayList<>();
  private final List<String> orders=new ArrayList<>();
  void placeOrder(String id, String payload){ orders.add(id); rows.add(new OutboxRow(id, payload, false)); }
  List<OutboxRow> unpublished(){ return rows.stream().filter(r->!r.published()).toList(); }
  void markPublished(String id){
    for (int i=0;i<rows.size();i++) if(rows.get(i).id().equals(id)) rows.set(i, new OutboxRow(id, rows.get(i).payload(), true));
  }
  List<String> orders(){ return List.copyOf(orders); }
}
final class OutboxRelay {
  private final OutboxStore store; private final List<String> published=new ArrayList<>();
  OutboxRelay(OutboxStore store){ this.store=store; }
  int relayOnce(){ int n=0; for(var r: store.unpublished()){ published.add(r.payload()); store.markPublished(r.id()); n++; } return n; }
  List<String> published(){ return List.copyOf(published); }
}
