
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

Compiling
---------

We will use the above template to learn the process for compiling, simulating, and synthesizing a design.  
While this template is empty, you can use this same flow freely as you build your applications in the tutorials below.

.. highlight:: bash

Currently, you should edit and place apps inside of your `spatial-lang/apps/src/` directory.  Copy-paste the above
template into a new file in this directory and you are ready to compile.

There are currently two backend targets that you can compile to: Scala and RTL.  

The Scala backend is the fastest way to check the correctness of your application.  
It does not simulate parallelization and pipelining as they would
execute in hardware so any race conditions or loop-carry dependency issues will not be caught in this backend, but
it is a very useful tool for early and rapid development. 

The RTL backend can be both simulated on a cycle-accurate simulator and synthesized to generate a bitstream
for an FPGA.  The RTL simulation is the most accurate way to test your design, but takes on the order of minutes
to compile.

**Compiling to Scala**

Targetting Scala is the quickest way to simulate your app and test for basic functional correctness.
You should use this backend if you are debugging things at the algorithm level.
In order to compile and simulate for the Scala backend, run::

    cd spatial-lang/ # Navigate to Spatial base directory
    bin/spatial <app name> --sim # + :doc:`other options <compileflags>`

The "<app name>" refers to the name of the ``object``. In our app above, for example, the app name is "HelloSpatial".
See the "Running" section below for a guide on how to test the generated app



**Compiling to RTL**

Targeting Chisel will let you compile your app down into Berkeley's Chisel language, which eventually compiles down to Verilog.
It also allows you to debug your app at the clock-cycle resolution. In order to compile with the Chisel backend, run the following::

    cd ${SPATIAL_HOME}
    bin/spatial <app name> --synth # + :doc:`other options <compileflags>`



Synthesizing and Testing
------------------------

After you have used the ``bin/spatial`` script to compile the app, navigate to the generated code
directory to test the app.  By default, this is ``spatial-lang/gen/<app name>``.  You will see some
files and directories in this folder that correspond to the code that Spatial created for the various
target platforms.
For the RTL backend, here is a rough breakdown of what the important files are:

+------------------------------+---------------------------------------------------------------------------+
| chisel/RootController.scala  | Main trait where all of the controller and dataflow connections are made  |
+------------------------------+---------------------------------------------------------------------------+
| chisel/x###.scala            | Nested traits where more controller and dataflow connections are made     |
+------------------------------+---------------------------------------------------------------------------+
| chisel/IOModule.scala        | Interface between FPGA accelerator and CPU                                |
+------------------------------+---------------------------------------------------------------------------+
| chisel/BufferControlCxns     | Connections for all N-buffered memories in the design                     |
+------------------------------+---------------------------------------------------------------------------+
| chisel/resources/\*.scala    | Files for all of the fundamental building blocks of a Spatial app         |
+------------------------------+---------------------------------------------------------------------------+
| cpp/TopHost.scala            | Contains the Application method where all CPU code is generated           |
+------------------------------+---------------------------------------------------------------------------+
| controller_tree.html         | Helpful diagram for showing the hierarchy of control nodes in your app    |
+------------------------------+---------------------------------------------------------------------------+
   

In order to finally test this code, you must compile the backend code itself. In order to do so, run the following::

    cd gen/<app name>
    
    # Choose ONE of the following
    make sim # If you chose the Scala backend
    make vcs # Cycle-accurate RTL simulation
    make aws-F1 # Synthesize for Amazon F1
    make zynq # Synthesize for Xilinx Zynq ZC706 or ZC702
    make de1soc # Synthesize for Altera DE1SoC

    # Run simulation executable if one of the first two options were chosen
    bash run.sh "<arguments>"

NOTE: The "<arguments>" should be a space-separated list, fully enclosed in quotes.  For example, an app that takes arguments 192 96 should be run with::

    bash run.sh "192 96"

After running an RTL simulation, you can see the waveforms generated in the ``test_run_dir/app.Launcher####`` folder, with the `.vcd` extension for further debugging

** Synthesized bitstream process TBA **


DRAM Transfers
--------------

We will now continue developing a Spatial app based on the above skeleton.
Please see the end of this section for a complete, copy-paste version of the code outlined below.

We will now add the code that will allow us to **1)** create data inside the host, **2)** transfer
this data to DRAM where it can be acessed by the FPGA, **3)** load the data, **4)** interact with the data
in on-chip SRAM, and **5)** store the data back to DRAM where it can be accessed by the host.

First, let's create a few data structures inside `main`, above the `Accel` block::
    
        val data1D        = Array.tabulate(64){i => i * 3} // Create 1D array with 64 elements, each element being index * 3
        val data1D_longer = Array.tabulate(1024){i => i} // Create 1D array with 1024 elements
        val data2D        = (0::64, 0::64){(i,j) => i*100 + j} // Create 64x64 2D, where each element is row * 100 + col
        val data5D        = (0::2, 0::2, 0::2, 0::2, 0::16){(i,j,k,l,m) => random[Int](5)} // Create 5D tensor, the highest dimension tensor currently supported in Spatial, with each element a random Int between 0 and 5

Now, let's allocate space in DRAM to memcpy this data to, so that the FPGA can read it later.  This code also lives above the `Accel` block::

        val dram1D        = DRAM[Int](64)
        val dram1D_longer = DRAM[Int](1024)
        val dram2D        = DRAM[Int](64,64)
        val dram5D        = DRAM[Int](2,2,2,2,16)

Next, we can transfer our generated data into these DRAM allocations, still above the `Accel` block::

        setMem(dram1D, data1D)
        setMem(dram1D_longer, data1D_longer)
        setMem(dram2D, data2D)
        setMem(dram5D, data5D)

We can also create a few DRAMs that will be written to by the Accel::
        
        val dram_result2D = DRAM[Int](32,32)
        val dram_scatter1D = DRAM[Int](1024)

Now, we will move into the `Accel` block to create some SRAMs to catch and hold data on-chip::

        val sram1D        = SRAM[Int](64)
        val sram2D        = SRAM[Int](32,32)
        val sram5D        = SRAM[Int](2,2,2,2,16)

With these SRAMs declared, we can load data into them.  DRAM is burst-addressable, relatively slow memory.  The Fringe module
manages the command and data streams that connect the FPGA to DRAM::

        sram1D load dram1D // Load data from a DRAM of matching dimension
        sram2D load dram2D(32::64, 0::32 par 16) // Load region from DRAM. In this case, we load the bottom-left quadrant of data from dram2D
        sram5D load dram5D // Load 5D tensor

In the above snippet, notice that you can parallelize these operations.  Parallelization of the leading dimension of the load
into the 2D SRAM means that rather than funnelling a 512-bit burst (consisting of 16 ints that are 32 bits each) into 1 element
at a time, we can store 16 elements at a time (the entire burst) into SRAM at once with each incoming burst.

Storing data from SRAM back into DRAM is straightforward, and can also have parallelization::

        dram_result2D(0::32, 0::32 par 8) store sram2D

The Fringe module also makes it very straightfoward to do scatter and gather operations from DRAM.  Because DRAM is
burst-addressable, it can be very inefficient to interact with individual, non-consecutive addresses.  The scatter and 
gather templates instantiate the control logic, caches, and other support required to efficiently coalesce, manage, and 
interact with DRAM at the word level.

        val gathered_sram = SRAM[Int](64)  // Create SRAM to hold data
        gathered_sram gather dram1D_longer(sram1D par 1, 64)  // Use the first 64 elements in sram1D as the addresses in dram1D_longer to collect, and store them into gathered_sram

We can also scatter this data back into DRAM

        dram_scatter1D(sram1D par 1, 64) scatter gathered_sram // For the first 64 elements, place element i of gathered_sram into the address indicated by the i'th element of sram1D 

Now, let's move outside the Accel and load our data back into the host to check if it is correct::
    
        val result_scattered = getMem(dram_scatter1D)
        val result2D = getMatrix(dram_result2D) // Collect 2D dram as a "Matrix."  Likewise, 3, 4, and 5D regions use "getTensor3", "getTensor4", and "getTensor5"

Finally, let's check if the data is correct and print the results. Note that while print lines inside the host code
will print for both the Scala and RTL backends, print lines inside the Accel will only print in the Scala backend and will be
ignored in RTL, since there is no straightforward print for FPGAs::
    
        printMatrix(result2D, "Result 2D: ") // printTensor3, printTensor4, and printTensor5 also exist
        printArray(result_scattered, "Result Scattered: ")
        val gold_2D = (32::64, 0::32){(i,j) => i*100 + j} // Remember we took bottom-left corner
        val cksum_2D = gold_2D.zip(result2D){_==_}.reduce{_&&_} // Zip the gold with the result and check if they are all equal
        val cksum_scattered = Array.tabulate(64){i => result_scattered(3*i) == 3*i}.reduce{_&&_} // Check if every 3 entries is equal to the index
        println("2D pass? " + cksum_2D)
        println("scatter pass? " + cksum_scattered)

Congratulations!  You have completed the DRAM section of the tutorial.  Please reference the 'Compiling' and 'Synthesizing and Testing' sections
above for a refresher on how to test your app.

Below is a copy-pastable version of the code outlined above::

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
        val data1D        = Array.tabulate(64){i => i * 3} // Create 1D array with 64 elements, each element being index * 3
        val data1D_longer = Array.tabulate(1024){i => i} // Create 1D array with 1024 elements
        val data2D        = (0::64, 0::64){(i,j) => i*100 + j} // Create 64x64 2D, where each element is row * 100 + col
        val data5D        = (0::2, 0::2, 0::2, 0::2, 0::16){(i,j,k,l,m) => random[Int](5)} // Create 5D tensor, the highest dimension tensor currently supported in Spatial, with each element a random Int between 0 and 5

        val dram1D        = DRAM[Int](64)
        val dram1D_longer = DRAM[Int](1024)
        val dram2D        = DRAM[Int](64,64)
        val dram5D        = DRAM[Int](2,2,2,2,16)

        setMem(dram1D, data1D)
        setMem(dram1D_longer, data1D_longer)
        setMem(dram2D, data2D)
        setMem(dram5D, data5D)

        val dram_result2D = DRAM[Int](32,32)
        val dram_scatter1D = DRAM[Int](1024)

        // 5. Algorithm to be accelerated in hardware
        Accel {
          val sram1D        = SRAM[Int](64)
          val sram2D        = SRAM[Int](32,32)
          val sram5D        = SRAM[Int](2,2,2,2,16)

          sram1D load dram1D // Load data from a DRAM of matching dimension
          sram2D load dram2D(32::64, 0::32 par 16) // Load region from DRAM. In this case, we load the bottom-left quadrant of data from dram2D
          sram5D load dram5D // Load 5D tensor

          dram_result2D(0::32, 0::32 par 8) store sram2D

          val gathered_sram = SRAM[Int](64)  // Create SRAM to hold data
          gathered_sram gather dram1D_longer(sram1D par 1, 64)  // Use the first 64 elements in sram1D as the addresses in dram1D_longer to collect, and store them into gathered_sram

          dram_scatter1D(sram1D par 1, 64) scatter gathered_sram // For the first 64 elements, place element i of gathered_sram into the address indicated by the i'th element of sram1D 
        }

        // 6. More code to be deployed to the host device, generally
        // consisting of validation checks
        val result_scattered = getMem(dram_scatter1D)
        val result2D = getMatrix(dram_result2D) // Collect 2D dram as a "Matrix."  Likewise, 3, 4, and 5D regions use "getTensor3D", "getTensor4D", and "getTensor5D"

        printMatrix(result2D, "Result 2D: ")
        printArray(result_scattered, "Result Scattered: ")
        val gold_2D = (32::64, 0::32){(i,j) => i*100 + j} // Remember we took bottom-left corner
        val cksum_2D = gold_2D.zip(result2D){_==_}.reduce{_&&_} // Zip the gold with the result and check if they are all equal
        val cksum_scattered = Array.tabulate(64){i => result_scattered(3*i) == 3*i}.reduce{_&&_} // Check if every 3 entries is equal to the index
        println("2D pass? " + cksum_2D)
        println("scatter pass? " + cksum_scattered)
      }
    }



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





Next, :doc:`learn how to build more complicated Spatial programs <model>`.

