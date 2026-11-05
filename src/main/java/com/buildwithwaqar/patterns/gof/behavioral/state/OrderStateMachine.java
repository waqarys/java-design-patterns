package com.buildwithwaqar.patterns.gof.behavioral.state;

public final class OrderStateMachine {
    private State state = new NewState();

    public String status() { return state.name(); }
    public void pay() { state = state.pay(this); }
    public void ship() { state = state.ship(this); }
    public void cancel() { state = state.cancel(this); }

    private sealed interface State permits NewState, PaidState, ShippedState, CancelledState {
        String name();
        default State pay(OrderStateMachine ctx) { throw new IllegalStateException(name()); }
        default State ship(OrderStateMachine ctx) { throw new IllegalStateException(name()); }
        default State cancel(OrderStateMachine ctx) { throw new IllegalStateException(name()); }
    }

    private static final class NewState implements State {
        public String name() { return "NEW"; }
        public State pay(OrderStateMachine ctx) { return new PaidState(); }
        public State cancel(OrderStateMachine ctx) { return new CancelledState(); }
    }
    private static final class PaidState implements State {
        public String name() { return "PAID"; }
        public State ship(OrderStateMachine ctx) { return new ShippedState(); }
        public State cancel(OrderStateMachine ctx) { return new CancelledState(); }
    }
    private static final class ShippedState implements State {
        public String name() { return "SHIPPED"; }
    }
    private static final class CancelledState implements State {
        public String name() { return "CANCELLED"; }
    }
}
