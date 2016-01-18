#include<stdio.h>
#include<iostream>
#include<string.h>
#include<string>
#include<stdlib.h>
#include<string.h>
#include<time.h>
#include<pthread.h>
using namespace std;
void* SeqReadWriteB(void *);
void* SeqReadWriteKB(void *);
void* SeqReadWriteMB(void *);
void* RanReadWriteB(void *);
void* RanReadWriteKB(void *);
void* RanReadWriteMB(void *);

int main()
{
	int select;
	pthread_t thread1,thread2;
	clock_t startTime,endTime;
	while(true)
	{
		cout<<"1.Sequential 1 thread"<<endl<<"2.Sequential 2 threads"<<endl<<"3.Random 1 thread"<<endl<<"4.Random 2 threads"<<endl<<"5.Close"<<endl;
		cout<<"Enter your Choice: ";
		cin>>select;
		switch(select)
		{
			case 1: cout<<endl;
				startTime=clock();
				pthread_create(&thread1,NULL,SeqReadWriteB,NULL);
				pthread_join(thread1,NULL);
				endTime=clock();
				cout<<"Time taken for 1Byte block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1Byte block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(5ul*1024ul*1024ul*1024ul))*1000ul<<"ms"<<endl;
				cout<<"Throughput for 1Byte block : "<<(400)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<" MB/sec"<<endl;
				
				cout<<endl;
				startTime=clock();
				pthread_create(&thread1,NULL,SeqReadWriteKB,NULL);
				pthread_join(thread1,NULL);
				endTime=clock();
				cout<<"Time taken for 1KB block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1KB block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(5ul*1024ul*1024ul*1024ul))*1000ul<<"ms"<<endl;
				cout<<"Throughput for 1KB block : "<<(16000)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<" MB/sec"<<endl;
				
				cout<<endl;
				startTime=clock();
				pthread_create(&thread1,NULL,SeqReadWriteMB,NULL);
				pthread_join(thread1,NULL);
				endTime=clock();
				cout<<"Time taken for 1MB block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1MB block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(5ul*1024ul*1024ul*1024ul))*1000ul<<"ms"<<endl;
				cout<<"Throughput for 1MB block : "<<(16384)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<" MB/sec"<<endl;

				break;
			    case 2: cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,SeqReadWriteB,NULL);
				pthread_create(&thread2,NULL,SeqReadWriteB,NULL);
                pthread_join(thread1,NULL);
				pthread_join(thread2,NULL);
                endTime=clock();
				cout<<"The time for 1Byte block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
                cout<<"Latency for 1Byte block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(5ul*1024ul*1024ul*1024ul))*1000ul<<"ms"<<endl;
				cout<<"Throughput for 1Byte block : "<<(2*400)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
				cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,SeqReadWriteKB,NULL);
				pthread_create(&thread2,NULL,SeqReadWriteKB,NULL);
                pthread_join(thread1,NULL);
				pthread_join(thread2,NULL);
                endTime=clock();
                cout<<"The time for 1KB block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1KB block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(5ul*1024ul*1024ul*1024ul))*1000ul<<"ms"<<endl;
				cout<<"Throughput for 1KB block :  "<<(2*16000)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
                cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,SeqReadWriteMB,NULL);
				pthread_create(&thread2,NULL,SeqReadWriteMB,NULL);
                pthread_join(thread1,NULL);
				pthread_join(thread2,NULL);
                endTime=clock();
                cout<<"The time for 1MB block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1MB block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(5ul*1024ul*1024ul*1024ul))*1000ul<<"ms"<<endl;
				cout<<"Throughput for 1MB block :  "<<(2*16384)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
                break;
				case 3: cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,RanReadWriteB,NULL);
                pthread_join(thread1,NULL);
                endTime=clock();
                cout<<"The time for 1Byte block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1Byte block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(20ul*1024ul*1024ul))<<"ms"<<endl;
				cout<<"Throughput for 1Byte block : "<<(1*20)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
                cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,RanReadWriteKB,NULL);
                pthread_join(thread1,NULL);
                endTime=clock();
                cout<<"The time for 1KB block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1KB block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(20ul*1024ul*1024ul))<<"ms"<<endl;
				cout<<"Throughput for 1KB block : "<<(256*20)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
                cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,RanReadWriteMB,NULL);
                pthread_join(thread1,NULL);
                endTime=clock();
                cout<<"The time for 1MB block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1MB block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(20ul*1024ul*1024ul))<<"ms"<<endl;
				cout<<"Throughput for 1MB block :  "<<(256*20)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
                break;
				case 4: cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,RanReadWriteB,NULL);
                pthread_create(&thread2,NULL,RanReadWriteB,NULL);
                pthread_join(thread1,NULL);
                pthread_join(thread2,NULL);
                endTime=clock();
                cout<<"The time for 1Byte block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1Byte block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(20ul*1024ul*1024ul))<<"ms"<<endl;
				cout<<"Throughput for 1Byte block :  "<<(2*20)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
                cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,RanReadWriteKB,NULL);
                pthread_create(&thread2,NULL,RanReadWriteKB,NULL);
                pthread_join(thread1,NULL);
                pthread_join(thread2,NULL);
                endTime=clock();
                cout<<"The time for 1KB block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1KB block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(20ul*1024ul*1024ul))<<"ms"<<endl;
				cout<<"Throughput for 1KB block :  "<<(2*256*20)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
                cout<<endl;
				startTime=clock();
                pthread_create(&thread1,NULL,RanReadWriteMB,NULL);
                pthread_create(&thread2,NULL,RanReadWriteMB,NULL);
                pthread_join(thread1,NULL);
                pthread_join(thread2,NULL);
                endTime=clock();
                cout<<"The time for 1MB block : "<<(double)(endTime-startTime)/CLOCKS_PER_SEC<<endl;
				cout<<"Latency for 1MB block : "<<(((double)(endTime-startTime)/CLOCKS_PER_SEC)/(20ul*1024ul*1024ul))<<"ms"<<endl;
				cout<<"Throughput for 1MB block : "<<(2*256*20)/((double)(endTime-startTime)/CLOCKS_PER_SEC)<<"MB/sec"<<endl;
                break;

			    case 5: exit(0);
		}
		cout<<endl;cout<<endl;
	}
}

void* SeqReadWriteB(void* param)
{
	long long i;
        int j;
        char *sourceString, *destinationString, *temp;
        sourceString = (char *)malloc(1048576);
        destinationString = (char *)malloc(1048576);
        temp=sourceString;
        for(i=0;i<1048576;i++)
        {
                *temp='a';
                temp++;
        }
        temp=sourceString;
        for(j=0;j<400;j++)
        {
                for(i=0;i<1048576;i++)
                {
                        memcpy(destinationString+(i),sourceString+(i),1);
                }
	}
	free(sourceString);free(destinationString);

}


void* SeqReadWriteKB(void* param)
{
	long long i;
        int j;
        char *sourceString, *destinationString, *temp;
        sourceString = (char *)malloc(1048576*200);
        destinationString = (char *)malloc(1048576*200);
	temp=sourceString;
        for(i=0;i<1048576*200;i++)
        {
                *temp='a';
                temp++;
        }
        
        for(j=0;j<80;j++)
        {
                for(i=0;i<1024*200;i++)
                {
                        memcpy(destinationString+(i*1024),sourceString+(i*1024),1024);
                }
        }free(sourceString);free(destinationString);
}

void* SeqReadWriteMB(void* param)
{
	long long i;
        int j;
        char *sourceString, *destinationString, *temp;
        sourceString = (char *)malloc(20971520);
        destinationString = (char *)malloc(20971520);
        temp=sourceString;
	for(i=0;i<20971520;i++)
        {
                *temp='a';
                temp++;
        }

	for(j=0;j<8192;j++)
        {
                for(i=0;i<2;i++)
                {
                        memcpy(destinationString+(i*1024*1024),sourceString+(i*1024*1024),1048576);
                }
        }free(sourceString);free(destinationString);
}

void* RanReadWriteB(void* param)
{
	long long i,j,k;
        char *sourceString, *destinationString, *temp;
        sourceString = (char *)malloc(1048576);
        destinationString = (char *)malloc(1048576);
        temp=sourceString;
        for(i=0;i<1048576;i++)
        {
                *temp='a';
                temp++;
        }
        temp=sourceString;
        for(j=0;j<20;j++)
	{
		for(i=0;i<1048576;i++)
		{
			k=rand()%1048576;
			memcpy(destinationString+i,sourceString+k,1);
		}
	}free(sourceString);free(destinationString);
}
void* RanReadWriteKB(void *param)
{
	long long i,j,k;
        char *sourceString, *destinationString, *temp;
        sourceString = (char *)malloc(20971520);
        destinationString = (char *)malloc(20971520);
        temp=sourceString;
        for(i=0;i<20971520;i++)
        {
                *temp='a';
                temp++;
        }

	for(j=0;j<256;j++)
        {
                for(i=0;i<20480;i++)
                {
			k=rand()%20479;
                        memcpy(destinationString+(i*1024),sourceString+(k*1024),1024);
                }
        }free(sourceString);free(destinationString);
}

void* RanReadWriteMB(void *param)
{
	long long i,j,k;
        char *sourceString, *destinationString, *temp;
        sourceString = (char *)malloc(20971520);
        destinationString = (char *)malloc(20971520);
        temp=sourceString;
        for(i=0;i<20971520;i++)
        {
                *temp='a';
                temp++;
        }

        for(j=0;j<256;j++)
        {
                for(i=0;i<20;i++)
                {
                        k=rand()%19;
			memcpy(destinationString+(i*1048576),sourceString+(k*1048576),1048576);
                }
        }free(sourceString);free(destinationString);
}

int randomFun (unsigned int min, unsigned int max)
{
  int base_random = rand(); 
  if (RAND_MAX == base_random) return randomFun(min, max);
  int range       = max - min,
      remainder   = RAND_MAX % range,
      bucket      = RAND_MAX / range;
   if (base_random < RAND_MAX - remainder) {
    return min + base_random/bucket;
  } else {
    return randomFun (min, max);
  }
}

