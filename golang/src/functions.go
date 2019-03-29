package main

import "fmt"

func main() {
	result1, _ := multipleReturnValues(12, 64)
	fmt.Println("the result is:", result1)
}

func functionNoParams() {
	fmt.Println("oh no")
}

func functionParamsDifferentType(param1 string, param2 int) {
	fmt.Println("param1", param1, "param2", param2)
}

func functionWithParamsOfSameType(price, number int) int {
	return price * number
}

func multipleReturnValues(price, number int) (result1, result2 int) {
	result1 = price
	result2 = number
	return
}
