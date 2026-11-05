package com.buildwithwaqar.patterns.gof.behavioral.interpreter;

import java.util.Map;

public sealed interface Expression permits NumberExpr, AddExpr, MulExpr {
    int evaluate(Map<String, Integer> ctx);
}

record NumberExpr(int value) implements Expression {
    public int evaluate(Map<String, Integer> ctx) { return value; }
}

record AddExpr(Expression left, Expression right) implements Expression {
    public int evaluate(Map<String, Integer> ctx) {
        return left.evaluate(ctx) + right.evaluate(ctx);
    }
}

record MulExpr(Expression left, Expression right) implements Expression {
    public int evaluate(Map<String, Integer> ctx) {
        return left.evaluate(ctx) * right.evaluate(ctx);
    }
}
