package main

import "github.com/abeltay/advent-of-code-2024/utilities"

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
	NE
	E
	SE
	S
	SW
	W
	NW
)

func traversal(dir, x, y, dist int) (int, int) {
	switch dir {
	case N:
		return x, y - dist
	case NE:
		return x + dist, y - dist
	case E:
		return x + dist, y
	case SE:
		return x + dist, y + dist
	case S:
		return x, y + dist
	case SW:
		return x - dist, y + dist
	case W:
		return x - dist, y
	default:
		return x - dist, y - dist
	}
}

func hasXmas(in [][]byte, dir, x, y int) bool {
	xmas := []byte("XMAS")
	for i := 0; i < len(xmas); i++ {
		newX, newY := traversal(dir, x, y, i)
		if newX < 0 || newY < 0 || newX >= len(in[0]) || newY >= len(in) {
			return false
		}
		if xmas[i] != in[newY][newX] {
			return false
		}
	}
	return true
}

func part1(filename string) int {
	in := parseInput(filename)
	var ans int
	for y, row := range in {
		for x := range row {
			for i := 0; i <= NW; i++ {
				if hasXmas(in, i, x, y) {
					ans++
				}
			}
		}
	}
	return ans
}

func checkXMas(in [][]byte, x, y, dir int) bool {
	switch dir {
	case 0:
		if in[y-1][x-1] == 'M' && in[y-1][x+1] == 'M' && in[y+1][x-1] == 'S' && in[y+1][x+1] == 'S' {
			return true
		}
	case 1:
		if in[y-1][x-1] == 'S' && in[y-1][x+1] == 'S' && in[y+1][x-1] == 'M' && in[y+1][x+1] == 'M' {
			return true
		}
	case 2:
		if in[y-1][x-1] == 'M' && in[y-1][x+1] == 'S' && in[y+1][x-1] == 'M' && in[y+1][x+1] == 'S' {
			return true
		}
	default:
		if in[y-1][x-1] == 'S' && in[y-1][x+1] == 'M' && in[y+1][x-1] == 'S' && in[y+1][x+1] == 'M' {
			return true
		}
	}
	return false
}

func part2(filename string) int {
	in := parseInput(filename)
	var ans int
	for y, row := range in {
		for x := range row {
			if in[y][x] != 'A' {
				continue
			}
			if y-1 < 0 || x-1 < 0 || y+1 >= len(in) || x+1 >= len(in[0]) {
				continue
			}
			for i := 0; i < 4; i++ {
				if checkXMas(in, x, y, i) {
					ans++
				}
			}
		}
	}
	return ans
}
