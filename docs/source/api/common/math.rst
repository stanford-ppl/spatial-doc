
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

.. _Math:

Math
====

Commonly used mathematical operators

**Methods**

+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **abs**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Num <../typeclasses/num>`\]\(value\: T\)\: T                                              |
| |             Returns the absolute value of the supplied numeric **value**.                                                                                |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **ceil**\[S\:BOOL,I\:INT,F\:INT\]\(x\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                         |
| |             Returns the smallest (closest to negative infinity) integer value greater than or equal to **x**.                                            |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **exp**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Num <../typeclasses/num>`\]\(x\: T\)\(implicit ctx\: SrcCtx\)\: T                         |
| |             Returns the natural exponentiation of **x** (e raised to the exponent **x**).                                                                |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **floor**\[S\:BOOL,I\:INT,F\:INT\]\(x\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                        |
| |             Returns the largest (closest to positive infinity) integer value less than or equal to **x**.                                                |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **log**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                      |
| |             Returns the natural logarithm of **x**.                                                                                                      |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **mux**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(select\: :doc:`Bit <bit>`, a\: T, b\: T\)\: T              |
| |             Creates a multiplexer that returns **a** when **select** is true, **b** otherwise.                                                           |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **min**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\::doc:`Order <../typeclasses/order>`\]\(a\: T, b\: T\)\: T    |
| |             Returns the minimum of the numeric values **a** and **b**.                                                                                   |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **max**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\::doc:`Order <../typeclasses/order>`\]\(a\: T, b\: T\)\: T    |
| |             Returns the maximum of the numeric values **a** and **b**.                                                                                   |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **pow**\[G\:INT,E\:INT\]\(base\: :doc:`FltPt <fltpt>`\[G,E\], exp\::doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                 |
| |             Returns **base** raised to the power of **exp**.                                                                                             |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **sqrt**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                     |
| |             Returns the square root of **x**.                                                                                                            |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **sin**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                      |
| |             Returns the trigonometric sine of **x**.                                                                                                     |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **cos**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                      |
| |             Returns the trigonometric cosine of **x**.                                                                                                   |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **tan**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                      |
| |             Returns the trigonometric tangent of **x**.                                                                                                  |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **sinh**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                     |
| |             Returns the hyperbolic sine of **x**.                                                                                                        |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **cosh**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                     |
| |             Returns the hyperbolic cosine of **x**.                                                                                                      |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **tanh**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                     |
| |             Returns the hyperbolic tangent of **x**.                                                                                                     |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **asin**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                     |
| |             Returns the arc sine of **x**.                                                                                                               |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **acos**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                     |
| |             Returns the arc cosine of **x**.                                                                                                             |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **atan**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                     |
| |             Returns the arc tangent of **x**.                                                                                                            |
+-----------+------------------------------------------------------------------------------------------------------------------------------------------------+



**Approximate Methods**

Some of the trigonometric functions are not yet supported in the Chisel backend yet.
As a placeholder, or to minimize future hardware costs, the following Taylor series approximations are predefined:

+-----------+-----------------------------------------------------------------------------------------------------------------+
| |     def   **sin_taylor**\[S\:BOOL,I\:INT,F\:INT\]\(x\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]    |
| |             Taylor series expansion for trigonometric sine from -pi to pi                                                 |
+-----------+-----------------------------------------------------------------------------------------------------------------+
| |     def   **cos_taylor**\[S\:BOOL,I\:INT,F\:INT\]\(x\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]    |
| |             Taylor series expansion for trigonometric cosine from -pi to pi                                               |
+-----------+-----------------------------------------------------------------------------------------------------------------+
| |     def   **exp_taylor**\[S\:BOOL,I\:INT,F\:INT\]\(x\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]    |
| |             Taylor series expansion for natural exponential                                                               |
+-----------+-----------------------------------------------------------------------------------------------------------------+
| |     def   **exp_taylor**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                |
| |             Taylor series expansion for natural exponential                                                               |
+-----------+-----------------------------------------------------------------------------------------------------------------+
| |     def   **log_taylor**\[S\:BOOL,I\:INT,F\:INT\]\(x\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]    |
| |             Taylor series expansion for natural log to third degree.                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------+
| |     def   **log_taylor**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                |
| |             Taylor series expansion for natural log to third degree.                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------+
| |     def   **sqrt_approx**\[S\:BOOL,I\:INT,F\:INT\]\(x\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]   |
| |             Taylor series expansion for square root to third degree.                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------+
| |     def   **sqrt_approx**\[G\:INT,E\:INT\]\(x\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]               |
| |             Taylor series expansion for square root to third degree.                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------+



