.. _Struct:

Structs
========

@alias Struct

Custom structs can be created in Spatial using the **@struct** macro. 

For example::

  @struct MyData(x: Int, y: Double)


creates a struct which contains an @Int and a @Double. An instance of this struct can be created using::

  val data = MyData(32, 45.0f)


Note that this instance is immutable - its fields cannot be updated. Mutable custom struct instances are currently not supported, but
may be added in the future.

The fields of a custom struct are accessed using::
  
  // Get the value of field x
  data.x

  // Get the value of field y
  data.y



Defining a custom struct also implicitly adds evidence of the @Bits and @Arith type classes. This allows syntax like::

  // Use Bits evidence to generate a random instance of MyData
  val rand2 = random[MyData]

  // Use Bits evidence to generate a random instance with defined max values
  val rand1 = random[MyData](MyData(32, 2.0f))

  // Use the Arith evidence to add two instances
  val a = rand2 + rand1
  val b = rand2 - rand1


