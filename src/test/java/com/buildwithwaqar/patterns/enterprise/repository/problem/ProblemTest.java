package com.buildwithwaqar.patterns.enterprise.repository.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void finds(){ var r=new InMemoryUserRepository(); r.save(new User("1","a")); assertTrue(r.findById("1").isPresent()); assertTrue(r.findById("z").isEmpty()); } }
