package main

import "fmt"

func main() {
	var boolean bool

	boolean = true

	var int1 int8  // -128 to 127
	var int2 int16 // -32768 to 32767
	var int3 int32 // -2147483648 to 2147483647
	var int4 int64 // -9223372036854775808 to 9223372036854775807
	var int5 int   // 32 or 64, depends on underlying platform

	int1 = 127 // error if wrongly assigned: constant 128 overflows int8
	int2 = 32767
	int3 = 2147483647
	int4 = 9223372036854775807
	int5 = 9223372036854775807

	fmt.Println("boolean", boolean)
	fmt.Println("int8", int1, "int16", int2, "int32", int3, "int64", int4, "int", int5)
}
