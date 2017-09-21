
.. role:: black
.. role:: gray
.. role:: silver
.. role:: white
.. role:: maroon
.. role:: red
.. role:: fuchsia
.. role:: pink
.. role:: orange
.. role:: yellow
.. role:: lime
.. role:: green
.. role:: olive
.. role:: teal
.. role:: cyan
.. role:: aqua
.. role:: blue
.. role:: navy
.. role:: purple

.. _Array:

Array
=====


Class and companion object for managing one dimensional arrays on the CPU.

Note that this type shadows the unstaged Scala Array.
In the case where an unstaged Array type is required, use the full `scala.Array` name.


---------------------

**Constructor**

The following syntax is available for constructing Arrays from indexed functions:: 

  (0::32){i => func(i) }

This returns an Array of size 32 with elements defined by `func(i)`.
More general :doc:`Range <../common/range>` forms can also be used, including strided (e.g. 0::2::8) and offset (e.g. 32::64). 
The iterator `i` will iterate over all values in the supplied range.

---------------------

**Static methods**

+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **Array**                                                                                                                                                                 |
+==========+===========================================================================================================================================================================+
| |    def   **tabulate**\[T\::doc:`Type <../typeclasses/type>`\]\(size\: :doc:`Index <../common/fixpt>`\)\(func\: :doc:`Index <../common/fixpt>` => T\)\: :doc:`Array <array>`\[T\]   |
| |            Returns an immutable Array with the given **size** and elements defined by **func**.                                                                                    |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **                                                                                                                                                                        |
| |                                                                                                                                                                                    |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **fill**\[T\::doc:`Type <../typeclasses/type>`\]\(size\: :doc:`Index <../common/fixpt>`\)\(func\: => T\)\: :doc:`Array <array>`\[T\] = this.tabulate\(size\){ _           |
| |            Returns an immutable Array with the given **size** and elements defined by **func**.                                                                                    |
| |            Note that while **func** does not depend on the index, it is still executed **size** times.                                                                             |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **empty**\[T\::doc:`Type <../typeclasses/type>`\]\(size\: :doc:`Index <../common/fixpt>`\)\: :doc:`Array <array>`\[T\]                                                    |
| |            Returns an empty, mutable Array with the given **size**.                                                                                                                |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../typeclasses/type>`\]\(elements\: T\*\)\: :doc:`Array <array>`\[T\]                                                                           |
| |            Returns an immutable Array with the given elements.                                                                                                                     |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+



------------------


**Infix methods**

+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **Array**\[T\]                                                                                                                                                                                                                                     |
+==========+====================================================================================================================================================================================================================================================+
| |    def   **length**\: :doc:`Index <../common/fixpt>`                                                                                                                                                                                                        |
| |            Returns the size of this Array.                                                                                                                                                                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(i\: :doc:`Index <../common/fixpt>`\)\: T                                                                                                                                                                                                |
| |            Returns the element at index **i**.                                                                                                                                                                                                              |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **update**\[A\]\(i\: :doc:`Index <../common/fixpt>`, data\: A\)\(implicit lift\: Lift\[A,T\]\)\: :doc:`Unit <../common/unit>`                                                                                                                      |
| |            Updates the element at index **i** to data.                                                                                                                                                                                                      |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **foreach**\(func\: T => :doc:`Unit <../common/unit>`\)\: :doc:`Unit <../common/unit>`                                                                                                                                                             |
| |            Applies the function **func** on each element in the Array.                                                                                                                                                                                      |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **map**\[R\::doc:`Type <../typeclasses/type>`\]\(func\: T => R\)\: :doc:`Array <array>`\[R\]                                                                                                                                                       |
| |            Returns a new Array created using the mapping **func** over each element in this Array.                                                                                                                                                          |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **zip**\[S\::doc:`Type <../typeclasses/type>`,R\::doc:`Type <../typeclasses/type>`\]\(that\: :doc:`Array <array>`\[S\]\)\(func\: \(T,S\) => R\)\: :doc:`Array <array>`\[R\]                                                                        |
| |            Returns a new Array created using the pairwise mapping **func** over each element in this Array                                                                                                                                                  |
| |            and the corresponding element in **that**.                                                                                                                                                                                                       |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **reduce**\(rfunc\: \(T,T\) => T\)\: T                                                                                                                                                                                                             |
| |            Reduces the elements in this Array into a single element using associative function **rfunc**.                                                                                                                                                   |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **fold**\(init\: T\)\(rfunc\: \(T,T\) => T\)\: T                                                                                                                                                                                                   |
| |            Reduces the elements in this Array and the given initial value into a single element                                                                                                                                                             |
| |            using associative function **rfunc**.                                                                                                                                                                                                            |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **filter**\(cond\: T => MBoolean\)\: :doc:`Array <array>`\[T\]                                                                                                                                                                                     |
| |            Returns a new Array with all elements in this Array which satisfy the given predicate **cond**.                                                                                                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **flatMap**\[R\::doc:`Type <../typeclasses/type>`\]\(func\: T => :doc:`Array <array>`\[R\]\)\: :doc:`Array <array>`\[R\]                                                                                                                           |
| |            Returns a new Array created by concatenating the results of **func** applied to all elements in this Array.                                                                                                                                      |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **groupByReduce**\[K\::doc:`Type <../typeclasses/type>`,V\::doc:`Type <../typeclasses/type>`\]\(key\: A => K\)\(value\: A => V\)\(reduce\: \(V,V\) => V\)\: :doc:`HashMap <hashmap>`\[K,V\]                                                        |
| |            Partitions this Array using the **key** function, then maps each element using **value**, and                                                                                                                                                    |
| |            finally combines values in each bin using the associative **reduce** function.                                                                                                                                                                   |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **mkString**\(delimeter\: :doc:`String <string>`\)                                                                                                                                                                                                 |
| |            Creates a string representation of this Array using the given **delimeter**.                                                                                                                                                                     |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **mkString**\(start\: :doc:`String <string>`, delimeter\: :doc:`String <string>`, stop\: :doc:`String <string>`\)\: :doc:`String <string>`                                                                                                         |
| |            Creates a string representation of this Array using the given **delimeter**, bracketed by **start** and **stop**.                                                                                                                                |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **reshape**\(rows\: :doc:`Index <../common/fixpt>`, cols\: :doc:`Index <../common/fixpt>`\)\: :doc:`Matrix <matrix>`\[T\]                                                                                                                          |
| |            Returns an immutable view of the data in this Array as a :doc:`Matrix <matrix>` with given **rows** and **cols**.                                                                                                                                |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **reshape**\(dim0\: :doc:`Index <../common/fixpt>`, dim1\: :doc:`Index <../common/fixpt>`, dim2\: :doc:`Index <../common/fixpt>`\)\: :doc:`Tensor3 <tensor>`\[T\]                                                                                  |
| |            Returns an immutable view of the data in this Array as a :doc:`Tensor3 <tensor>` with given dimensions.                                                                                                                                          |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **reshape**\(dim0\: :doc:`Index <../common/fixpt>`, dim1\: :doc:`Index <../common/fixpt>`, dim2\: :doc:`Index <../common/fixpt>`, dim3\: :doc:`Index <../common/fixpt>`\)\: :doc:`Tensor4 <tensor>`\[T\]                                           |
| |            Returns an immutable view of the data in this Array as a :doc:`Tensor4 <tensor>` with given dimensions.                                                                                                                                          |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **reshape**\(dim0\: :doc:`Index <../common/fixpt>`, dim1\: :doc:`Index <../common/fixpt>`, dim2\: :doc:`Index <../common/fixpt>`, dim3\: :doc:`Index <../common/fixpt>`, dim4\: :doc:`Index <../common/fixpt>`\)\: :doc:`Tensor5 <tensor>`\[T\]    |
| |            Returns an immutable view of the data in this Array as a :doc:`Tensor5 <tensor>` with given dimensions.                                                                                                                                          |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **!=**\(that\: :doc:`Array <array>`\[T\]\)\: MBoolean = this.zip\(that\){\(x,y\) => x =!                                                                                                                                                           |
| |            Returns true if this Array and **that** differ by at least one element, false otherwise.                                                                                                                                                         |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **==**\(that\: :doc:`Array <array>`\[T\]\)\: MBoolean = this.zip\(that\){\(x,y\) => x ==                                                                                                                                                           |
| |            Returns true if this Array and **that** contain the same elements, false otherwise.                                                                                                                                                              |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

