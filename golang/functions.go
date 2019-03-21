package main

import "fmt"

func main() {
	result := functionWithParamsOfSameType(100, 5)
	fmt.Println("the result is:", result)
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

func multipleReturnValues(price, number int) (int, int) {
	return
}
