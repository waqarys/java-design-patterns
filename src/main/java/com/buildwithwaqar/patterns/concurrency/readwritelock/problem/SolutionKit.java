package com.buildwithwaqar.patterns.concurrency.readwritelock.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class CachedManifest {
  private final ReadWriteLock lock=new ReentrantReadWriteLock();
  private String value="v1";
  String read(){ lock.readLock().lock(); try { return value; } finally { lock.readLock().unlock(); } }
  void write(String v){ lock.writeLock().lock(); try { value=v; } finally { lock.writeLock().unlock(); } }
}
