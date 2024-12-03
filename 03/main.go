package main

import (
	"fmt"
	"os"
	"regexp"
	"strconv"
	"strings"

	"github.com/abeltay/advent-of-code-2024/utilities"
)

func parseInput(filename string) []string {
	in := utilities.ParseFile(filename)
	return in
}

func process(code string) int {
	re := regexp.MustCompile(`mul\((\d{1,3}),(\d{1,3})\)`)
	captured := re.FindAllSubmatch([]byte(code), -1)
	var ans int
	for _, v := range captured {
		i1, err := strconv.Atoi(string(v[1]))
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}
		i2, err := strconv.Atoi(string(v[2]))
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}
		ans += i1 * i2
	}
	return ans
}

func part1(filename string) int {
	in := parseInput(filename)
	var ans int
	for _, row := range in {
		ans += process(row)
	}
	return ans
}

func shortener(code string) string {
	i := strings.Index(code, "don't()")
	if i == -1 {
		return code
	}
	leftover := code[i+6:]
	i2 := strings.Index(leftover, "do()")
	if i2 == -1 {
		return code[:i]
	}
	return code[:i] + shortener(leftover[i2+3:])
}

func part2(filename string) int {
	in := parseInput(filename)
	var combined string
	for _, row := range in {
		combined += row
	}
	s := shortener(combined)
	return process(s)
}
