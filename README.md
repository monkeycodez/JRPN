JRPN
=============
JRPN is a scripting language implemented in java.
It is based off the concept of "Reverse Polish Notation" where
the values are put before the operand such as "1 1 +" instead 
of "1 + 1".

JRPN is currently in development.

A Quick overview:
----------------
* Atoms: The basic building blocks of JRPN are atoms: litterals 1, 2, "foo",
  etc. and identifiers foo, bar, etc.
* There are also references to variables ,foo which acts somewhat like the
  &varname operation in C.  
* Blocks, regions of code enclosed in {} are used simmilarly to their
  equivalents in other languages.
* The Stack-centric language:  JRPN is based upon the stack concept:
	various operations push and pop values from the stack

	1 1 + println

becomes:
	PUSH 1
	PUSH 1
	CALL +
		POP + POP
		PUSH result
	CALL println

