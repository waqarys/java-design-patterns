package com.buildwithwaqar.patterns.gof.creational.singleton.problem;
import com.buildwithwaqar.patterns.gof.creational.singleton.MetricsRegistry;
import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @BeforeEach void reset() { MetricsRegistry.getInstance().reset(); }
  @Test void naiveSplits() { var a=new NaiveMetrics(); var b=new NaiveMetrics(); a.hit(); b.hit(); assertEquals(1,a.hits()); assertEquals(1,b.hits()); }
  @Test void singletonShares() { MetricsRegistry.getInstance().hit(); MetricsRegistry.getInstance().hit(); assertEquals(2, MetricsRegistry.getInstance().hits()); }
}
