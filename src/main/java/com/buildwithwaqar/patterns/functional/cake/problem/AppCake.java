package com.buildwithwaqar.patterns.functional.cake.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Multi-layer cake: Repo → Service → Controller, with a test cake swapping fakes. */
public final class AppCake {
    private AppCake() {}

    public record User(String id, String email) {}

    public interface UserRepo {
        Optional<User> find(String id);
        User save(User user);
    }

    public interface EmailGateway {
        void send(String to, String body);
        List<String> sent();
    }

    public interface UserService {
        User register(String id, String email);
        String welcome(String id);
    }

    public interface UserController {
        String postRegister(String id, String email);
        String getWelcome(String id);
    }

    public interface UserRepoComponent { UserRepo userRepo(); }
    public interface EmailComponent { EmailGateway emailGateway(); }
    public interface UserServiceComponent extends UserRepoComponent, EmailComponent {
        default UserService userService() {
            return new UserService() {
                public User register(String id, String email) {
                    var existing = userRepo().find(id);
                    if (existing.isPresent()) throw new IllegalStateException("exists:" + id);
                    var u = userRepo().save(new User(id, email));
                    emailGateway().send(email, "welcome " + id);
                    return u;
                }
                public String welcome(String id) {
                    return userRepo().find(id)
                            .map(u -> "hello " + u.email())
                            .orElseThrow(() -> new IllegalArgumentException("missing:" + id));
                }
            };
        }
    }
    public interface UserControllerComponent extends UserServiceComponent {
        default UserController userController() {
            return new UserController() {
                public String postRegister(String id, String email) {
                    try {
                        var u = userService().register(id, email);
                        return "201:" + u.id();
                    } catch (IllegalStateException e) {
                        return "409:" + e.getMessage();
                    }
                }
                public String getWelcome(String id) {
                    try {
                        return "200:" + userService().welcome(id);
                    } catch (IllegalArgumentException e) {
                        return "404:" + e.getMessage();
                    }
                }
            };
        }
    }

    public static final class LiveCake implements UserControllerComponent {
        private final Map<String, User> db = new LinkedHashMap<>();
        private final List<String> mail = new ArrayList<>();
        public UserRepo userRepo() {
            return new UserRepo() {
                public Optional<User> find(String id) { return Optional.ofNullable(db.get(id)); }
                public User save(User user) { db.put(user.id(), user); return user; }
            };
        }
        public EmailGateway emailGateway() {
            return new EmailGateway() {
                public void send(String to, String body) { mail.add(to + ":" + body); }
                public List<String> sent() { return List.copyOf(mail); }
            };
        }
        public List<String> mailbox() { return emailGateway().sent(); }
    }

    /** Test cake — fake repo + recording email. */
    public static final class TestCake implements UserControllerComponent {
        private final Map<String, User> db = new LinkedHashMap<>();
        private final List<String> mail = new ArrayList<>();
        public TestCake seed(User u) { db.put(u.id(), u); return this; }
        public UserRepo userRepo() {
            return new UserRepo() {
                public Optional<User> find(String id) { return Optional.ofNullable(db.get(id)); }
                public User save(User user) { db.put(user.id(), user); return user; }
            };
        }
        public EmailGateway emailGateway() {
            return new EmailGateway() {
                public void send(String to, String body) { mail.add(to + ":" + body); }
                public List<String> sent() { return List.copyOf(mail); }
            };
        }
        public List<String> mailbox() { return List.copyOf(mail); }
    }
}
