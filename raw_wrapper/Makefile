CC = gcc
FC = gfortran

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

build/solver.o: fortran_src/solver.f
	$(FC) $(FFLAGS) -c fortran_src/solver.f -o build/solver.o

build/blas.o: fortran_src/blas.f
	$(FC) $(FFLAGS) -c fortran_src/blas.f -o build/blas.o

build/linpack.o: fortran_src/linpack.f
	$(FC) $(FFLAGS) -c fortran_src/linpack.f -o build/linpack.o

build/timer.o: fortran_src/timer.f
	$(FC) $(FFLAGS) -c fortran_src/timer.f -o build/timer.o
	
clean:
	#cd build && rm -f -r *
	#cd dist && rm -f -r *
	rm -r build
	rm -r dist

