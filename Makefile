prep:
	echo $(DAY) > day.txt
	cp src/template.kt src/Day$(DAY).kt
	sed -i '' -e 's/{TEMPLATE}/$(DAY)/g' src/Day$(DAY).kt
	touch src/Day$(DAY)_test.txt

input: DAY=$(shell cat day.txt)
input: COOKIE=$(shell cat cookie.txt)
input: URL_DAY=$(shell echo $(DAY) | sed 's/^0*//')
input:
	curl --location --request GET "https://adventofcode.com/2024/day/$(URL_DAY)/input" \
		--header "Cookie: $(COOKIE)" --output "src/Day$(DAY).txt"
