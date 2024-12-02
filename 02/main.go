package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"

	"github.com/abeltay/advent-of-code-2024/utilities"
)

func parseInput(filename string) [][]int {
	in := utilities.ParseFile(filename)

	var arr [][]int
	for _, row := range in {
		s := strings.Split(row, " ")
		var arr2 []int
		for _, v := range s {
			i, err := strconv.Atoi(v)
			if err != nil {
				fmt.Println(err)
				os.Exit(1)
			}
			arr2 = append(arr2, i)
		}
		arr = append(arr, arr2)
	}
	return arr
}

func part1(filename string) int {
	in := parseInput(filename)
	var ans int
	for _, row := range in {
		if isSafe(row, 1, 3) {
			ans++
		}
	}
	return ans
}

func isSafe(arr []int, allowMin, allowMax int) bool {
	var increasing bool
	if arr[0] <= arr[1]-allowMin {
		increasing = true
	}
	for i := 1; i < len(arr); i++ {
		var diff int
		if increasing {
			diff = arr[i] - arr[i-1]
		} else {
			diff = arr[i-1] - arr[i]
		}
		if diff < allowMin || diff > allowMax {
			return false
		}
	}
	return true
}

func part2(filename string) int {
	in := parseInput(filename)
	var ans int
	for _, row := range in {
		if isSafe(row, 1, 3) {
			ans++
		} else {
			for i := 1; i <= len(row); i++ {
				var n []int
				n = append(n, row[:i-1]...)
				n = append(n, row[i:]...)
				if isSafe(n, 1, 3) {
					ans++
					break
				}
			}
		}
	}
	return ans
}
