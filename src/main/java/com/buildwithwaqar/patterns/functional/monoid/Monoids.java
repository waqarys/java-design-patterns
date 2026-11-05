package com.buildwithwaqar.patterns.functional.monoid;

public final class Monoids {
    private Monoids() {}

    public static final Monoid<Integer> INT_SUM = new Monoid<>() {
        public Integer empty() { return 0; }
        public Integer combine(Integer a, Integer b) { return a + b; }
    };

    public static final Monoid<String> STRING = new Monoid<>() {
        public String empty() { return ""; }
        public String combine(String a, String b) { return a + b; }
    };
}
