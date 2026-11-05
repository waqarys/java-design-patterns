package com.buildwithwaqar.patterns.enterprise.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
    @Test void savesAndFinds() {
        var repo = new InMemoryUserRepository();
        repo.save(new User("1", "a@b.c"));
        assertEquals("a@b.c", repo.findById("1").orElseThrow().email());
        assertTrue(repo.findById("x").isEmpty());
    }
}
