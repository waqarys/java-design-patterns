package com.buildwithwaqar.patterns.gof.structural.composite;

import java.util.ArrayList;
import java.util.List;

public final class DirectoryComposite implements FileSystemNode {
    private final String name;
    private final List<FileSystemNode> children = new ArrayList<>();

    public DirectoryComposite(String name) { this.name = name; }

    public DirectoryComposite add(FileSystemNode node) {
        children.add(node);
        return this;
    }

    @Override public String name() { return name; }

    @Override public int size() {
        return children.stream().mapToInt(FileSystemNode::size).sum();
    }
}
