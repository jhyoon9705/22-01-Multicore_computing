#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define CUDA 0
#define OPENMP 1
#define SPHERES 20

#define rnd( x ) (x * rand() / RAND_MAX)
#define INF 2e10f
#define DIM 2048

struct Sphere {
    float    r,b,g;
    float    radius;
    float    x,y,z;
    __device__ float  hit( float  ox, float  oy, float  *n ) {
        float  dx = ox - x;
        float  dy = oy - y;
        if  (dx*dx + dy*dy < radius*radius) {
            float  dz = sqrtf( radius*radius - dx*dx - dy*dy );
            *n = dz / sqrtf( radius * radius );
            return  dz + z;
        }
        return  -INF;
    }
};

void ppm_write(unsigned char* bitmap, int  xdim,int  ydim, FILE* fp)
{
  int  i,x,y;
  fprintf(fp,"P3\n");
  fprintf(fp,"%d %d\n",xdim, ydim);
  fprintf(fp,"255\n");
  for  (y=0 ;y<ydim;y++) {
    for  (x=0 ;x<xdim;x++) {
      i=x+y*xdim;
      fprintf(fp,"%d %d %d ",bitmap[4 *i],bitmap[4 *i+1 ],bitmap[4 *i+2 ]);
    }
    fprintf(fp,"\n");
  }
}

__global__ void kernel(Sphere *s, unsigned char *ptr)
{
  int  x = threadIdx.x + blockIdx.x * blockDim.x;
  int  y = threadIdx.y + blockIdx.y * blockDim.y;
  int  offset = x + y * blockDim.x * gridDim.x;
  float  ox = (x - DIM/2 );
  float  oy = (y - DIM/2 );

  float  r=0 , g=0 , b=0 ;
  float    maxz = -INF;
  for (int  i=0 ; i<SPHERES; i++) {
    float    n;
    float    t = s[i].hit( ox, oy, &n );
    if  (t > maxz) {
      float  fscale = n;
      r = s[i].r * fscale;
      g = s[i].g * fscale;
      b = s[i].b * fscale;
      maxz = t;
    } 
  }
  ptr[offset*4  + 0 ] = (int )(r * 255 );
  ptr[offset*4  + 1 ] = (int )(g * 255 );
  ptr[offset*4  + 2 ] = (int )(b * 255 );
  ptr[offset*4  + 3 ] = 255 ;
}

int  main(void)
{
  int  x,y;
  cudaEvent_t start, end;
  float  execTime;

  cudaEventCreate(&start);
  cudaEventCreate(&end);

  unsigned char* bitmap;
  unsigned char* d_bitmap;
  Sphere *d_s;

  srand(time(NULL));
  FILE* fp = fopen("result.ppm","w");

  cudaEventRecord(start, 0 ); // timer start

  Sphere *temp_s = (Sphere*)malloc( sizeof(Sphere) * SPHERES );
  
  for  (int  i=0 ; i<SPHERES; i++) {
    temp_s[i].r = rnd( 1.0 f );
    temp_s[i].g = rnd( 1.0 f );
    temp_s[i].b = rnd( 1.0 f );
    temp_s[i].x = rnd( 2000.0 f ) - 1000 ;
    temp_s[i].y = rnd( 2000.0 f ) - 1000 ;
    temp_s[i].z = rnd( 2000.0 f ) - 1000 ;
    temp_s[i].radius = rnd( 200.0 f ) + 40 ;
  }
  
  bitmap=(unsigned char*)malloc(sizeof(unsigned char)*DIM*DIM*4 );

  // memory allocation to use in device memory
  cudaMalloc((void**)&d_bitmap, sizeof(unsigned char)*DIM*DIM*4 );
  cudaMalloc((void**)&d_s, sizeof(Sphere)*SPHERES);


  // memory copy from host(temp_s) to device(d_s)
  cudaMemcpy(d_s, temp_s, sizeof(Sphere)*SPHERES, cudaMemcpyHostToDevice);
  
  // configures the number of grid and thread 
  dim3 grids(DIM/16 , DIM/16 );
  dim3 threads(16 , 16 );

  // call kernel function
  kernel<<<grids, threads>>>(d_s, d_bitmap);

  // memory copy from device(d_bitmap; result) to host(bitmap)
  cudaMemcpy(bitmap, d_bitmap, sizeof(unsigned char)*DIM*DIM*4 , cudaMemcpyDeviceToHost);

  cudaEventRecord(end, 0 ); // timer stop
  cudaEventSynchronize(end);
  cudaEventElapsedTime(&execTime, start, end); // caculate execution time
    
  ppm_write(bitmap,DIM,DIM,fp); // writing result in a result file

  printf("CUDA ray tracing: %lf sec\n", execTime * 0.001 );
  printf("[result.ppm] was generated.\n");
  fclose(fp);
  cudaEventDestroy(start);
  cudaEventDestroy(end);
  free(bitmap);
  free(temp_s);

  return  0 ;
}
