import java.util.Random;
import java.util.Scanner;

class cpuExec extends Thread{
	public static int thread_count;
	public static long emptyLoop_float_elapsedTime;
	public static long emptyLoop_int_elapsedTime;
	public void run(){
		int thread_count = cpuExec.thread_count;
		System.out.println("Thread #"+ thread_count+" Start");
		long flops_elapsedTime = flops_elapsedTime();
		long iops_elapsedTime = iops_elapsedTime();
		System.out.println("Thread #"+thread_count+" : Time for FLOPS	:"+ flops_elapsedTime+"ms");
		System.out.println("Thread #"+thread_count+" : Time for IOPS	:"+ iops_elapsedTime+"ms");
		
		float no_of_gflops = calg(flops_elapsedTime);
		System.out.println("Thread #"+thread_count+" : Number of GFLOPS	:"+ no_of_gflops);
		float no_of_giops = calg(iops_elapsedTime);
		System.out.println("Thread #"+thread_count+" : Number of GIOPS	:"+ no_of_giops);
		System.out.println("Thread #"+ thread_count+" End");
	}	
	
	public static float calg(float value){
		value = value/1000;
		float g = ((Integer.MAX_VALUE)/value)/1000000000;
		return g;
	}
	
	public static long flops_elapsedTime(){		
		float a = (float)( new Random_Value().integer());
		float b = (float)( new Random_Value().integer());
		float c = 0;
		Stopwatch St = new Stopwatch();
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			 c=a+b;			
		}
		float d = c;
		return St.elapsedTime();
	}
	
	
	public static long iops_elapsedTime(){		
		int a = new Random_Value().integer();
		int b = new Random_Value().integer();
		int c =0;
		Stopwatch St = new Stopwatch();
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			 c =a+b;			
		}
		int d = c;
		return St.elapsedTime();
	}
}



class cpu {
	public static void main(String[] argv) throws InterruptedException	
	{
	while(true){
		int total_Threads;
		cpuExec[] run = new cpuExec[8];
		long emptyLoop_float_elapsedTime = emptyLoop_float_elapsedTime();
		long emptyLoop_int_elapsedTime = emptyLoop_int_elapsedTime();
		cpuExec.emptyLoop_float_elapsedTime = emptyLoop_float_elapsedTime;
		cpuExec.emptyLoop_int_elapsedTime = emptyLoop_int_elapsedTime;
		
				
		System.out.println("\nEnter # Threads: (1,2,4,8)");
	    Scanner in = new Scanner(System.in);
		total_Threads = in.nextInt();
		for(int i=1; i<=total_Threads ; i++)
		{
			cpuExec.thread_count = i;
			run[i-1] = new cpuExec();
			run[i-1].start();
			Thread.sleep(100);
		}		
		}
	}

	public static long emptyLoop_float_elapsedTime(){
		float a = (float)( new Random_Value().integer());
		Stopwatch St = new Stopwatch();
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			float c = 123.123f;			
		}
		return St.elapsedTime();		
	}
	
	public static long emptyLoop_int_elapsedTime(){
		int a = new Random_Value().integer();
		Stopwatch St = new Stopwatch();
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			int c = a;			
		}
		return St.elapsedTime();
	}
}

class Stopwatch {
	private final long start;
	public Stopwatch() {
		start = System.currentTimeMillis();
	}
	public long elapsedTime() {
		long now = System.currentTimeMillis();
		return (now - start);
	}
}

class Random_Value {
	public int integer() {
		int lower = 1;
		int upper = 100;
		Random rn = new Random();
		return (rn.nextInt(upper-lower) + lower);
	}
}

