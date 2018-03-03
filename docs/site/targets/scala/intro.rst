0. Introduction
===============

Prerequisites
-------------

Running on EC2 FPGAs requires the following prerequisites:

- An installation of Spatial as described in the :doc:`previous tutorial <../../tutorial/starting>`
- Vivado and a license to run on the desired Amazon FPGA. We tested this tutorial both on Amazon's `FPGA Developer AMI <https://aws.amazon.com/marketplace/pp/B06VVYBLZZ#>`_ which contains all the required software tools,
  as well as locally by following `these instructions <https://github.com/aws/aws-fpga/blob/master/hdk/docs/on_premise_licensing_help.md>`_.

Spatial Compile
-----

.. highlight:: bash

Clone Amazon's `EC2 FPGA Hardware and Software Development Kit <https://github.com/aws/aws-fpga/>`_ to any location::

    git clone https://github.com/aws/aws-fpga.git

Spatial was most recently tested with version 1.3.3 of this repository (git commit 934000f9a57c0cde8786441864d5c6e0cf42fef9).

Set the ``AWS_HOME`` environment variable to point to the cloned directory.
Also source the AWS setup scripts. The HDK script is needed for simulation and synthesis, and the SDK is needed to create the host binary::

    export AWS_HOME=/path/to/aws-fpga
    cd /path/to/aws-fpga/
    source /path/to/aws-fpga/hdk_setup.sh
    source /path/to/aws-fpga/sdk_setup.sh

For example, you can add the 4 commands above to your ``.bashrc`` and source that.

Finally, applications targeting the F1 board (in hardware or simulation) need to set the ``target`` variable. For example,
make the following change in the very top of the ``apps/src/MatMult_outer.scala`` application::

    object MatMult_outer extends SpatialApp {
      override val target = spatial.targets.AWS_F1  // <---- new line
      ...

The next tutorial sections describe how to generate and run applications for :doc:`simulation <sim>` and :doc:`for the FPGAs on the F1 instances<F1>`.

Backend Compile


Execute