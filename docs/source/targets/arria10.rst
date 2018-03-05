Arria10 SoC
===========

Prerequisites
-------------

You will need an Arria10 board, and a valid license for the Quartus toolchain for this target.  

- `Arria10 <https://www.altera.com/products/soc/portfolio/arria-10-soc/overview.html>`_ 

More TBD...

Notes
-----

Specifications of the Arria10 can be `found here <https://www.altera.com/en_US/pdfs/literature/hb/arria-10/a10_datasheet.pdf>`_. 


Spatial Compile
---------------

When writing your app, you should make the following modifications to bring in board-specific
knowledge into the spatial compiler, such as DSE models::

    import spatial.targets._

    ...

    object <app name> extends SpatialApp {
      override val target = Arria10

      ...

    }

Then, compile the Spatial app with the following steps::

    $ cd spatial-lang/ # Navigate to Spatial base directory
    $ bin/spatial <app name> --synth # + other options

The ``<app name>`` refers to the name of the ``object`` that extends ``SpatialApp``.
For the other options, see `here <../../compiler>`_



Backend Compile
---------------

In the generated directory, you will notice the following structure.  By default, this directory is ``spatial-lang/gen/<app name>``.  
Here is a rough breakdown of what the important files are:

+---------------------------------------+-------------------------------------------------------------+
| chisel/template-level/fringeArria10/  | Contains scripts for stitching together all of the IPs      |
+---------------------------------------+-------------------------------------------------------------+
| cpp/fringeArria10/                    | Contains board specific C++ code                            |
+---------------------------------------+-------------------------------------------------------------+
| verilog-arria10/                      | Will appear after synthesis, contains log files and verilog |
+---------------------------------------+-------------------------------------------------------------+

To compile the VCS target and prepare for simulation, run the following::

    $ cd gen/<app name> # Navigate to generated directory
    $ make arria10 # Alternatively, you can run make arria10-hw && make arria10-sw

This will first convert the Chisel to verilog, then synthesize that verilog and
stitch it to the other IPs.  Finally, it enters the ``cpp/`` directory and compiles the host code
drops a ``<app name>.tar.gz`` in the generated app directory that
binds the verilog to the host when executed.


Execute
-------

To execute the generated code, scp the generated ``.tar.gz`` file to the Arria10, export APPNAME as appropriate, and run::

    $ mkdir $APPNAME
    $ tar -xvf $APPNAME.tar.gz -C $APPNAME
    $ cd $APPNAME
    $ # EXTRA STEPS TBD...
    $ sudo ./Top <arguments>

It is very important to use the directory structure above or else it may cause the board to hang.  
The "<arguments>" should be a space-separated list.  For example, an app that takes arguments 192 96 should be run with::

    $ sudo ./Top 192 96

If you've forgotten what input arguments are required for a given app, you can run::

    $ sudo ./Top -h
    
Sudo is required if you are not logged in as root because the executable will attempt to program the fpga, which requires root permissions


