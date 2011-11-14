CC = gcc
F77 = gfortran

CFLAGS_DEBUG = -fPIC -Wall -g
CFLAGS_RELEASE = -fPIC -Wall -O3
CFLAGS = $(CFLAGS_RELEASE)
FFLAGS_DEBUG = -fPIC -Wall -fexceptions -g
FFLAGS_RELEASE = -fPIC -Wall -fexceptions -O3
FFLAGS = $(FFLAGS_RELEASE)

all: dist

init:
	mkdir -p dist
	mkdir -p build

dist: init dist/liblbfgsb_wrapper.so dist/test_prog

dist/liblbfgsb_wrapper.so: build/lbfgsb_wrapper.o build/solver.o  build/blas.o build/linpack.o build/timer.o lbfgsb_wrapper.i
	mkdir -p dist/src/lbfgsb/jniwrapper
	swig -java -package lbfgsb.jniwrapper -outdir dist/src/lbfgsb/jniwrapper lbfgsb_wrapper.i
	$(CC) -I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/linux -fPIC -c lbfgsb_wrapper_wrap.c -o build/lbfgsb_wrapper_wrap.o #JAVA_HOME environment variable has to be set
	$(CC) -shared -o dist/liblbfgsb_wrapper.so build/lbfgsb_wrapper_wrap.o build/lbfgsb_wrapper.o build/solver.o build/blas.o build/linpack.o build/timer.o -lm -lgfortran
	

dist/test_prog: build/test_prog.o build/lbfgsb_wrapper.o build/solver.o build/blas.o build/linpack.o build/timer.o
	$(CC) -o dist/test_prog build/test_prog.o build/lbfgsb_wrapper.o build/solver.o build/blas.o build/linpack.o build/timer.o -lm -lgfortran

build/test_prog.o: test_prog.c
	$(CC) $(CFLAGS) -c test_prog.c -o build/test_prog.o

build/lbfgsb_wrapper.o: lbfgsb_wrapper.c lbfgsb_wrapper.h
	$(CC) $(CFLAGS) -c lbfgsb_wrapper.c -o build/lbfgsb_wrapper.o

build/solver.o: solver.f
	$(F77) $(FFLAGS) -c solver.f -o build/solver.o

build/blas.o: blas.f
	$(F77) $(FFLAGS) -c blas.f -o build/blas.o

build/linpack.o: linpack.f
	$(F77) $(FFLAGS) -c linpack.f -o build/linpack.o

build/timer.o: timer.f
	$(F77) $(FFLAGS) -c timer.f -o build/timer.o
	
clean:
	#cd build && rm -f -r *
	#cd dist && rm -f -r *
	rm -r build
	rm -r dist

