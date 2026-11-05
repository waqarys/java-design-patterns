package com.buildwithwaqar.patterns.functional.cake.problem;

public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Cake: repo → service → controller ===");
        System.out.println("--- naive ---");
        System.out.println(new NaiveApp().postRegister("u1", "a@b.c"));

        System.out.println("--- live cake ---");
        var live = new AppCake.LiveCake();
        System.out.println(live.userController().postRegister("u2", "x@y.z"));
        System.out.println(live.userController().getWelcome("u2"));
        System.out.println("mail=" + live.mailbox());
        System.out.println(live.userController().postRegister("u2", "x@y.z")); // conflict

        System.out.println("--- test cake with fakes ---");
        var test = new AppCake.TestCake().seed(new AppCake.User("seed", "s@e.ed"));
        System.out.println(test.userController().getWelcome("seed"));
        System.out.println(test.userController().postRegister("u3", "t@est.io"));
        System.out.println("mail=" + test.mailbox());
        System.out.println(test.userController().getWelcome("missing"));
    }
}
