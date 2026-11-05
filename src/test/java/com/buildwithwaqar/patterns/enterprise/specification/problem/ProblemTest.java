package com.buildwithwaqar.patterns.enterprise.specification.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void and(){ Spec s=new CheapSpec(1000).and(new InStockSpec()); assertTrue(s.isSatisfiedBy(new Product("a",500,1))); assertFalse(s.isSatisfiedBy(new Product("b",500,0))); } }
