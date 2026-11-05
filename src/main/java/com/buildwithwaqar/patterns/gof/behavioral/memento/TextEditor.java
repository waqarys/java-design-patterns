package com.buildwithwaqar.patterns.gof.behavioral.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public final class TextEditor {
    private String text = "";

    public void type(String chunk) { text += chunk; }
    public String text() { return text; }

    public Memento save() { return new Memento(text); }
    public void restore(Memento m) { text = m.state(); }

    public record Memento(String state) {}

    /** Caretaker */
    public static final class History {
        private final Deque<Memento> stack = new ArrayDeque<>();
        public void push(Memento m) { stack.push(m); }
        public Memento pop() { return stack.pop(); }
        public boolean isEmpty() { return stack.isEmpty(); }
    }
}
