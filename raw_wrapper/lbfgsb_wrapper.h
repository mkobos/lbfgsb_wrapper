#ifndef LBFGSB_WRAPPER
#define LBFGSB_WRAPPER

//@author: Mateusz Kobos
//Wrapper inspired by L-BFGS-B C++ binding from 
//"A MATLAB interface for L-BFGS-B" by Peter Carbonetto 
//available on http://www.cs.ubc.ca/~pcarbo/lbfgsb-for-matlab.html .

#define LBFGSB_TASK_SIZE 60

enum lbfgsb_task_type { LBFGSB_FG, LBFGSB_NEW_X, LBFGSB_CONV, LBFGSB_ABNO, 
	LBFGSB_ERROR, LBFGSB_START, LBFGSB_STOP, LBFGSB_UNKNOWN };

// Data used by the algorithm. 
// Check the original Fortran source code to see what each variable means.
struct lbfgsb{
	int n; // variables no.
	int m; // corrections no.
	double* x; // point
	double* l; // lower bounds
	double* u; // upper bounds
	int* nbd; // bound type for each dimension: 
				// 0 - unbounded,
				// 1 - only a lower bound,
				// 2 - both lower and upper bounds, 
				// 3 - only an upper bound.
	double f; // value of the function at point x
	double* g; // gradient of the function at point x
	double factr; // function value stop condition tolerance factor, 0 to suppress this stop condition
	double pgtol; // gradient norm value stop condition
	double* wa; // workspace array 
	int* iwa; // integer workspace array
	char task[LBFGSB_TASK_SIZE+1]; // task name string:
					// Strings returned by the algorithm:
					// "FG"
					// "NEW_X"
					// "CONV"
					// "ABNO"
					// "ERROR"
					// Strings that can be set by the user:
					// "START"
					// "STOP"
	int iprint; // output printing frequency 
	char csave[60]; // character working array
	int lsave[4]; // logical working array
	int isave[44]; // integer working array
	double dsave[29]; // double working array
};

// Create algorithm data with default values set where it is possible
struct lbfgsb* lbfgsb_create(int n, int m);

// Delete algorithm structure
void lbfgsb_delete(struct lbfgsb* data);

void lbfgsb_step(struct lbfgsb* data);

void lbfgsb_set_task(struct lbfgsb* data, enum lbfgsb_task_type type);

enum lbfgsb_task_type lbfgsb_get_task(struct lbfgsb* data);

// Internal task manipulation function: 
// Copies C-string (with '\0' at the end), to Fortran-string (padded with spaces)
void lbfgsb_set_task_str(struct lbfgsb* data, char* c_str);

// Internal task manipulation function: 
// @return true iff the task description is equal up to the length of c_str
int lbfgsb_is_task_str_equal(struct lbfgsb* data, char* c_str);

#endif
