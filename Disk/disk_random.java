import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class disk_random {

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			int block_size = (int)Math.pow(1024, i);
			diskRExec.buf_len = block_size;
			diskRExec.Exec();
			System.out.println("Testing Random R/W Speeds for Block Size	: " + block_size + " Bytes \n");
			diskRExec.result();
			System.out.println("		--------------		");
		}
	}
}

class diskRExec extends Thread {
	public static long buf_len;
	public static float[] average_write = new float[4];
	public static double[] average_read = new double[4];
	public static float[] average_latency = new float[4];
	public static float[] write_time = new float[4];
	public static double[] read_time = new double[4];
	public static float[] latency_time = new float[4];
	public static int thread_num;

	public static void Exec() throws InterruptedException {
		diskRExec[] run = new diskRExec[8];
		for (int j = 0; j < 4; j++) {
			int num_thread = (int)Math.pow(2, j);
			for (int i = 0; i < num_thread; i++) {
				run[i] = new diskRExec();
				run[i].start();
				run[i].thread_num = j;
				Thread.sleep(100);
			}
			diskRExec.average_write[j] = ((write_time[j] / num_thread)/ 1000000000)*(1048576/buf_len);
			diskRExec.average_read[j] = ((read_time[j] / num_thread)/ 1000000000)*(1048576/buf_len);
			diskRExec.average_latency[j] = (latency_time[j] / num_thread) / 1000000;
		}
	}

	public void run() {

		try {
			rWrite(diskRExec.buf_len);
			rWrite();
			rlatency();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void rWrite(long buf_len) throws Exception {
		
		File file = new File("file.txt");
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        accessFile.seek(file.length());
        if(buf_len==1)
        {
        	for(int i=0;i<100;i++)
    	    {	
            	accessFile.write('X');
    	    }
        }
        else
        {
        	for(int i=0;i<buf_len;i++)
    	    {	
            	accessFile.write('X');
    	    }
        }
        long start = System.nanoTime();
        for(int i=0;i<buf_len;i++)
	    {	
        	accessFile.seek((file.length()+i)-buf_len);
        	accessFile.write('a');
	    }
        diskRExec.write_time[thread_num] = diskRExec.write_time[thread_num]
				+ (System.nanoTime() - start);
        accessFile.close();      

	}

	public static void rWrite() throws Exception {		
		File file = new File("file.txt");
        RandomAccessFile accessFile = new RandomAccessFile(file, "r");
        accessFile.seek(file.length());
        byte[] data = new byte[1];
	    long start = System.nanoTime();
	    for(int i=0;i<buf_len;i++)
	    {	
        	accessFile.seek((file.length()+i)-buf_len);
        	accessFile.read(data);
	    }
	    diskRExec.read_time[thread_num] = diskRExec.read_time[thread_num]
				+ (System.nanoTime() - start);
	    accessFile.close();
	}

	public static void rlatency() throws Exception {
		File file = new File("file.txt");
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        long start = System.nanoTime();
        for(int i=0;i<buf_len;i++)
	    {	
        	accessFile.seek((file.length()+i)-buf_len);
	    }
        float latency = System.nanoTime() - start;
        accessFile.close();
        diskRExec.latency_time[thread_num] = diskRExec.latency_time[thread_num]
				+ (latency/buf_len);

	}

	public static void result() {
		for (int i = 0; i < 4; i++) {
			System.out.println("T#" +(int)Math.pow(2, i) + " Write Speed  in Mbps	: " + (1 / diskRExec.average_write[i]));
			System.out.println("T#" +(int)Math.pow(2, i) + " Read  Speed  in Mbps	: " + (1 / diskRExec.average_read[i]));
			System.out.println("T#" +(int)Math.pow(2, i) + " Latency Time in mSec	: " + diskRExec.average_latency[i] + "\n");
		}
	}
}
