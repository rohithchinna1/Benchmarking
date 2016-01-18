#include<stdio.h>
#include<sys/time.h>
#include<cuda.h>
#define N 1024

__global__ void add( int *a, int *b, int *c ) {
 if(threadIdx.x<N)
c[threadIdx.x] = a[threadIdx.x] + b[threadIdx.x];
}

__global__ void add1( float  *a, float  *b, float  *c ) {
 if(threadIdx.x<N)
c[threadIdx.x] = a[threadIdx.x] + b[threadIdx.x];
}

long getMicroSeconds();
double noofOperations,totalTime,IOPS,GFLOPS,FLOPS,GIOPS,totalTime1,IFLOPS;


int main( void ) {
float a,b,c;
int a1[N], b1[N], c1[N];
float *dev_a,*dev_b,*dev_c;
int *dev_a1, *dev_b1, *dev_c1;
double  time=0;
int size=sizeof(int);
int fsize=sizeof(float);
double start,stop,end,start1,end1,time1;
int i;
int threadCount=0;
cudaMalloc( (void**)&dev_a, N * sizeof(float) );
cudaMalloc( (void**)&dev_b, N * sizeof(float) );
cudaMalloc( (void**)&dev_c, N * sizeof(float) );

a=2.3;
b=4.2;
int istart = getMicroSeconds();
cudaMemcpy(dev_a, &a,fsize, cudaMemcpyHostToDevice);
stop=getMicroSeconds();
time=fsize/(stop-start);
printf("\n Read bandwidth for 1B%f\t\n",time);
cudaMemcpy(dev_b, &b,fsize, cudaMemcpyHostToDevice);
start = getMicroSeconds();
add1<<<1,1>>>(dev_a, dev_b, dev_c);
threadCount=1;
noofOperations=1;
stop=getMicroSeconds();
totalTime=fsize/(stop-start);
printf("\n Write Bandwidth of 1B %f\n",totalTime);
FLOPS=noofOperations/totalTime;
printf("\n FLOPS\t%f",FLOPS);
printf("\tThreadCount\t\%d\n",threadCount);

GFLOPS=FLOPS/(pow(10,9));
printf("time taken in gflops: %f\n",GFLOPS);

cudaMemcpy( c, dev_c, N * sizeof(float), cudaMemcpyDeviceToHost );
printf("c %f \n",c);

cudaFree(dev_a); cudaFree(dev_b); cudaFree(dev_c);

//************1 KB byte************************      
// allocate the memory on the GPU
cudaMalloc( (void**)&dev_a1, N * sizeof(int) ) ;
cudaMalloc( (void**)&dev_b1, N * sizeof(int) ) ;
cudaMalloc( (void**)&dev_c1, N * sizeof(int) ) ;

 // fill the arrays 'a' and 'b' on the CPU
for (i=0; i<N; i++)
{
a1[i] = -i;
b1[i] = i * i;
}

time=0;
start=getMicroSeconds();

cudaMemcpy( dev_a1, a1, N * sizeof(int), cudaMemcpyHostToDevice );
cudaMemcpy( dev_b1, b1, N * sizeof(int), cudaMemcpyHostToDevice );
  end=getMicroSeconds();
  time=(2*size)/(end-start);
  printf("\n Read bandwidth for 1KB%f\t\n",time);
  start=getMicroSeconds();
       add<<<1,N>>>( dev_a1, dev_b1, dev_c1 );
       stop=getMicroSeconds();
   start1=getMicroSeconds();
  cudaMemcpy( c1, dev_c1, N * sizeof(int), cudaMemcpyDeviceToHost );
  end1=getMicroSeconds();
  time1=(end1-start1);
  totalTime1=(2*size)/time1;
  printf("\n Write Bandwidthof 1KB %f\n",totalTime1);


  time=(stop-start);
  threadCount=N;
  noofOperations=1;
  totalTime=(2*size)/time;
  IOPS=noofOperations/totalTime;
  printf("\n IOPS\t%f\t",IOPS);
  printf("\ThreadCount\t\%d\n",threadCount);
  IFLOPS=IOPS/(pow(10,9));
  printf("time taken in Iflops: %f\n",IFLOPS);

  start1=getMicroSeconds(); 
  cudaMemcpy( c1, dev_c1, N * sizeof(int), cudaMemcpyDeviceToHost );
  end1=getMicroSeconds();
  time1=(end1-start1);

totalTime1=(2*size)/time1;
  printf("\n Write Bandwidth %f\n",totalTime1);


       // display the results
  for (int i=0; i<N; i++) {
   printf( "%d + %d = %d\n", a1[i], b1[i], c1[i] );
   }
       // free the memory allocated on the GPU
       cudaFree( dev_a1 );
       cudaFree( dev_b1 );
       cudaFree( dev_c1 );
return 0;
}


 long getMicroSeconds(){
        struct timeval tv;
        gettimeofday(&tv, NULL);
        return tv.tv_sec * 1000000 + tv.tv_usec;
}

