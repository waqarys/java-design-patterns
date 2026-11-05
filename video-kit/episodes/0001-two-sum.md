---
title: Two Sum
subtitle: One pass with a HashMap
series: LeetCode · NeetCode 150 · #0001
lower_name: Waqar Shaikh
lower_topic: Two Sum · Arrays & Hashing
next: Next up · 0036 · Valid Sudoku
repo: github.com/waqarys/AlgoExpert (leetcode branch)
thumb_big: TWO SUM
thumb_small: O(n²) → O(n)
thumb_tag: JAVA
---
# Hook
[face] LeetCode problem number one, and still the most common warm-up question in interviews. Let's solve it the way an interviewer wants to see it.

# Problem
[02] Given an array of numbers and a target, return the indices of the two numbers that add up to the target. There is exactly one answer, and you cannot use the same element twice. For example, two and seven add up to nine, so we return zero and one.

# Naive approach
[04] The brute force idea is to try every pair. For each i, check every j after it, and test whether the two values add up to the target. It works, but it is quadratic time. With a hundred thousand numbers, that is billions of checks. {key: Brute force: O(n²) time, O(1) space}

# Pattern
[03] Here is the key insight. For every number x, the partner we need is target minus x, its complement. If we remember every value we have already seen, along with its index, we can check for the complement in constant time.
[05] So the plan is: keep a hash map from value to index. For each number, first check if its complement is already in the map. If it is, we are done. Otherwise, store the number and move on. Checking before inserting guarantees we never pair an element with itself. {key: Check the complement first, then insert}

# Code walkthrough
[06] Let's trace three, two, four, with a target of six. At index zero we need three, the map is empty, so we store three. At index one we need four, not there, store two. At index two we need two, and it is in the map at index one. We return one and two.
[face-pip 07] And here is the Java solution. One loop, one hash map, and an early return. I have also kept the brute force version underneath, so you can compare them side by side in your interview prep.

# Complexity / Trade-offs
[08] Complexity: one pass over the array, so linear time. And linear extra space for the map. We trade memory for speed, which is almost always the right call here. {key: O(n) time · O(n) space}

# Recap
[09] Two pitfalls before you go. Return indices, not values, and do not sort the array, because sorting destroys the original indices. The full solution and tests are on GitHub. I am Waqar. Like and subscribe, and I will see you in the next problem.
