On-Chip Memories
================

Spatial includes a variety of on-chip memory templates for use in accelerator design. 
Unless otherwise specified, the size of a specified on-chip memory allocation must always
be a statically calculable number in order to map it to hardware resources.

Additionally, on-chip allocations follow traditional software scoping rules. Allocations
done within the scope of @Controllers, for example, logically create new memories on each
loop iteration. Unless otherwise specified, the contents of on-chip memories are undefined
upon allocation in order to minimize unnecessary memory initialization. 

.. toctree::
   onchip/counter
   onchip/counterchain
   onchip/fifo
   onchip/filo
   onchip/linebuffer
   onchip/lut
   onchip/reg
   onchip/regfile
   onchip/sram
