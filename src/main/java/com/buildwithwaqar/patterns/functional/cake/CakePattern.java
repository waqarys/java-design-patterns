package com.buildwithwaqar.patterns.functional.cake;

/**
 * Cake pattern (Scala) emulated with Java interfaces + default methods.
 * Self-type: a component declares what it needs via abstract getters;
 * the "cake" assembles mixins into one object.
 */
public final class CakePattern {
    private CakePattern() {}

    public interface UserRepoComponent {
        UserRepo userRepo();
    }

    public interface UserRepo {
        String findEmail(String id);
    }

    public interface EmailServiceComponent {
        EmailService emailService();
    }

    public interface EmailService {
        String send(String to, String body);
    }

    public interface UserServiceComponent extends UserRepoComponent, EmailServiceComponent {
        default UserService userService() {
            return id -> {
                var email = userRepo().findEmail(id);
                return emailService().send(email, "welcome " + id);
            };
        }
    }

    public interface UserService {
        String welcome(String id);
    }

    /** The cake — wires live implementations. */
    public static final class App implements UserServiceComponent {
        public UserRepo userRepo() { return id -> id + "@example.com"; }
        public EmailService emailService() { return (to, body) -> "sent:" + to + ":" + body; }
    }
}
