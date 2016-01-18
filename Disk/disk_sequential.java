import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class disk_sequential {

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			int block_size = (int)Math.pow(1024, i);
			diskSExec.buf_len = block_size;
			diskSExec.Exec();
			System.out.println("Testing Sequential R/W Speeds for Block Size	: " + block_size + " Bytes \n");
			diskSExec.result();
			System.out.println("		--------------		");
		}
	}
}

class diskSExec extends Thread {

	public static long buf_len;
	public static float[] average_write = new float[4];
	public static double[] average_read = new double[4];
	public static float[] average_latency = new float[4];
	public static float[] write_time = new float[4];
	public static double[] read_time = new double[4];
	public static float[] latency_time = new float[4];
	public static int thread_num;

	public static void Exec() throws InterruptedException {
		diskSExec[] run = new diskSExec[8];
		for (int j = 0; j < 4; j++) {
			int num_thread = (int)Math.pow(2, j);
			for (int i = 0; i < num_thread; i++) {
				run[i] = new diskSExec();
				run[i].start();
				run[i].thread_num = j;
				Thread.sleep(100);
			}
			diskSExec.average_write[j] = ((write_time[j] / num_thread)/ 1000000000)*(1048576/buf_len);
			diskSExec.average_read[j] = ((read_time[j] / num_thread)/ 1000000000)*(1048576/buf_len);
			diskSExec.average_latency[j] = (latency_time[j] / num_thread) / 1000000;
		}
	}

	public void run() {

		try {
			sWrite(diskSExec.buf_len);
			sRead();
			slatency();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sWrite(long buf_len) throws Exception {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < buf_len; i++) {
			buffer.append("X");
		}
		File file = new File("file.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(
				file.getAbsoluteFile()));
		long start = System.nanoTime();
		bw.write(buffer.toString());
		diskSExec.write_time[thread_num] = diskSExec.write_time[thread_num]
				+ (System.nanoTime() - start);
		bw.close();

	}

	public static void sRead() throws Exception {
		File file = new File("file.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		long start = System.nanoTime();
		fis.read(data);
		diskSExec.read_time[thread_num] = diskSExec.write_time[thread_num]
				+ (System.nanoTime() - start);
		fis.close();
	}

	public static void slatency() throws Exception {

		File file = new File("file.txt");
		RandomAccessFile access = new RandomAccessFile(file, "rw");
		long start = System.nanoTime();
		access.seek(file.length());
		float latency = System.nanoTime() - start;
		access.close();
		diskSExec.latency_time[thread_num] = diskSExec.latency_time[thread_num]
				+ latency;

	}

	public static void result() {
		for (int i = 0; i < 4; i++) {
			System.out.println("T#" +(int)Math.pow(2, i) + " Write Speed  in Mbps	: " + (1 / diskSExec.average_write[i]));
			System.out.println("T#" +(int)Math.pow(2, i) + " Read  Speed  in Mbps	: " + (1 / diskSExec.average_read[i]));
			System.out.println("T#" +(int)Math.pow(2, i) + " Latency Time in mSec	: " + diskSExec.average_latency[i] + "\n");
		}
	}
}
