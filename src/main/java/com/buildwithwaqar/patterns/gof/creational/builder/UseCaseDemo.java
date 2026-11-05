package com.buildwithwaqar.patterns.gof.creational.builder;

import com.buildwithwaqar.patterns.gof.creational.builder.HttpRequest;

/** Problem: construct API requests with many optional headers without telescoping ctors. */
public final class UseCaseDemo {
    public static void main(String[] args) {
        System.out.println("=== Builder problem: HTTP request with optionals ===");
        var req = HttpRequest.builder("https://api.example/v1/videos")
                .method("POST")
                .header("Authorization", "Bearer t")
                .body("{\"title\":\"Adapter\"}")
                .timeoutMs(3000)
                .build();
        System.out.println("method=" + req.method() + " auth=" + req.headers().get("Authorization")
                + " timeout=" + req.timeoutMs());
    }
}
