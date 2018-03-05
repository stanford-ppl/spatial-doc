Plasticine
=====================

Prerequisites
-------------

More TBD...

Notes
-----

Plasticine is a Coarse Grain Reconfigurable Architecture (CGRA) developed as part of the DAWN collaboration at Stanford.

More TBD...


Spatial Compile
---------------

When writing your app, you should make the following modifications to bring in board-specific
knowledge into the spatial compiler, such as DSE models.

Then, compile the Spatial app with the following steps::

    $ cd spatial-lang/ # Navigate to Spatial base directory
    $ bin/spatial <app name> --pir # + other options

The ``<app name>`` refers to the name of the ``object`` that extends ``SpatialApp``.
For the other options, see `here <../../compiler>`_



Backend Compile
---------------

TBD...


Execute
-------

TBD...
