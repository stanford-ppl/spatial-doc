0. Getting Started
==================

Prerequisites
-------------

First, make sure to download and install the following prerequisites:

- `Scala SBT <http://www.scala-sbt.org>`_ 
- `Java JDK <http://www.oracle.com/technetwork/java/javase/downloads/index.html>`_

While it's not at all required, it may be easier to learn to use Spatial if you've had experience with Scala
or a similar functional programming language in the past.  Knowledge of Scala will allow you to use
meta-programming to assist your Spatial designs.

If you'd like, check out this `Scala tutorial <https://www.tutorialspoint.com/scala/>`_ .

Finally, please sign up for the `Spatial users google group <https://groups.google.com/forum/#!forum/spatial-lang-users>`_ if you have any questions. 



.. Installation (From Binary)
.. --------------------------

.. Run the following command to clone the quickstart repository::

..     git clone https://github.com/stanford-ppl/spatial-quickstart.git
    
.. To test to make sure it's working::

..     bin/spatial HelloSpatial
..     ./HelloSpatial.sim 32

.. That's it! You're ready to create and run Spatial programs!



Installation From Maven (Recommended)
--------------------------

.. highlight:: bash

To get started with Spatial, you do not need to clone or build any DSL software yourself. 
You simply need to create a new SBT project as follows::

	mkdir -p spatial-lang/src/main && cd spatial-lang

.. highlight:: scala

In ``spatial-lang/``, create a file called ``build.sbt``::

	organization := "org"

	version := "1.0-SNAPSHOT"

	name := "spatial-app"

	scalaVersion := "2.12.1"
	val paradiseVersion = "2.1.0"

	scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:reflectiveCalls")

	libraryDependencies ++= Seq("edu.stanford.cs.dawn" %% "spatial" % "0.1-SNAPSHOT")

	resolvers ++= Seq(
	  Resolver.sonatypeRepo("snapshots"),
	  Resolver.sonatypeRepo("releases"),
	  // "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository/",
	)
	addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)

	scalaSource in Compile := baseDirectory.value / "src" 

.. highlight:: bash

NOTE: You will temporarily need to do the following because of resource files that are hardcoded
into the compiler as of 3/7/2018 and this requirement will be removed shortly or a clean spatial-quickstart
will be provided for you in future versions::

	git clone git@github.com:stanford-ppl/spatial-lang
	export SPATIAL_HOME=`pwd`/spatial-lang

Finally, start writing your apps is ``spatial-lang/src/main/<filename>.scala``.  Refer to 
`targets <../targets.html>`_ to learn how to compile the app.  The command is::

	$ sbt "runMain <app name> <args>"



Installation From Source
--------------------------

.. highlight:: bash

Run the following (bash) commands to clone and update the spatial-lang repository::

    git clone https://github.com/stanford-ppl/spatial-lang.git
    cd spatial-lang
    git submodule update --init

This will pull Spatial's submodules `argon`, `apps`, and `scala-virtualized`.

You may need to export your JAVA_HOME environment variable to point to your Java installation (usually /usr/bin)

.. Running automated tests requires a few environment variables to be set.  If you are using the recommended
.. directory structure in this tutorial, then you can simply run the following command::

..     cd ${HOME}/spatial-lang
..     source ./init-env.sh

.. If you have some other structure, you need to set the following variables manually.
.. It may be easiest to set them in your terminal startup script (e.g. bashrc) so all future sessions have them::

..     export JAVA_HOME = ### Directory Java is installed, usually /usr/bin
..     export ARGON_HOME = ### Top directory of argon
..     export SPATIAL_HOME = ### Top directory of spatial-lang
..     export VIRTUALIZED_HOME = ### Top directory of scala-virtualized

You are now ready to compile the language.  Run the following::

    cd spatial-lang # Navigate to root of spatial-lang repository
    sbt compile

A good habit would be to pull from these repositories often and run ``sbt compile`` in your spatial-lang directory.


That's it! Up next, you will learn how to use the language by working through a series of examples.
The concepts you will learn in these tutorials are listed below.  Feel free to skip around the apps as
you find convenient:

- :doc:`Hello, World! <helloworld>`

 - Application skeleton (import statements, application creation, accel scope, host scope)

 - ArgIn
 
 - ArgOut
 
 - HostIO
 
 - DRAM
 
 - SRAM
 
 - Reg
 
 - Typing system

 - Data transfer between host and accel (setArg, setMem, getArg, getMem, load, store, gather, scatter)
 
 - Basic debugging hooks
 
 - Compiling an app


- :doc:`Dot Product <dotproduct>`

 - Tiling
 
 - Reduce and Fold

 - Sequential execution and Coarse-grain pipelining
 
 - Parallelization
 
 - Basic buffering and banking


- :doc:`General Matrix Multiply (GEMM) <gemm>`

 - MemReduce and MemFold

 - Debugging with instrumentation 
 
 - Advanced banking

 - Advanced buffering
 

- :doc:`Differentiator & Sobel Filter <convolution>`

 - LineBuffer 
 
 - ShiftRegister
 
 - LUT

 - Spatial Functions and Multifile Projects


- :doc:`Needleman-Wunsch <nw>`

 - FSM

 - Branching

 - FIFO 

 - Systolic Arrays
 
 - File IO and text management

 - Asserts, Breakpoints, and Sleep
 
