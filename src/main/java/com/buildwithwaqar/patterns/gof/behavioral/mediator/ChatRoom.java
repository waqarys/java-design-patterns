package com.buildwithwaqar.patterns.gof.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

interface ChatMediator {
    void register(User user);
    void send(String from, String message);
}

public final class ChatRoom implements ChatMediator {
    private final List<User> users = new ArrayList<>();
    private final List<String> log = new ArrayList<>();

    public void register(User user) { users.add(user); }

    public void send(String from, String message) {
        var line = from + ": " + message;
        log.add(line);
        for (var u : users) if (!u.name().equals(from)) u.receive(line);
    }

    List<String> log() { return List.copyOf(log); }
}

final class User {
    private final String name;
    private final ChatMediator mediator;
    private final List<String> inbox = new ArrayList<>();

    User(String name, ChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.register(this);
    }

    String name() { return name; }
    void send(String msg) { mediator.send(name, msg); }
    void receive(String msg) { inbox.add(msg); }
    List<String> inbox() { return List.copyOf(inbox); }
}
