
.. role:: black
.. role:: gray
.. role:: silver
.. role:: white
.. role:: maroon
.. role:: red
.. role:: fuchsia
.. role:: pink
.. role:: orange
.. role:: yellow
.. role:: lime
.. role:: green
.. role:: olive
.. role:: teal
.. role:: cyan
.. role:: aqua
.. role:: blue
.. role:: navy
.. role:: purple

.. _FileIO:

File I/O
========

@alias FileIO

File I/O operations are available for use on the host PU and during simulation.

------------------

**Methods**

@table-start
NoHeading

  /** 
    * Loads the CSV at `filename` as an @Array using the supplied `delimiter` for parsing. 
    * The delimiter defaults to a comma if none is supplied.
    **/
  @api def loadCSV1D[T:Type](filename: MString, delimiter: MString)(implicit cast: Cast[MString,T]): MArray[T]
 
  /** Loads the CSV at `filename` as a @Matrix, using the supplied element delimiter and linebreaks across rows. **/
  @api def loadCSV2D[T:Type](filename: MString, delimiter: MString)(implicit cast: Cast[MString,T]): Matrix[T] = {
  
  /** 
    * Writes the given Array to the file at `filename` using the given `delimiter`. 
    * If no delimiter is given, defaults to comma.
    **/
  @api def writeCSV1D[T:Type](array: MArray[T], filename: MString, delimiter: MString): MUnit = {
  
  /** 
    * Writes the given Matrix to the file at `filename` using the given element `delimiter`.
    * If no element delimiter is given, defaults to comma.
    **/
  @api def writeCSV2D[T:Type](matrix: Matrix[T], filename: MString, delimiter: MString): MUnit = {
  
@table-end

------------------

**Under development**

The following methods are not yet fully supported. If you need support for these methods,
please contact the Spatial group. 

@table-start
NoHeading

  /** Loads the given binary file at `filename` as an @Array. **/  
  @api def loadBinary[T:Type:Num](filename: MString): MArray[T]
  
  /** Saves the given Array to disk as a binary file at `filename`. **/
  @api def writeBinary[T:Type:Num](array: MArray[T], filename: MString): MUnit

@table-end
