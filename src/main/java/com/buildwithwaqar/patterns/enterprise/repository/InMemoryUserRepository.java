package com.buildwithwaqar.patterns.enterprise.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

record User(String id, String email) {}

interface UserRepository {
    Optional<User> findById(String id);
    User save(User user);
    Collection<User> findAll();
}

/** Collection-like interface over persistence details. */
public final class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> store = new ConcurrentHashMap<>();

    public Optional<User> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public User save(User user) { store.put(user.id(), user); return user; }
    public Collection<User> findAll() { return store.values(); }
}
