
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

.. _Boolean:

Boolean
=======

@alias Boolean
@alias Bit
@alias Bool

Boolean represents a staged single boolean value.

Note that this type shadows the unstaged Scala Boolean.
In the case where an unstaged Boolean type is required, use the full `scala.Boolean` name.

**Infix methods**

+---------------------+----------------------------------------------------------------------------------------------------------------------+
|      `class`          **Boolean**                                                                                                          |
+=====================+======================================================================================================================+
| |               def   **unary_!**\: :doc:`bit`                                                                                             |
| |                       Negates the given boolean expression.                                                                              |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **&&**\(y: :doc:`bit`): :doc:`bit`                                                                                   |
| |                       Boolean AND.                                                                                                       |
| |                       Compares two Booleans and returns `true` if both are `true`.                                                       |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **||**\(y: :doc:`bit`): :doc:`bit`                                                                                   |
| |                       Boolean OR.                                                                                                        |
| |                       Compares two Booleans and returns `true` if at least one is `true`.                                                |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **^**\(y: :doc:`bit`): :doc:`bit`                                                                                    |
| |                       Boolean exclusive-or (XOR).                                                                                        |
| |                       Compares two Booleans and returns `true` if exactly one is `true`.                                                 |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **!=**\(y: :doc:`bit`): :doc:`bit`                                                                                   |
| |                       Value inequality (equivalent to exclusive-or).                                                                     |
| |                       Compares two Booleans and returns `true` if exactly one is `true`.                                                 |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **==**\(y: :doc:`bit`): :doc:`bit`                                                                                   |
| |                       Value equality, equivalent to exclusive-nor (XNOR).                                                                |
| |                       Compares two Booleans and returns `true` if both are `true` or both are `false`                                    |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **toString**\: :doc:`../sw/string`                                                                                   |
| |                       Creates a printable String from this value                                                                         |
| |                                                                                                                                          |
| |                       \[**NOTE**\] This method is unsynthesizable, and can be used only on the CPU or in simulation.                     |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
