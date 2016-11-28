CC = gcc
FC = gfortran
LIBGFORTRAN = -lgfortran

CFLAGS_DEBUG = -fPIC -Wall -g
CFLAGS_RELEASE = -fPIC -Wall -O3
CFLAGS = $(CFLAGS_RELEASE)
FFLAGS_DEBUG = -fPIC -Wall -fexceptions -g
FFLAGS_RELEASE = -fPIC -Wall -fexceptions -O3
FFLAGS = $(FFLAGS_RELEASE)

ifeq ($(OS),Windows_NT)
  WINDOWS = 1
else
  ifeq ($(OS),Windows)
    WINDOWS = 1
  else
    WINDOWS = 0
  endif
endif

ifeq ($(WINDOWS),0)
  LIB_NAME = liblbfgsb_wrapper.so
  JNI_ARCH = linux
  LDFLAGS = -s -Wl,--version-script=lbfgsb_wrapper.map
else
  LIB_NAME = lbfgsb_wrapper.dll
  JNI_ARCH = win32
  LDFLAGS = -s -Wl,-kill-at
endif
JNI_ARCH_DIR =
ifeq ($(JNI_ARCH_DIR),)
  JNI_ARCH_DIR_AUTO = $(JAVA_HOME)/include/$(JNI_ARCH)
else
  JNI_ARCH_DIR_AUTO = $(JNI_ARCH_DIR)
endif

all: dist

init:
	mkdir -p dist
	mkdir -p build

dist: init dist/$(LIB_NAME) dist/test_prog

dist/$(LIB_NAME): build/lbfgsb_wrapper.o build/solver.o  build/blas.o build/linpack.o build/timer.o lbfgsb_wrapper.i
	mkdir -p dist/src/lbfgsb/jniwrapper
	swig -java -package lbfgsb.jniwrapper -outdir dist/src/lbfgsb/jniwrapper lbfgsb_wrapper.i
	$(CC) -I $(JAVA_HOME)/include -I $(JNI_ARCH_DIR_AUTO) -fPIC -c lbfgsb_wrapper_wrap.c -o build/lbfgsb_wrapper_wrap.o #JAVA_HOME environment variable has to be set
	$(CC) $(LDFLAGS) -shared -o dist/$(LIB_NAME) build/lbfgsb_wrapper_wrap.o build/lbfgsb_wrapper.o build/solver.o build/blas.o build/linpack.o build/timer.o -lm $(LIBGFORTRAN)
	

dist/test_prog: build/test_prog.o build/lbfgsb_wrapper.o build/solver.o build/blas.o build/linpack.o build/timer.o
	$(CC) -o dist/test_prog build/test_prog.o build/lbfgsb_wrapper.o build/solver.o build/blas.o build/linpack.o build/timer.o -lm $(LIBGFORTRAN)

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

