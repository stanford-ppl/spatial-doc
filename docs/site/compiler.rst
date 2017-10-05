The Spatial Compiler
=====================

@alias Compiler

Compilation
------------

.. highlight:: bash

The Spatial Compiler is run using::

  bin/spatial <Application Name> [flags]

where *<Application Name>* is the name of the Spatial application object. 



Compiler Flags
---------------

The following compiler flags are available in the Spatial compiler:

+----------------+-----------------------------------------------------------------------------------------------+
| `--sim`        | Turns on the Scala backend for functional simulation.                                         |
+----------------+-----------------------------------------------------------------------------------------------+
| `--synth`      | Turns on the Chisel RTL backend for cycle-accurate simulation and/or synthesis.               |
+----------------+-----------------------------------------------------------------------------------------------+
| `--instrument` | Enables RTL instrumentation hooks for manual pipeline balancing analyses                      |
+----------------+-----------------------------------------------------------------------------------------------+
| `--retime`     | Enables RTL retiming to meet higher clock speeds.                                             |
+----------------+-----------------------------------------------------------------------------------------------+
|                | This flag will eventually be enabled by default.                                              |
+----------------+-----------------------------------------------------------------------------------------------+
| `--syncMem`    | Enables synchronous memory operations for all SRAMs to use fewer resources.                   |
|                |                                                                                               |
|                | Also enables `--retime`.                                                                      |
+----------------+-----------------------------------------------------------------------------------------------+
| `--out <dir>`  | Specifies an output directory to place generated code.                                        |
|                |                                                                                               |
|                | Default directory is `gen/<app name>`.                                                        |
+----------------+-----------------------------------------------------------------------------------------------+



Advanced Compiler Flags
-------------------------

These flags are primarily for use in development of the Spatial compiler.

+----------------+---------------------------------------------------------------------------------------------+
| `--cheapFifos` | Uses "cheap" FIFOs if there are no FIFOs with lane-dependent enqueues or dequeues.          | 
+----------------+---------------------------------------------------------------------------------------------+
| `--multifile`  | 0-6, default = 4                                                                            |
+----------------+---------------------------------------------------------------------------------------------+
| `--naming`     | Turns on useful naming for assistance in debugging generated chisel                         |
+----------------+---------------------------------------------------------------------------------------------+
| `--dse`        | Run compiler design space exploration.                                                      |
|                |                                                                                             |
|                | Interactivity is planned, but currently just generates a data file.                         |
+----------------+---------------------------------------------------------------------------------------------+

More to be added...