package com.buildwithwaqar.patterns.concurrency.futurepromise.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class ThumbService {
  CompletableFuture<String> render(String id){
    return CompletableFuture.supplyAsync(() -> {
      if (id==null || id.isBlank()) throw new IllegalArgumentException("id");
      return id+".bin";
    });
  }
  CompletableFuture<String> toUrl(String blob){ return CompletableFuture.completedFuture("https://cdn/"+blob.replace(".bin",".jpg")); }
}
