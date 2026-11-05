package com.buildwithwaqar.patterns.concurrency.futurepromise.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void composes(){ var s=new ThumbService(); assertEquals("https://cdn/a.jpg", s.render("a").thenCompose(s::toUrl).join()); }
@Test void badId(){ var s=new ThumbService(); assertThrows(CompletionException.class, ()->s.render("").join()); } }
