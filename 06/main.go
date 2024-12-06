package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"

	"github.com/abeltay/advent-of-code-2024/utilities"
)

func parseInput(filename string) [][]byte {
	in := utilities.ParseFile(filename)

	var arr [][]byte
	for _, row := range in {
		arr = append(arr, []byte(row))
	}
	return arr
}

const (
	N = iota
	E
	S
	W
)

func step(x, y, direction int) (int, int) {
	switch direction {
	case N:
		y--
	case E:
		x++
	case S:
		y++
	default:
		x--
	}
	return x, y
}

func walk(floor [][]byte, x, y int) map[string]bool {
	visited := make(map[string]bool)
	var direction int
	for {
		newX, newY := step(x, y, direction)
		if newX < 0 || newY < 0 || newX >= len(floor[0]) || newY >= len(floor) {
			return visited
		}
		if floor[newY][newX] != '#' {
			s := fmt.Sprintf("%d,%d", newX, newY)
			visited[s] = true
			x, y = newX, newY
		} else {
			direction = turn(direction)
		}
	}
}

func part1(filename string) int {
	in := parseInput(filename)
	for y := 0; y < len(in); y++ {
		for x := 0; x < len(in[y]); x++ {
			if in[y][x] == '^' {
				visited := walk(in, x, y)
				return len(visited)
			}
		}
	}
	return 0
}

func turn(direction int) int {
	direction++
	if direction >= 4 {
		direction = 0
	}
	return direction
}

func walk2(floor [][]byte, x, y int, path map[string]bool) int {
	var ans int
	for k := range path {
		s := strings.Split(k, ",")
		blockX, err := strconv.Atoi(s[0])
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}
		blockY, err := strconv.Atoi(s[1])
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}
		if hasLoop(floor, x, y, blockX, blockY) {
			ans++
			continue
		}
	}
	return ans
}

func hasLoop(floor [][]byte, x, y, blockX, blockY int) bool {
	visited := make(map[string]bool)
	var direction int
	for {
		newX, newY := step(x, y, direction)
		if newX < 0 || newY < 0 || newX >= len(floor[0]) || newY >= len(floor) {
			return false
		}
		if floor[newY][newX] == '#' || (newX == blockX && newY == blockY) {
			direction = turn(direction)
		} else {
			key := fmt.Sprintf("%d,%d,%d", newX, newY, direction)
			if visited[key] {
				return true
			}
			visited[key] = true
			x, y = newX, newY
		}
	}
}

func part2(filename string) int {
	in := parseInput(filename)
	for y := 0; y < len(in); y++ {
		for x := 0; x < len(in[y]); x++ {
			if in[y][x] == '^' {
				visited := walk(in, x, y)
				s := fmt.Sprintf("%d,%d", x, y)
				delete(visited, s)
				return walk2(in, x, y, visited)
			}
		}
	}
	return 0
}
