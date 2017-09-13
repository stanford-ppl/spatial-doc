
1. Hello, Spatial!
==================

Catalog of Features
-------------------

In this section, you will learn about the following components in Spatial:

- Application skeleton (import statements, application creation, accel scope, host scope)

- DRAM
 
- SRAM

- ArgIn
 
- ArgOut
 
- HostIO
  
- Reg
 
- Typing system

- Data transfer between host and accel (setArg, setMem, getArg, getMem, load, store, gather, scatter)
 
- Basic debugging hooks
 
- Compiling an app

Application Overview
--------------------

In this section, you will see how to put together the bare-minimum Spatial application.  While the
code does not do any "meaningful" work, it demonstrates the basic primitives that almost all applications 
have and is intended to be the "Hello, world!" program for hardware.  You will start by generating input and
output registers to get the accelerator and host to interact with each other, and then add tile transfers
between the off-chip DRAM and on-chip SRAM.  You will then learn what functions are provided to test
functionality and utilize the host.  Finally, you will learn the basic compilation flows for testing the
functionality of the algorithm, cycle-accurate simulation of the generated RTL, and bitstream generation to
deploy to a supported FPGA or architecture.  

Below is a visualization of what we will be doing in this tutorial.  We start with a host and an FPGA, both 
connected to DRAM.  We will then instantiate all of the different ways you can get the two processors to interact
with each other.  We will create an RTL that will sit inside the FPGA, as well as some C++ code that will sit inside
the host.  Spatial automatically instantiates a box called "Fringe," which is an FPGA-agnostic hardware design
that allows the RTL to interact with peripherals, DRAM, PCIe buses, and whatever else is available on a given
SoC or FPGA board.

.. image:: layout.gif


Application Template
---------------------

All Spatial programs have a few basic components. The following code example shows each of those components for
an application that is called `HelloSpatial`::

    // 1. Imports
    import spatial.dsl._
    import org.virtualized._

    // 2. The Scala object which can be compiled and staged
    object HelloSpatial extends SpatialApp {

      // 3. This method, main, is called on program startup.
      // Spatial apps are required to use the "@virtualize" macro
      @virtualize
      def main() {

        // 4. Code to be deployed to the host device, generally
        // consisting of setting up data and reading files

        // 5. Algorithm to be accelerated in hardware
        Accel {

        }

        // 6. More code to be deployed to the host device, generally
        // consisting of validation checks
      }
    }

DRAM Transfers
--------------

We will now add the code that will allow us to **1)** create data inside the host, **2)** transfer
this data to DRAM where it can be acessed by the FPGA, **3)** load the data, **4)** interact with the data
in on-chip SRAM, and **5)** store the data back to DRAM where it can be accessed by the host.

First, let's create a few data structures inside `main`, above the `Accel` block::
    
        val data1D        = Array.tabulate(64){i => i * 3} // Create 1D array with 64 elements, each element being index * 3
        val data1D_longer = Array.tabulate(1024){i => i} // Create 1D array with 1024 elements
        val data2D        = (0::64, 0::64){(i,j) => i*100 + j} // Create 64x64 2D, where each element is row * 100 + col
        val data5D        = (0::2, 0::2, 0::2, 0::2, 0::16){(i,j,k,l,m) => random[Int](5)} // Create 5D tensor, the highest dimension tensor currently supported in Spatial, with each element a random Int between 0 and 5

Now, let's allocate space in DRAM to memcpy this data to, so that the FPGA can read it later::

        val dram1D        = DRAM[Int](64)
        val dram1D_longer = DRAM[Int](1024)
        val dram2D        = DRAM[Int](64,64)
        val data5D        = DRAM[Int](2,2,2,2,16)

Next, we can transfer our generated data into these DRAM allocations::

        setMem(dram1D, data1D)
        setMem(dram1D_longer, data1D_longer)
        setMem(dram2D, data2D)
        setMem(data5D, data5D)

We can also create a few DRAMs that will be written to by the Accel::
        
        val dram_result2D = DRAM[Int](64,64)
        val dram_scatter1D = DRAM[Int](1024)

Now we can move into the Accel block to create some SRAMs to catch and hold data on-chip::
        
        
.. Because Spatial is a DSL for programming reconfigurable *hardware*, we will begin with the hardware equivalent of "Hello, World."
.. In this app, the hardware reads some numeric argument from an off-chip source and then echoes it back to an off-chip destination.

.. Spatial apps are always divided into two parts: the portion of code that runs on the host CPU and the portion of code that gets generated as an accelerator.
.. In this example, the entirety of the app exists inside of **(3)** ``main()``, and the subset of code inside of the scope prefixed with **(7)** ``Accel`` is the hardware part of the app.

.. In the ArgInOut app, we start with three declarations above the ``Accel`` scope:

.. **(4)** We first declare *N* to be one of the command-line input arguments at run-time by setting it equal to ``args(0)``.
.. We must also explicitly cast this :doc:`../cpu/string` argument to a Spatial type by appending ``.to[Int]``.

.. **(5)** We then, declare *x* to be an :doc:`ArgIn <../accel/memories/reg>` of type :doc:`Int <../common/fixpt>` and
.. *y* to be an :doc:`ArgOut <../accel/memories/reg>` of type :doc:`Int <../common/fixpt>`.

.. In addition to ArgIns and ArgOuts, Spatial offers :doc:`../accel/memories/dram`, which represents an off-chip memory that
.. both the host and the accelerator can read from and write to.


.. **(6)** Now that we have both a value that represents an ArgIn and another value which reads some value from the command-line at runtime,
.. we must connect the two with ``setArg(<HW val>, <SW val>)``.
.. Similarly, we can connect a DRAM to an array with ``setMem(<HW array>, <SW array>)``.

.. **(7)** Next, we specify the ``Accel`` block.
.. In this particular app, we simply want to add the number `4` to whatever input argument is read in.
.. To do this, we just use the Reg ``:=`` operation to write our ArgOut register with ``x + 4``.
.. In later sections, you will learn what other operations and building blocks Spatial exposes to the developer.


.. **(8)**  After the ``Accel`` block, we return to the host code section of an app that will interact with the result generated by the hardware.
.. Specifically, we start by assigning the ArgOut register to a software variable with ``getArg(<HW val>)``.
.. Similarly, we can assign a DRAM to a software array with ``getMem(<HW array>)``.

.. **(9)** Finally, we add any debug and validation code to check if the accelerator is performing as expected.
.. In this example, we compute the result we expect the hardware to give, and then :doc:`print <../cpu/debug>` both this number and the number we actually got.

----------------

Compiling
---------

.. highlight:: bash

Currently, you should edit and place apps inside of your `${SPATIAL_HOME}/apps/src/` directory.
**Any time you change an app, you must remake Spatial with:** ::

    cd ${SPATIAL_HOME} && make apps

Once you have a complete Spatial app, the next step is to compile and run it.
Currently, there are two available targets: Scala (for simple functional simulation) and Chisel (for FPGA).

**Compiling to Scala**

Targetting Scala is the quickest way to simulate your app and test for basic functional correctness.
It also allows ``println`` calls in code that exists inside the ``Accel`` block.
You should use this backend if you are debugging things at the algorithm level.
In order to compile and simulate for the Scala backend, run::

    cd ${SPATIAL_HOME}/
    bin/spatial <app name> --scala # + other options

The "<app name>" refers to the name of the ``object``. In our app above, for example, the app name is "ArgInOut".
See the "Testing" section below for a guide on how to test the generated app



**Compiling to Chisel**

Targeting Chisel will let you compile your app down into Berkeley's Chisel language, which eventually compiles down to Verilog.
It also allows you to debug your app at the clock-cycle resolution. In order to compile with the Chisel backend, run the following::

    cd ${SPATIAL_HOME}
    bin/spatial <app name> --chisel # + other options



Synthesizing and Testing
------------------------

After you have used the ``bin/spatial`` script to compile the app, navigate to the generated code
directory to test the app.  By default, this is ``${SPATIAL_HOME}/gen/<app name>``.  You will see some
files and directories in this folder that correspond to the code that Spatial created for the various
target platforms.
For the Chisel backend, here is a rough breakdown of what the important files are:

+---------------------------+---------------------------------------------------------------------------+
| chisel/TopTrait.scala     | Main trait where all of the controller and dataflow connections are made  |
+---------------------------+---------------------------------------------------------------------------+
| chisel/IOModule.scala     | Interface between FPGA accelerator and CPU                                |
+---------------------------+---------------------------------------------------------------------------+
| chisel/BufferControlCxns  | Connections for all N-buffered memories in the design                     |
+---------------------------+---------------------------------------------------------------------------+
| chisel/resources/\*.scala | Files for all of the fundamental building blocks of a Spatial app         |
+---------------------------+---------------------------------------------------------------------------+
| cpp/TopHost.scala         | Contains the Application method where all CPU code is generated           |
+---------------------------+---------------------------------------------------------------------------+
| controller_tree.html      | Helpful diagram for showing the hierarchy of control nodes in your app    |
+---------------------------+---------------------------------------------------------------------------+


In order to finally test this code, you must compile the backend code itself. In order to do so, run the following::

    cd ${SPATIAL_HOME}/gen/<app name>
    make sim
    bash run.sh <arguments>

If using the Chisel backend, this will turn any Chisel code into Verilog, which then gets turned into C++ through Verilator.
It also compiles the Spatial-generated C++.  Finally, the ``run.sh`` script executes the entire application with communication between the hardware and CPU and returns the result.
If using the Scala backend, this will just test the Scala code on your machine.

After running a Chisel app, you can see the waveforms generated in the ``test_run_dir/app.Launcher####`` folder, with the `.vcd` extension for further debugging

The "<arguments>" should be a space-separated list, fully enclosed in quotes.  For example, an app that takes arguments 192 96 should be run with::

	bash run.sh "192 96"



Now that you have built and tested your first app, there are a lot more things you can do in Spatial!
You may already have an algorithm in mind that you want to write, or you may want to keep exploring to get a sense of what the language can do.
Feel free to poke around the apps we have written in ``${SPATIAL_HOME}/apps/src`` for examples of apps.
You may also find it useful to copy/paste one of our existing apps and start tweaking it to get more interesting algorithms.
If you run into any questions or issues, you can always post on our [forum](https://groups.google.com/forum/#!forum/spatial-lang-users).

Note that since the language is still actively under development, if one of our apps does not work and you think it should,
you should check the regression test status at the top of this README for a quick reference whether or not the app you are playing with is expected to work at the moment.


Next, :doc:`learn how to build more complicated Spatial programs <model>`.

