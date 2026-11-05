package com.buildwithwaqar.patterns.gof.structural.composite.problem;

import java.util.*;

import java.util.*;
sealed interface FsNode permits FileNode, DirNode { String name(); int size(); }
record FileNode(String name, int size) implements FsNode {}
final class DirNode implements FsNode {
  private final String name; private final List<FsNode> kids = new ArrayList<>();
  public DirNode(String name){ this.name=name; }
  public DirNode add(FsNode n){ kids.add(n); return this; }
  public String name(){ return name; }
  public int size(){ return kids.stream().mapToInt(FsNode::size).sum(); }
}
