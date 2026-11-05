package com.buildwithwaqar.patterns.concurrency.activeobject.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class ActiveLedger implements AutoCloseable {
  private int bal; private final ExecutorService servant=Executors.newSingleThreadExecutor();
  CompletableFuture<Void> deposit(int c){ return CompletableFuture.runAsync(() -> { bal+=c; }, servant); }
  CompletableFuture<Integer> balance(){ return CompletableFuture.supplyAsync(() -> bal, servant); }
  public void close(){ servant.shutdown(); }
}
