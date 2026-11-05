package com.buildwithwaqar.patterns.gof.creational.abstractfactory;

interface Button { String paint(); }
interface Checkbox { String paint(); }

record LightButton() implements Button {
    public String paint() { return "light-button"; }
}
record DarkButton() implements Button {
    public String paint() { return "dark-button"; }
}
record LightCheckbox() implements Checkbox {
    public String paint() { return "light-checkbox"; }
}
record DarkCheckbox() implements Checkbox {
    public String paint() { return "dark-checkbox"; }
}

/** Family of related products without specifying concrete classes. */
public interface UiFactory {
    Button button();
    Checkbox checkbox();

    static UiFactory of(Theme theme) {
        return switch (theme) {
            case LIGHT -> new LightFactory();
            case DARK -> new DarkFactory();
        };
    }
}

enum Theme { LIGHT, DARK }

record LightFactory() implements UiFactory {
    public Button button() { return new LightButton(); }
    public Checkbox checkbox() { return new LightCheckbox(); }
}

record DarkFactory() implements UiFactory {
    public Button button() { return new DarkButton(); }
    public Checkbox checkbox() { return new DarkCheckbox(); }
}

record Dialog(UiFactory factory) {
    String render() {
        return factory.button().paint() + "+" + factory.checkbox().paint();
    }
}
