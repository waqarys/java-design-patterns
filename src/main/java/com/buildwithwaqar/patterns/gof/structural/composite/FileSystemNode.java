package com.buildwithwaqar.patterns.gof.structural.composite;

public sealed interface FileSystemNode permits FileLeaf, DirectoryComposite {
    String name();
    int size();
}
