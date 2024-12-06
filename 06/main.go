package main

import (
	"fmt"

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

func key(rowLength, x, y int) int {
	return y*rowLength + x
}

func walk(floor [][]byte, x, y int) map[int]bool {
	visited := make(map[int]bool)
	var direction int
	rowLength := len(floor[0])
	for {
		newX, newY := step(x, y, direction)
		if newX < 0 || newY < 0 || newX >= len(floor[0]) || newY >= len(floor) {
			return visited
		}
		if floor[newY][newX] != '#' {
			visited[key(rowLength, newX, newY)] = true
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

func walk2(floor [][]byte, x, y int, path map[int]bool) int {
	var ans int
	rowLength := len(floor[0])
	for k := range path {
		blockX := k % rowLength
		blockY := k / rowLength
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
				rowLength := len(in[0])
				delete(visited, key(rowLength, x, y))
				return walk2(in, x, y, visited)
			}
		}
	}
	return 0
}
