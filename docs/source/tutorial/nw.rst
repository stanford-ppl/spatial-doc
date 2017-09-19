4. Needleman-Wunsch (NW)
========================

Catalog of Features
-------------------

In this section, you will learn about the following components in Spatial:

 - FSM

 - Branching

 - FIFO 
 
 - File IO and text management



Application Overview
--------------------

The Needleman-Wunsch algorithm is an algorithm used in bioinformatics to align protein or nucleotide sequences. 
It builds a scoring matrix based on two strings, and then backtraces through the score matrix to determine the
alignment of minimum error.  For more information on the algorithm's history and implementations, visit
the Wikipedia page (https://en.wikipedia.org/wiki/Needleman%E2%80%93Wunsch_algorithm).  The image below (credit Wikipedia)
demonstrates a rough overview of how the algorithm works.

.. image:: nw.png


Data Setup and Validation
-------------------------

In this algorithm, we will assume that a domain-expert keeps data files of DNA sequences, ``seqA.txt`` and ``seqB.txt``.
We will create an app that will read whatever text is in these files, pass it to the FPGA, return with the best alignment
between the two, and print out the result back into files called ``alignedA.txt`` and ``alignedB.txt``.  In order to test
if our alignments are "correct," we will aim to have less than 5% of the entries be in error.::

		import spatial.dsl._
		import org.virtualized._

		object NW extends SpatialApp {

			@virtualize
			def main() {
				val seqA_text = loadCSV1D[MString]("/home/SaulNeedleman/seqA.txt", " ").apply(0) // Loads into array with 1 string
				val seqB_text = loadCSV1D[MString]("/home/SaulNeedleman/seqB.txt", " ").apply(0) // Loads into array with 1 string
				println("Aligning " + seqA_text + " and ")
				println("         " + seqB_text)

				val seqA_data = argon.lang.String.string2num(seqA_text) // Returns array of Int8
				val seqB_data = argon.lang.String.string2num(seqB_text) // Returns array of Int8

				val seq_length = seqA_data.length
				val LEN = ArgIn[Int]
				val LEN2x = ArgIn[Int]
				setArg(LEN, seq_length)
				setArg(LEN2x, seq_length*2)
				val seqA = DRAM[Int8](LEN)
				val seqB = DRAM[Int8](LEN)
				setMem(seqA, seqA_data)
				setMem(seqB, seqB_data)
				val seqA_aligned = DRAM[Int8](LEN)
				val seqB_aligned = DRAM[Int8](LEN2x)

				Accel{}

				val seqA_aligned_result = getMem(seqA_aligned)
				val seqB_aligned_result = getMem(seqB_aligned)

				val errors = seqA_aligned_result.zip(seqB_aligned_result){_!=_}.reduce{_+_}
				println("Found " + errors + " errors out of " + {seq_length*2} + " characters")
				val cksum = errors.to[Float] / (seq_length*2).to[Float] < 0.1.to[Float]
				println("Acceptable? " + cksum)

				val seqA_aligned_string = argon.lang.String.num2string(seqA_aligned_result)
				val seqB_aligned_string = argon.lang.String.num2string(seqB_aligned_result)
				writeCSV1D[MString](seqA_aligned_string, "/home/mattfel/alignedA.txt", ",")
				writeCSV1D[MString](seqB_aligned_string, "/home/mattfel/alignedB.txt", ",")
			}

		}
