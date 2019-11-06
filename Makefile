
.PHONY: build

SRCROOTS = src test
SOURCES = $(foreach root, $(SRCROOTS), $(shell find $(root) -name '*.clj')) project.clj


build: feedeen-stat.jar

feedeen-stat.jar: $(SOURCES)
	lein uberjar
	mv target/feedeen-stat-0.1.0-SNAPSHOT-standalone.jar feedeen-stat.jar
