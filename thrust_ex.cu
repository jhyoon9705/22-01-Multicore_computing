#include <thrust/device_vector.h>
#include <thrust/host_vector.h>
#include <thrust/transform.h>
#include <thrust/sequence.h>
#include <cmath>
#include <iostream>
#include <chrono>

using namespace std::chrono;

long  num_steps = 100000000 ;
double step;

int  main(void)
{
    step = 1.0  / (double)num_steps;

    thrust::device_vector<double> X(num_steps);
    thrust::device_vector<double> Y(num_steps, 0.5 );
    thrust::device_vector<double> ONE(num_steps, 1.0 );
    thrust::device_vector<double> STEP(num_steps, step);
    thrust::device_vector<double> TEMP(num_steps, 4.0 );
    thrust::sequence(X.begin(), X.end(), 0 );

    auto start = high_resolution_clock::now();
 
    // (i+0.5)
    thrust::transform(X.begin(), X.end(), Y.begin(), X.begin(), thrust::plus<double>());
  
    // (i+0.5)*step (=x)
    thrust::transform(X.begin(), X.end(), STEP.begin(), X.begin(), thrust::multiplies<double>());

    // x*x
    thrust::transform(X.begin(), X.end(), X.begin(), X.begin(), thrust::multiplies<double>());
    // 1.0+x*x
    thrust::transform(X.begin(), X.end(), ONE.begin(), X.begin(), thrust::plus<double>());

    // 4.0/(1.0+x*x)
    thrust::transform(TEMP.begin(), TEMP.end(), X.begin(), TEMP.begin(), thrust::divides<double>());
 
   // add all elements
    double pi = step * thrust::reduce(TEMP.begin(), TEMP.end());
 
    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop - start);
 
    printf("pi=%.8lf\n", pi);
    std::cout << "Execution time: " << duration.count() << " microseconds" << std::endl;

    return  0 ;
}
