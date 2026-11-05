package com.buildwithwaqar.patterns.enterprise.specification;

import java.util.function.Predicate;

public interface Specification<T> extends Predicate<T> {
    default Specification<T> and(Specification<T> other) {
        return t -> this.test(t) && other.test(t);
    }

    default Specification<T> or(Specification<T> other) {
        return t -> this.test(t) || other.test(t);
    }

    default Specification<T> not() { return t -> !this.test(t); }
}

record Product(String name, int priceCents, boolean inStock) {}

final class PriceSpec implements Specification<Product> {
    private final int max;
    PriceSpec(int max) { this.max = max; }
    public boolean test(Product p) { return p.priceCents() <= max; }
}

final class InStockSpec implements Specification<Product> {
    public boolean test(Product p) { return p.inStock(); }
}
