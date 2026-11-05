package com.buildwithwaqar.patterns.functional.lens.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void update(){ var p=new Person("A", new Address("X","1")); assertEquals("NYC", PersonLenses.personCity.getA(PersonLenses.personCity.setA(p,"NYC"))); } }
