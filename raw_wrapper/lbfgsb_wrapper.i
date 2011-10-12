%module lbfgsb_wrapper
%{
#include "lbfgsb_wrapper.h"
%}

//Generate real java enums 
//(instead of some pseudo-enums which are the only available option 
//before java 1.5)
%include "enums.swg" 

//Arrays manipulation helpers{
%include "carrays.i"
%array_functions(int, intArray);
%array_functions(double, doubleArray);
//Arrays manipulation helpers}

%include "lbfgsb_wrapper.h"
