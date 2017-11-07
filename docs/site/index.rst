
Spatial
=======

For a brief introduction to Spatial and its purpose, see `this presentation <https://github.com/stanford-ppl/spatial-doc/tree/master/docs/site/slides/WhatIsSpatial.pdf>`_.

For a brief rundown of the language and its constructs, see `this presentation <https://github.com/stanford-ppl/spatial-doc/tree/master/docs/site/slides/Rundown.pdf>`_ and the :doc:`in-depth tutorials <tutorial/starting>` on this website.

.. image:: images/spatial_start.png


+------------------------------------------+------------------------------------+-----------------------------------------------------------+
| :doc:`Get Started <tutorial/starting>`   | :doc:`API Documentation <api>`     | `Source <https://github.com/stanford-ppl/spatial-lang>`_  |
+------------------------------------------+------------------------------------+-----------------------------------------------------------+

Spatial is a domain-specific language for describing hardware accelerators for use on FPGAs and other supported spatial architectures.
The language is intended to be both higher level than hardware description languages (HDLs) like Verilog, VHDL, and `Chisel <https://chisel.eecs.berkeley.edu/>`_,
while also being easier to use than Altera's OpenCL or high level synthesis (HLS) languages like Xilinx's Vivado.

Language features of Spatial include:
   * Tunable, hardware specific templates
   * User specified and implicitly created design parameters
   * Design runtime and area analysis
   * Automatic parameter tuning
   * Automatic memory banking and buffering


To run Spatial on the new Amazon EC2 FPGA instances, see :doc:`the AWS Tutorial <targets/aws/intro>`.

.. toctree::
   :maxdepth: 1

   api
   tutorial
   examples
   targets
   theory
   faq
   contact
