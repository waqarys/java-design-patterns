package com.buildwithwaqar.patterns.functional.collections.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class Playlist {
  private final List<String> songs;
  Playlist(List<String> songs){ this.songs=List.copyOf(songs); }
  Playlist add(String s){ var n=new ArrayList<>(songs); n.add(s); return new Playlist(n); }
  List<String> songs(){ return songs; }
}
