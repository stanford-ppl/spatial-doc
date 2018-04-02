Scala
=====

Prerequisites
-------------

Running the Scala backend requires... Scala to be installed on your machine. 

- `Scala SBT <http://www.scala-sbt.org>`_ 

You must have Scala installed to compile Spatial applications, so if you can compile the app successfully then you
already have what you need to run the Scala backend.

Notes
-----

The Scala backend is a useful tool for debugging at the algorithm level. It has a few advantages and disadvantages, however:


- Advantages
    
    * `println` statements in the `Accel` will actually print while the app simulates
    * Fastest way to go from source code to execution

- Disadvantages

    * Not cycle accurate.  The Scala backend will not expose bugs related to things like loop-carry dependencies in coarse-grain pipelines or unprotected parallelized writes to memories.
    * Cannot target any devices from generated code.
    * Execution can be slow

Spatial Compile
---------------

.. highlight:: bash

Then, compile the Spatial app with the following steps::

    $ cd spatial-lang/ # Navigate to Spatial base directory
    $ bin/spatial <app name> --sim <other options>

The ``<app name>`` refers to the name of the ``object`` that extends ``SpatialApp``.
For the other options, see `here <../../compiler>`_


Backend Compile
---------------

For the Scala backend, compiling the generated code is optional since the execution script will
call SBT, which will compile the code anyway. If you want to just compile the Scala, run the following::


    $ cd gen/<app name> # Navigate to generated directory
    $ make sim # If you chose the Scala backend
 

Execute
-------

To execute the generated code, run::


    $ # Run simulation executable if one of the first two options were chosen
    $ bash run.sh "<arguments>"


NOTE: The "<arguments>" should be a space-separated list, fully enclosed in quotes.  For example, an app that takes arguments 192 96 should be run with::


    $ bash run.sh "192 96"
