
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

.. _Math:

Math
====

Commonly used mathematical operators

**Methods**

@table-start
NoHeading


  /** Returns the absolute value of the supplied numeric `value`. **/
  @api def abs[T:Type:Num](value: T): T

  /** Returns the smallest (closest to negative infinity) integer value greater than or equal to `x`. **/
  @api def ceil[S:BOOL,I:INT,F:INT](x: FixPt[S,I,F]): FixPt[S,I,F] = FixPt[S,I,F](fix_ceil(x.s))

  /** Returns the natural exponentiation of `x` (e raised to the exponent `x`). **/
  @api def exp[T:Type:Num](x: T)(implicit ctx: SrcCtx): T

  /** Returns the largest (closest to positive infinity) integer value less than or equal to `x`. **/
  @api def floor[S:BOOL,I:INT,F:INT](x: FixPt[S,I,F]): FixPt[S,I,F] = FixPt[S,I,F](fix_floor(x.s))

  /** Returns the natural logarithm of `x`. **/
  @api def log[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = FltPt(flt_log(x.s))

  /** Creates a multiplexer that returns `a` when `select` is true, `b` otherwise. **/
  @api def mux[T:Type:Bits](select: Bit, a: T, b: T): T = wrap( math_mux(select.s, a.s, b.s) )
  /** Returns the minimum of the numeric values `a` and `b`. **/
  @api def min[T:Type:Bits:Order](a: T, b: T): T = wrap( math_min(a.s, b.s) )
  /** Returns the maximum of the numeric values `a` and `b`. **/
  @api def max[T:Type:Bits:Order](a: T, b: T): T = wrap( math_max(a.s, b.s) )

  /** Returns `base` raised to the power of `exp`. **/
  @api def pow[G:INT,E:INT](base: FltPt[G,E], exp:FltPt[G,E]): FltPt[G,E] = wrap( math_pow(base.s, exp.s) )

  /** Returns the square root of `x`. **/
  @api def sqrt[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap(Math.flt_sqrt(x.s))

  /** Returns the trigonometric sine of `x`. **/
  @api def sin[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_sin(x.s) )
  /** Returns the trigonometric cosine of `x`. **/
  @api def cos[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_cos(x.s) )
  /** Returns the trigonometric tangent of `x`. **/
  @api def tan[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_tan(x.s) )
  /** Returns the hyperbolic sine of `x`. **/
  @api def sinh[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_sinh(x.s) )
  /** Returns the hyperbolic cosine of `x`. **/
  @api def cosh[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_cosh(x.s) )
  /** Returns the hyperbolic tangent of `x`. **/
  @api def tanh[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_tanh(x.s) )
  /** Returns the arc sine of `x`. **/
  @api def asin[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_asin(x.s) )
  /** Returns the arc cosine of `x`. **/
  @api def acos[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_acos(x.s) )
  /** Returns the arc tangent of `x`. **/
  @api def atan[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E] = wrap( math_atan(x.s) )


@table-end


**Approximate Methods**

Some of the trigonometric functions are not yet supported in the Chisel backend yet.
As a placeholder, or to minimize future hardware costs, the following Taylor series approximations are predefined:

@table-start
NoHeading

  /** Taylor series expansion for trigonometric sine from -pi to pi **/
  @api def sin_taylor[S:BOOL,I:INT,F:INT](x: FixPt[S,I,F]): FixPt[S,I,F]
  /** Taylor series expansion for trigonometric cosine from -pi to pi **/
  @api def cos_taylor[S:BOOL,I:INT,F:INT](x: FixPt[S,I,F]): FixPt[S,I,F]
  /** Taylor series expansion for natural exponential**/
  @api def exp_taylor[S:BOOL,I:INT,F:INT](x: FixPt[S,I,F]): FixPt[S,I,F]
  /** Taylor series expansion for natural exponential**/
  @api def exp_taylor[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E]
  /** Taylor series expansion for natural log to third degree. **/
  @api def log_taylor[S:BOOL,I:INT,F:INT](x: FixPt[S,I,F]): FixPt[S,I,F]
  /** Taylor series expansion for natural log to third degree. **/
  @api def log_taylor[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E]
  /** Taylor series expansion for square root to third degree. **/
  @api def sqrt_approx[S:BOOL,I:INT,F:INT](x: FixPt[S,I,F]): FixPt[S,I,F]
  /** Taylor series expansion for square root to third degree. **/
  @api def sqrt_approx[G:INT,E:INT](x: FltPt[G,E]): FltPt[G,E]

@table-end


