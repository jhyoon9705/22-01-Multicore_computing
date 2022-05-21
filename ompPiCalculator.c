#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

long num_steps = 10000000;
double step;


int main(int argc, char* argv[]) {
	int typeNum = atoi(argv[1]);
	int chunkSize = atoi(argv[2]);
	int threadNum = atoi(argv[3]);

	long i; double x, pi, sum = 0.0;
	double start_time, end_time;
	omp_set_num_threads(threadNum);

	if (typeNum == 1) {
		start_time = omp_get_wtime();
		step = 1.0 / (double)num_steps;
#pragma omp parallel for schedule(static, chunkSize) reduction(+:sum) private(x)
		for (int i = 0; i < num_steps; i++) {
			x = (i + 0.5) * step;
			sum = sum + 4.0 / (1.0 + x * x);
		}
		pi = step * sum;
	}

	else if (typeNum == 2) {
		start_time = omp_get_wtime();
		step = 1.0 / (double)num_steps;
#pragma omp parallel for schedule(dynamic, chunkSize) reduction(+:sum) private(x)
		for (int i = 0; i < num_steps; i++) {
			x = (i + 0.5) * step;
			sum = sum + 4.0 / (1.0 + x * x);
		}
		pi = step * sum;
	}

	else if (typeNum == 3) {
		start_time = omp_get_wtime();
		step = 1.0 / (double)num_steps;
#pragma omp parallel for schedule(guided, chunkSize) reduction(+:sum) private(x)
		for (i = 0; i < num_steps; i++) {
			x = (i + 0.5) * step;
			sum = sum + 4.0 / (1.0 + x * x);
		}
		pi = step * sum;
	}

	end_time = omp_get_wtime();
	double timeDiff = end_time - start_time;

	printf("Execution Time : %lf\n", timeDiff);
	printf("pi=%.24lf\n", pi);
}
