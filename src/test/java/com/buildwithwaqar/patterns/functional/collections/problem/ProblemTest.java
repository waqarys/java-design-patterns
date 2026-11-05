package com.buildwithwaqar.patterns.functional.collections.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void share(){ var p=new Playlist(List.of("a")); var p2=p.add("b"); assertEquals(List.of("a"), p.songs()); assertEquals(List.of("a","b"), p2.songs()); } }
