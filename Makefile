
all: 
	cd docs; \
	sphinx-build -b html source build

clean:
	cd docs; \
	rm -rf build
