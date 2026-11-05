package com.buildwithwaqar.patterns.concurrency.futurepromise;

import com.buildwithwaqar.patterns.concurrency.futurepromise.Promise;
/** Problem: generate a thumbnail asynchronously then compose the CDN URL. */
public final class UseCaseDemo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Future/Promise problem: async thumbnail ===");
    var bytes = Promise.computeAsync(128).toCompletableFuture().get();
    System.out.println("thumbBytes=" + bytes + " url=https://cdn/x/" + bytes + ".jpg");
  }
}
