# Exponential Search

## Overview

This project searches for large exponentials that are uncommonly close to each other. An exponential is an integer that can be expressed as `a^b`, where `a` and `b` are integers greater than 2. (I believe there is an actual name for these numbers, but I could not find it, so I'm calling them exponentials.) As you can imagine, these numbers grow very quickly. They are often over 10,000 units apart. It is interesting, though, that even when they become astronomical, they occasionally are within even 100 of each other. This program searches for such instances.

## Algorithm

The project contains two files. The first is `Exponential.java`. This file defines the `Exponential` class, which stores numbers in exponential format. It internally uses a [`BigInteger`][bi] for the base, an `int` for the power, and another [`BigInteger`][bi] for the value. This allows it to scale virtually infinitely, the limit being 2^MAX_INT, or 2^((2^31)-1), which is unfathomably gigantic.

The algorithm is contained in `Main.java`. It's main goal is continually finding the next smallest exponential, starting with the smallest, 2^2. It does this by keeping a sorted list of all the possible candidates. Specifically, it uses a [`TreeMap`][tm], which implements a [red-black tree][rbt]. In each iteration of finding the next smallest one, it simply takes the first exponential off the front of the list. It then checks if the exponential was a perfect square, if its power was 2. If so, then it must add a new candidate to the list for the future: the next perfect square. This is simple to calculate, since the base and power are stored separately. We just increment the base by one and add it to the set. Whether it is or is not a perfect square, the power is incremented and added to the set of possible candidates for the future.

This is how an example run would look:
  
    Beginning set: [2^2=4]
    
    - Remove 2^2=4
    - Check for near misses from what was last remembered
    - 2^2=4 is a square: add 3^2=9
    - Increment power: add 2^3=8
    
    Resultant set: [2^3=8, 3^2=9]
    
    - Remove 2^3
    - Check for near misses
    - Increment power: add 2^4=16
    
    Resultant set: [3^2=9, 2^4=16]
    
    - Remove 3^2
    - Check for near misses
    - 3^2 is a square: add 4^2=16
    - Increment power: add 3^3=27
    
    Resultant set: [4^2=16, 2^4=16, 3^3=27]  (Note that both 2^4 and 4^2 equal 16. This could probably be optimized out, as 4^x is useless when we have 2^x.)
    
    - Remove 4^2
    - Check for near misses
    - 4^2 is a square: add 5^2=25
    - Increment power: add 4^3=64
    
    Resultant set: [2^4=16, 5^2=25, 3^3=27, 4^3=64]
    
    - Remove 2^4
    - Check for near misses
    - Increment power: add 2^5=32
    
    Resultant set: [5^2=25, 3^3=27, 2^5=32, 4^3=64]

## Performance

After trying several algorithms, this one has proven to be extremely fast. It searches past 1,200,000^2 within ten seconds on an average server. The issue was not running time but space. Soon after it hit 1,300,000^2 the heap ran out of memory. I do not know how the [`BigInteger`][bi] stores its data, but the value should not be large. It is more likely that the sheer amount of exponentials that must be stored is what is causing the problem. Since it has reached 1.3 million square, it has 1.3 million elements in the set. Each exponential object is 24 bytes, not including the storage of the [`BigInteger`][bi]s. So this alone is nearly 32MB, a significant amount. This also does not account for the overhead of the [`TreeMap`][tm]. A detailed analysis would be interesting to see.



[bi]: https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html
[tm]: https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html
[rbt]: https://en.wikipedia.org/wiki/Red%E2%80%93black_tree
