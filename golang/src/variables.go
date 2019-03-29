package main

import "fmt"

func main() {
	var age int                       // variable declaration with type
	var footSize = 42                 // type inference
	var headSize, waistSize = 200, 70 // multiple variables of same type

	var ( // multiple variables of different type
		name     = "Mario"
		myAge    = 5
		lastName = "nope"
	)

	heartSize, country := 100, "Italia" // shorthand declaration

	fmt.Println("my age is:", age, "and my foot size:", footSize) // the space btw. variables is automatically given
	fmt.Println("headSize:", headSize, "waistSize:", waistSize)
	fmt.Println("name:", name, "myAge:", myAge, "lastname:", lastName)
	fmt.Println("heartSize:", heartSize, "country name:", country)
}
