package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public final class EmailFactory extends NotifierFactory { public Notifier create() { return new EmailNotifier(); } }
