
6. Porting Guide
==================


Overview
-------------------

In this section, you will learn how to port apps written for Spatial 1.0 (github.com/stanford-ppl/spatial-lang) to Spatial 2.0 (github.com/stanford-ppl/spatial)

Steps
-----

1) Relocate apps
    Apps used to reside in `spatial-lang/apps/src/`.  They should now be placed in `spatial/test/spatial/tests/` for the bin/spatial script to find them.


2) Change imports:
    

    import spatial.dsl._
    import virtualized._



    import spatial.dsl._


3) Change macros and setup of app:


    object <appname> extends SpatialApp {
        @virtualize def main() {
            ...
        }
    }



    @spatial class <appname> extends SpatialTest {
        def main(args: Array[String]): Unit = {
            ...
        }
    }

4) Change typeargs to user-defined functions:


    def foo[T:Type:Num](in: T): Unit = {...}



    def foo[T:Num](in: T): Unit = {...}

5) Replace all instances of `[Index]` with `[I32]`

6) Replace all instances of `[MString]` with `[String]`

7) Convert chars to ints with `.to[I8]`.  Convert array of I8 to String with `charArrayToString(arr)`




