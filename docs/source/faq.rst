FAQ
=================================


Simulation
-----------


**Why does my Scala result not match my RTL simulation result?**

   This is generally due to loop-carry dependency, pipelining, or parallelization issues.
   The best way to rule out these is to turn off all pipelining and parallelizations and check
   if the result is correct.  If you have tried this and the results still do not match,
   it is possible you have exposed a compiler bug and should post to the `Spatial users google group <https://groups.google.com/forum/#!forum/spatial-lang-users>`_.

**Why does my Scala result have a bunch of numbers with X's in them?**
  
   This means that this particular value was computed using uninitialized numbers at some point in its history.


Language
----------

**Why does Spatial use type classes rather than inheritance for things like Bits and Num?**

   While inheritance and type classes have many of the same properties, type classes are somewhat more flexible in that they allow simple conditional 
   rules. Spatial views the idea that a type can be represented by a fixed set of Bits as a conditional property, rather than a superclass.
   Spatial uses type classes because this conditional attribution is not possible with normal inheritance.

   For example, with the Tuple2[A,B] type, Spatial can conditionally determine if an instance of this type is representable by a fixed set of Bits based on the
   types A and B. If A and B both have the Bits property, then that particular instance will as well. 

   The downside of this is that type classes are based on type information only, not on the instance. This means, for example, that the VectorN type does not 
   implicitly have evidence of being representable by Bits, even though every instance of that class will include a fixed width. 



**Why does Spatial include the VectorN type?**

   Unlike C++, Scala does not support template classes which are parameterized by integer values. This means that, for methods where the number of elements
   in the resulting Vector depends on an argument to that method, the resulting type of the method cannot be written statically in Scala. However, this 
   width is still statically analyzable. As a workaround, Spatial uses the VectorN type to denote a Vector which needs static analysis of its width. 



If you could not find the answer to your question here, head over to the `google group <https://groups.google.com/forum/#!forum/spatial-lang-users>`_.

More to be added...
