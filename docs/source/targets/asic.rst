ASIC
====

Prerequisites
-------------

More TBD...

Notes
-----

TBD...

Spatial Compile
---------------

.. highlight:: bash

Then, compile the Spatial app with the following steps::

    $ cd spatial-quickstart/ # Navigate to Spatial base directory
    $ bin/spatial <app name> --synth <other options>

The ``<app name>`` refers to the name of the ``object`` that extends ``SpatialApp``.
For the other options, see `here <../../compiler>`_



Backend Compile
---------------

To synthesize for the ASIC, run the following::

    $ cd gen/<app name> # Navigate to generated directory
    $ make asic 

More TBD...


Execute
-------

TBD...

