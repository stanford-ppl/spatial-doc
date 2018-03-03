0. Introduction
===============

Prerequisites
-------------

Running the VCS backend requires a Synopsys VCS license and environment variables properly pointing to the VCS license 
and VCS binary. 

- `VCS <https://www.synopsys.com/verification/simulation/vcs.html>`_ ** NOTE: License is required **


Notes
-----

The VCS simulator is a powerful, cycle-accurate verilog simulator.  It is a good representative of what to expect
when you deploy your algorithm to an FPGA.  This backend is integrated with DRAMSim to somewhat simulate what would
happen with the real DRAM peripherals.


Spatial Compile
-----

To compile the Spatial app, do the following steps:

    cd spatial-lang/ # Navigate to Spatial base directory
    bin/spatial <app name> --synth # + :doc:`other options <../../compiler>`

The "<app name>" refers to the name of the ``object``.


Backend Compile
-----

In the generated directory, you will notice the following structure.  By default, this directory is ``spatial-lang/gen/<app name>``.  
Here is a rough breakdown of what the important files are:

+------------------------------+---------------------------------------------------------------------------------------------+
| chisel/RootController.scala  | Main trait where all of the controller and dataflow connections are made                    |
+------------------------------+---------------------------------------------------------------------------------------------+
| chisel/x###.scala            | Nested traits where more controller and dataflow connections are made                       |
+------------------------------+---------------------------------------------------------------------------------------------+
| chisel/IOModule.scala        | Interface between FPGA accelerator and CPU                                                  |
+------------------------------+---------------------------------------------------------------------------------------------+
| chisel/BufferControlCxns     | Connections for all N-buffered memories in the design                                       |
+------------------------------+---------------------------------------------------------------------------------------------+
| chisel/templates/\*.scala    | Files for all of the fundamental building blocks of a Spatial app, including Fringe         |
+------------------------------+---------------------------------------------------------------------------------------------+
| cpp/TopHost.scala            | Contains the Application method where all CPU code is generated                             |
+------------------------------+---------------------------------------------------------------------------------------------+
| controller_tree.html         | Helpful diagram for showing the hierarchy of control nodes in your app                      |
+------------------------------+---------------------------------------------------------------------------------------------+

To compile the VCS target and prepare for simulation, run the following::

    cd gen/<app name> # Navigate to generated directory
    
    make vcs # Alternatively, you can run make vcs-hw && make vcs-sw to build the components separately

This will first convert the Chisel to verilog, then compile that verilog for the simulation.  Finally, it enters
the ``cpp/`` directory and compiles the host code and puts a ``Top`` executable in the generated app directory that
binds the verilog to the host when executed.

If you want to turn on waveforms, you can either turn on .vcd dumping or .vpd dumping.  To do this, edit ``chisel/template-level/fringeVCS/Top-harness.sv``
at the appropriate lines::

	reg vpdon = 0;
	reg vcdon = 0;

 If you turn on either of these flags, the waveform dump will appear in the generated directory after you execute, as described in the following section.

Execute
-----

To execute the generated code, run:

    # Run simulation executable if one of the first two options were chosen
    bash run.sh "<arguments>"

NOTE: The "<arguments>" should be a space-separated list.  For example, an app that takes arguments 192 96 should be run with::

    bash run.sh 192 96

If you've forgotten what input arguments are required for a given app, you can run::

	bash run.sh -h
	
The simulator will print out a status note after every 10000 cycles.  By default, the VCS simulation will time out after 
10000000000 cycles.  If your app appears to hang in simulation, you can either put breakpoints in your Spatial app directly, or
edit the following line in ``cpp/fringeVCS/FringeContextVCS.h`` and recompile the software::

	uint64_t maxCycles = 10000000000;


