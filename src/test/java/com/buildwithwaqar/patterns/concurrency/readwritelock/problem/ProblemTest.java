package com.buildwithwaqar.patterns.concurrency.readwritelock.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void writeVisible(){ var c=new CachedManifest(); c.write("x"); assertEquals("x", c.read()); } }
