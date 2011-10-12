#include "lbfgsb_wrapper.h"
#include <stdio.h>
#include <string.h>

double fun(double x)
{
	return (x+4)*(x+4);
}

double df(double x)
{
	return 2*(x+4);
}

int main(int argc, char *argv[])
{
	struct lbfgsb* data = lbfgsb_create(1, 5);
	data->iprint = 1;
	data->x[0] = 40;
	data->l[0] = -100;
	data->u[0] = 100;
	data->nbd[0] = 2;
	
//	printf("float size=%u\n", sizeof(float));
//	printf("double size= %u\n", sizeof(double));
//	printf("int size= %u\n", sizeof(int));
//	printf("char size= %u\n", sizeof(char));

	lbfgsb_set_task(data, LBFGSB_START);
	int i = 0;
	int i_max = 100;
	while(1)
	{
		if(i==i_max)
		{
			lbfgsb_set_task(data, LBFGSB_STOP);
			lbfgsb_step(data);
			break;
		}
		lbfgsb_step(data);
		printf("---->%d x: %lf\n", i, data->x[0]);
		enum lbfgsb_task_type type = lbfgsb_get_task(data);
		if(type==LBFGSB_FG)
		{
			printf("FG!\n");
			data->f = fun(data->x[0]);
			data->g[0] = df(data->x[0]);
		}
		else if(type==LBFGSB_NEW_X)
		{
			printf("NEW_X! - new iteration\n");
		}
		else if(type==LBFGSB_CONV)
		{
			printf("Convergence!\n");
			break;
		}
		else if(type==LBFGSB_ABNO)
		{
			printf("Abnormal exit!!\n");
			break;
		}
		else if(type==LBFGSB_ERROR)
		{
			printf("Error!!!\n");
			break;
		}
		i++;
	}
	printf("The end my friend\n");
	lbfgsb_delete(data);
	return 0;
}
