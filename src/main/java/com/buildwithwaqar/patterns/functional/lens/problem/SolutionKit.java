package com.buildwithwaqar.patterns.functional.lens.problem;

import java.util.*;
import java.util.function.*;

record Address(String city, String zip){}
record Person(String name, Address address){}
record Lens<S,A>(Function<S,A> get, BiFunction<S,A,S> set){
  A getA(S s){ return get.apply(s); }
  S setA(S s, A a){ return set.apply(s,a); }
  <B> Lens<S,B> andThen(Lens<A,B> other){
    return new Lens<>(s -> other.get.apply(get.apply(s)), (s,b) -> set.apply(s, other.set.apply(get.apply(s), b)));
  }
}
final class PersonLenses {
  static final Lens<Person,Address> address = new Lens<>(Person::address, (p,a)-> new Person(p.name(), a));
  static final Lens<Address,String> city = new Lens<>(Address::city, (a,c)-> new Address(c, a.zip()));
  static final Lens<Person,String> personCity = address.andThen(city);
}
