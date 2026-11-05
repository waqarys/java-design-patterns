package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public final class SmsFactory extends NotifierFactory { public Notifier create() { return new SmsNotifier(); } }
