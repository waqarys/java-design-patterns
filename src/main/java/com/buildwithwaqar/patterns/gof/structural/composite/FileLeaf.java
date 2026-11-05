package com.buildwithwaqar.patterns.gof.structural.composite;

public record FileLeaf(String name, int size) implements FileSystemNode {}
