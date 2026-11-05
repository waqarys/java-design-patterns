package com.buildwithwaqar.patterns.gof.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

interface Command { void execute(); void undo(); }

final class Editor {
    private final StringBuilder text = new StringBuilder();
    void append(String s) { text.append(s); }
    void deleteLast(int n) { text.delete(Math.max(0, text.length() - n), text.length()); }
    String text() { return text.toString(); }
}

record WriteCommand(Editor editor, String chunk) implements Command {
    public void execute() { editor.append(chunk); }
    public void undo() { editor.deleteLast(chunk.length()); }
}

/** Invoker with undo stack. */
public final class CommandBus {
    private final Deque<Command> history = new ArrayDeque<>();

    public void run(Command command) {
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) history.pop().undo();
    }
}
