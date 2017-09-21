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

.. _Matrix:

Matrix
======


Class and companion object for dense matrices on the CPU. 

------------------

**Constructor**

The following syntax is available for constructing Matrix instances from indexed functions:: 

  (0::16, 0::32){(i,j) => func(i,j) }

This returns a Matrix with 16 rows and 32 columns, with elements defined by `func(i,j)`.
More general :doc:`Range <../common/range>` forms can also be used, including strided (e.g. 0::2::8) and offset (e.g. 32::64). 
The iterators `i` and `j` will iterate over all values in their respective ranges.

------------------

**Static methods**

+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **Matrix**                                                                                                                                                                                                                                             |
+==========+========================================================================================================================================================================================================================================================+
| |    def   **tabulate**\[T\::doc:`Type <../typeclasses/type>`\]\(rows\: :doc:`Index <../common/fixpt>`, cols\: :doc:`Index <../common/fixpt>`\)\(func\: \(:doc:`Index <../common/fixpt>`, :doc:`Index <../common/fixpt>`\) => T\)\: :doc:`Matrix <matrix>`\[T\]   |
| |            Returns an immutable Matrix with the given **rows** and **cols** and elements defined by **func**.                                                                                                                                                   |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **fill**\[T\::doc:`Type <../typeclasses/type>`\]\(rows\: :doc:`Index <../common/fixpt>`, cols\: :doc:`Index <../common/fixpt>`\)\(func\: => T\)\: :doc:`Matrix <matrix>`\[T\] = this.tabulate\(rows, cols\){\(_,_\)                                    |
| |            Returns an immutable Matrix with the given **rows** and **cols** and elements defined by **func**.                                                                                                                                                   |
| |            Note that while **func** does not depend on the index, it is still executed **rows*****cols** times.                                                                                                                                                 |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+



------------------

**Infix methods**

+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **Matrix**                                                                                                                                                                         |
+==========+====================================================================================================================================================================================+
| |    def   **rows**\: :doc:`Index <../common/fixpt>`                                                                                                                                          |
| |            Returns the number of rows in this Matrix.                                                                                                                                       |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **cols**\: :doc:`Index <../common/fixpt>`                                                                                                                                          |
| |            Returns the number of columns in this Matrix.                                                                                                                                    |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(i\: :doc:`Index <../common/fixpt>`, j\: :doc:`Index <../common/fixpt>`\)\: T                                                                                            |
| |            Returns the element at the given two-dimensional address (**i**, **j**).                                                                                                         |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **update**\(i\: :doc:`Index <../common/fixpt>`, j\: :doc:`Index <../common/fixpt>`, elem\: T\)\: :doc:`Unit <../common/unit>`                                                      |
| |            Updates the element at the given two dimensional address to **elem**.                                                                                                            |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **flatten**\: :doc:`Array <array>`\[T\]                                                                                                                                            |
| |            Returns a flattened, immutable :doc:`Array <array>` view of this Matrix's data.                                                                                                  |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **foreach**\(func\: T => :doc:`Unit <../common/unit>`\)\: :doc:`Unit <../common/unit>`                                                                                             |
| |            Applies the function **func** on each element in this Matrix.                                                                                                                    |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **map**\[R\::doc:`Type <../typeclasses/type>`\]\(func\: T => R\)\: :doc:`Matrix <matrix>`\[R\]                                                                                     |
| |            Returns a new Matrix created using the mapping **func** over each element in this Matrix.                                                                                        |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **zip**\[S\::doc:`Type <../typeclasses/type>`,R\::doc:`Type <../typeclasses/type>`\]\(that\: :doc:`Matrix <matrix>`\[S\]\)\(func\: \(T,S\) => R\)\: :doc:`Matrix <matrix>`\[R\]    |
| |            Returns a new Matrix created using the pairwise mapping **func** over each element in this Matrix                                                                                |
| |            and the corresponding element in **that**.                                                                                                                                       |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **reduce**\(rfunc\: \(T,T\) => T\)\: T                                                                                                                                             |
| |            Reduces the elements in this Matrix into a single element using associative function **rfunc**.                                                                                  |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **transpose**\(\)\: :doc:`Matrix <matrix>`\[T\] = \(0\:\:cols, 0\:\:rows\){\(j, i\)                                                                                                |
| |            Returns the transpose of this Matrix.                                                                                                                                            |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+


