HASH=$(addprefix hash/, BFHash FNVHash RanHash MurHash)

FILTER=$(addprefix filter/, BloomFilter BloomFilterAbstract BloomFilterFNV BloomFilterMurmur BloomFilterRan)

DIFF=$(addprefix differential/, BloomDifferential NaiveDifferential)

PA1=$(addprefix coms435/pa1/, $(DIFF) $(FILTER) $(HASH) CMS EmpericalComparison FalsePositives)

FILES=$(addprefix bin/main/, $(addsuffix .class, $(PA1) ie/ucd/murmur/MurmurHash))

TEST=$(addprefix bin/test/coms435/pa1/, $(addsuffix .class, differential/DiffTest filter/FilterTest hash/HashTest Pa1Test))


.PHONY: default makebin clean

default: $(FILES)
	@echo "compilation successful"

test: $(TEST)
	@echo "test compilation successful"

bin/%.class: src/%.java | makebin
	@echo "compiling $<"
	@javac -Werror -d bin/ -cp bin:src/main:src/test -Xlint $<


clean:
	@[ ! -d bin ] || echo "removing bin directory"
	@[ ! -d bin ] || rm -r bin

makebin:
	@[  -d bin ] || echo "making bin directory"
	@[  -d bin ] || mkdir bin