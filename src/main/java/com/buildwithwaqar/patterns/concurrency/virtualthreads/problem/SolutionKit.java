package com.buildwithwaqar.patterns.concurrency.virtualthreads.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class UrlChecker {
  int checkAll(int n) throws Exception {
    try (var scope = Executors.newVirtualThreadPerTaskExecutor()) {
      List<Future<Boolean>> fs=new ArrayList<>();
      for (int i=0;i<n;i++) fs.add(scope.submit(() -> true));
      int ok=0; for (var f: fs) if (f.get()) ok++;
      return ok;
    }
  }
}
