package main

import (
	"fmt"
	"os"
	"sort"

	"github.com/abeltay/advent-of-code-2024/utilities"
)

func parseInput(filename string) ([]int, []int) {
	in := utilities.ParseFile(filename)

	var left, right []int
	for _, row := range in {
		var l, r int
		_, err := fmt.Sscanf(row, "%d %d", &l, &r)
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}
		left = append(left, l)
		right = append(right, r)
	}
	return left, right
}

func part1(filename string) int {
	left, right := parseInput(filename)
	sort.Ints(left)
	sort.Ints(right)
	var ans int
	for i := range left {
		diff := left[i] - right[i]
		if diff < 0 {
			diff *= -1
		}
		ans += diff
	}
	return ans
}

func part2(filename string) int {
	left, right := parseInput(filename)
	m := make(map[int]int)
	for _, v := range right {
		m[v] = m[v] + 1
	}
	var ans int
	for _, v := range left {
		ans += v * m[v]
	}
	return ans
}
