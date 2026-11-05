package com.buildwithwaqar.patterns.gof.creational.prototype;

import java.util.ArrayList;
import java.util.List;

/** Prototype: clone a preconfigured object instead of rebuilding from scratch. */
public final class DocumentTemplate implements Cloneable {
    private String title;
    private final List<String> sections;

    public DocumentTemplate(String title, List<String> sections) {
        this.title = title;
        this.sections = new ArrayList<>(sections);
    }

    public String title() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<String> sections() { return sections; }

    /** Deep copy so mutating the clone does not affect the prototype. */
    @Override
    public DocumentTemplate clone() {
        return new DocumentTemplate(title, sections);
    }
}

/** Modern: records are naturally copyable via canonical constructor / with-style helpers. */
record SlideDeck(String title, List<String> slides) {
    SlideDeck {
        slides = List.copyOf(slides);
    }

    SlideDeck customized(String newTitle) {
        return new SlideDeck(newTitle, slides);
    }
}
