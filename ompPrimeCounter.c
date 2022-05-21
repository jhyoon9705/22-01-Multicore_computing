#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

#define END_NUMBER 200000

int isPrime(int x) {
	if (x <= 1) {
		return 0;
	}
	for (int i = 2; i < x; i++) {
		if (x % i == 0) {
			return 0;
		}
	}

	return 1;
}

int main(int argc, char* argv[]) {
	int typeNum = atoi(argv[1]);
	int threadNum = atoi(argv[2]);

	double start_time, end_time, timeDiff;
	omp_set_num_threads(threadNum);
	int primeCounter = 0;

	if (typeNum == 1) {
		start_time = omp_get_wtime();
#pragma omp parallel for schedule(static)
		for (int i = 1; i <= END_NUMBER; i++) {
			if (isPrime(i) == 1) {
#pragma omp critical
				primeCounter++;
			}
		}
	}

	else if (typeNum == 2) {
		start_time = omp_get_wtime();
#pragma omp parallel for schedule(dynamic)
		for (int i = 1; i <= END_NUMBER; i++) {
			if (isPrime(i) == 1) {
#pragma omp critical
				primeCounter++;
			}
		}
	}

	else if (typeNum == 3) {
		start_time = omp_get_wtime();
#pragma omp parallel for schedule(static, 10)
		for (int i = 1; i <= END_NUMBER; i++) {
			if (isPrime(i) == 1) {
#pragma omp critical
				primeCounter++;
			}
		}
	}

	else if (typeNum == 4) {
		start_time = omp_get_wtime();
#pragma omp parallel for schedule(dynamic, 10)
		for (int i = 1; i <= END_NUMBER; i++) {
			if (isPrime(i) == 1) {
#pragma omp critical
				primeCounter++;
			}
		}
	}

	else {
		printf("TypeNumber error!\n");
	}

	end_time = omp_get_wtime();
	timeDiff = end_time - start_time;

	printf("Program execution time: %lf\n", timeDiff);
	printf("1...%d prime# counter = %d", END_NUMBER, primeCounter);
	
}
