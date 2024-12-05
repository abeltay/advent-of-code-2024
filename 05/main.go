package main

import (
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"

	"github.com/abeltay/advent-of-code-2024/utilities"
)

type order struct {
	left  int
	right int
}

func parseInput(filename string) ([]order, [][]int) {
	in := utilities.ParseFile(filename)

	var row int
	var arr []order
	for ; row < len(in); row++ {
		if in[row] == "" {
			break
		}
		var o order
		_, err := fmt.Sscanf(in[row], "%d|%d", &o.left, &o.right)
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}
		arr = append(arr, o)
	}
	row++
	var books [][]int
	for ; row < len(in); row++ {
		nums := strings.Split(in[row], ",")
		var pages []int
		for _, v := range nums {
			n, err := strconv.Atoi(v)
			if err != nil {
				fmt.Println(err)
				os.Exit(1)
			}
			pages = append(pages, n)
		}
		books = append(books, pages)

	}
	return arr, books
}

func isValid(mapBook map[int]bookOrder, book []int) bool {
	for k, v := range book {
		node := mapBook[v]
		for i := k + 1; i < len(book); i++ {
			var found bool
			for j := range node.after {
				if book[i] == node.after[j] {
					found = true
					break
				}
			}
			if !found {
				return false
			}
		}
	}
	return true
}

type bookOrder struct {
	book  int
	after []int
}

func part1(filename string) int {
	orders, books := parseInput(filename)
	m := make(map[int]bool)
	for _, row := range orders {
		m[row.left] = true
		m[row.right] = true
	}
	mapBook := make(map[int]bookOrder)
	for k := range m {
		mapBook[k] = bookOrder{book: k, after: make([]int, 0)}
	}

	for _, row := range orders {
		node := mapBook[row.left]
		node.after = append(node.after, row.right)
		mapBook[row.left] = node
	}

	var ans int
	for _, book := range books {
		if isValid(mapBook, book) {
			ans += book[len(book)/2]
		}
	}
	return ans
}

type node []int

func (a node) Len() int      { return len(a) }
func (a node) Swap(i, j int) { a[i], a[j] = a[j], a[i] }
func (a node) Less(i, j int) bool {
	n := ordering[a[i]]
	for _, v := range n.after {
		if v == a[j] {
			return true
		}
	}
	return false
}

var ordering map[int]bookOrder

func part2(filename string) int {
	orders, books := parseInput(filename)
	m := make(map[int]bool)
	for _, row := range orders {
		m[row.left] = true
		m[row.right] = true
	}
	mapBook := make(map[int]bookOrder)
	for k := range m {
		mapBook[k] = bookOrder{book: k, after: make([]int, 0)}
	}

	for _, row := range orders {
		node := mapBook[row.left]
		node.after = append(node.after, row.right)
		mapBook[row.left] = node
	}

	ordering = mapBook

	var ans int
	for _, book := range books {
		if !isValid(mapBook, book) {
			b := node(book)
			sort.Sort(b)
			ans += b[len(b)/2]
		}
	}
	return ans
}
