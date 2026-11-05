package com.buildwithwaqar.patterns.concurrency.producerconsumer.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class BoundedQueue<T> {
  private final ArrayBlockingQueue<T> q;
  BoundedQueue(int cap){ q=new ArrayBlockingQueue<>(cap); }
  void put(T t) throws InterruptedException { q.put(t); }
  T take() throws InterruptedException { return q.take(); }
  int size(){ return q.size(); }
}
