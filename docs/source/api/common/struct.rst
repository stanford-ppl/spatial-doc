.. _Struct:

Structs
========


Custom structs can be created in Spatial using the **@struct** macro. 

For example::

  @struct MyData(x: Int, y: Double)


creates a struct which contains an :doc:`Int <fixpt>` and a :doc:`Double <fltpt>`. An instance of this struct can be created using::

  val data = MyData(32, 45.0f)


Note that this instance is immutable - its fields cannot be updated. Mutable custom struct instances are currently not supported, but
may be added in the future.

The fields of a custom struct are accessed using::
  
  data.x

  data.y



Defining a custom struct also implicitly adds evidence of the :doc:`Bits <../typeclasses/bits>` and :doc:`Arith <../typeclasses/arith>` type classes. This allows syntax like::

  val rand2 = random[MyData]

  val rand1 = random[MyData](MyData(32, 2.0f))

  val a = rand2 + rand1
  val b = rand2 - rand1


