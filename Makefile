
all: 
	sbt compile; \
	sbt run "docs/source";
	cd docs; \
	sphinx-build -b html prepped build

clean:
	cd docs; \
	rm -rf build; \
	rm -rf prepped
