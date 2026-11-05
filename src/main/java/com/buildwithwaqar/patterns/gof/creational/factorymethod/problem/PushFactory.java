package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public final class PushFactory extends NotifierFactory { public Notifier create() { return new PushNotifier(); } }
