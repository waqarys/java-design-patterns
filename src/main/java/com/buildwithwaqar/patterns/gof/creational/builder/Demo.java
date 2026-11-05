package com.buildwithwaqar.patterns.gof.creational.builder;

public final class Demo {
    public static void main(String[] args) {
        System.out.println("=== Builder walkthrough ===");
        var req = HttpRequest.builder("https://api.buildwithwaqar.dev/v1/videos")
                .method("POST")
                .header("Authorization", "Bearer demo")
                .header("Content-Type", "application/json")
                .body("{\"title\":\"Singleton\"}")
                .timeoutMs(2500)
                .build();
        System.out.println("method=" + req.method());
        System.out.println("url=" + req.url());
        System.out.println("auth=" + req.headers().get("Authorization"));
        System.out.println("timeout=" + req.timeoutMs());
        var call = ApiCall.get("https://x").withBody("hi").withTimeout(100);
        System.out.println("apiCall=" + call.method() + " body=" + call.body() + " t=" + call.timeoutMs());
    }
}
