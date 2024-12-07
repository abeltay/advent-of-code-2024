prep:
	echo $(DAY) > day.txt
	cp -r template $(DAY)

kotlin-prep:
	echo $(DAY) > day.txt
	cp src/template.kt src/Day$(DAY).kt
	sed -i '' -e 's/{TEMPLATE}/$(DAY)/g' src/Day$(DAY).kt
	touch src/Day$(DAY)_test.txt

input: DAY=$(shell cat day.txt)
input: COOKIE=$(shell cat cookie.txt)
input: URL_DAY=$(shell echo $(DAY) | sed 's/^0*//')
input:
	curl --location --request GET "https://adventofcode.com/2024/day/$(URL_DAY)/input" \
		--header "Cookie: $(COOKIE)" --output "$(DAY)/testdata/input.txt"

kotlin-input: DAY=$(shell cat day.txt)
kotlin-input: COOKIE=$(shell cat cookie.txt)
kotlin-input: URL_DAY=$(shell echo $(DAY) | sed 's/^0*//')
kotlin-input:
	curl --location --request GET "https://adventofcode.com/2024/day/$(URL_DAY)/input" \
		--header "Cookie: $(COOKIE)" --output "src/Day$(DAY).txt"

test: DAY = $(shell cat day.txt)
test:
	go test -v $(DAY)/*.go
