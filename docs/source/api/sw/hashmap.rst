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

.. _HashMap:

HashMap
=======

Class and companion object for an immutable map on the host CPU.


**Infix methods**

+---------------------+----------------------------------------------------------------------------------------------------------------------+
|      `class`          **HashMap**\[K, V\]                                                                                                  |
+=====================+======================================================================================================================+
| |               def   **size**: :doc:`Int <../common/fixpt>`                                                                               |
| |                       Returns the number of key-value pairs stored in this HashMap.                                                      |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **keys**: :doc:`array`\[K\]                                                                                          |
| |                       Returns an Array of all keys stored in this HashMap.                                                               |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **values**: :doc:`array`\[V\]                                                                         						   |
| |                       Returns an Array of all values stored in this HashMap.                                                             |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(key: K): V  																															                                 |
| |                       Returns the value associated with the given key.									    																						 |
| |												Throws an exception if the given key is not stored in this HashMap 																								 |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **contains**\(key: K): :doc:`Boolean <../common/  																															                             |
| |                       Returns the value associated with the given key.									    																						 |
| |												Throws an exception if the given key is not stored in this HashMap 																								 |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
