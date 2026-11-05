package com.buildwithwaqar.patterns.concurrency.threadpool.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class SimpleThreadPool {
  private final ExecutorService pool;
  SimpleThreadPool(int n){ pool=Executors.newFixedThreadPool(n); }
  <T> Future<T> submit(Callable<T> c){ return pool.submit(c); }
  void shutdown(){ pool.shutdown(); }
}
