FAQ
=================================

- Why does my Scala result not match my RTL simulation result?

  - This is generally due to loop-carry dependency, pipelining, or parallelization issues.
    The best way to rule out these is to turn off all pipelining and parallelizations and check
    if the result is correct.  If you have tried this and the results still do not match,
    it is possible you have exposed a compiler bug and should post to the `Spatial users google group <https://groups.google.com/forum/#!forum/spatial-lang-users>`_.

- Why does my Scala result have a bunch of numbers with X's in them?
  
  - This means that this particular value was computed using uninitialized numbers at some point
    in its history

`
If you could not find the answer to your question here, head over to the `google group <https://groups.google.com/forum/#!forum/spatial-lang-users>`_.

More to be added...
