
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable {
	static long FileSize;
	static Socket sock;
	static File input;
	private int ThreadNumber;
	
	public static void main(String args[]) {
		System.out.println("Enter # Threads: (1,2)");
		Scanner in = new Scanner(System.in);
		int total_Threads = in.nextInt();
		in.close();
		System.out.println("\nStarting " + total_Threads + " Client Connections");
		
		System.out.println("Client I/O initiating...");
		try {
			sock = new Socket("localhost", 1111);
		} catch (Exception e) {
		}
		System.out.println("Client connected to server successfully!");
		input = new File("Files/file.txt");
		FileSize = input.length();
		System.out.println("Size of the file		:	" + FileSize + " Byte(s) \n");
				
		for (int i = 1; i <= total_Threads; i++) {
			Client ct = new Client();
			ct.ThreadNumber = i;
			new Thread(ct).start();
		}
	}
	
	public void run() {
		try {			
			getUploadSpeed(input,ThreadNumber);
			Thread.sleep(3000);
			getDownloadSpeed(ThreadNumber);		
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	
	
	public static void getUploadSpeed(File input, int TNumber){
		try {
		OutputStream os = sock.getOutputStream();
		FileInputStream fis = new FileInputStream(input);
		byte[] data = new byte[1 * 1024];
		int len = 0;
		System.out.println("T# "+TNumber+" File uploading started");
		Stopwatch stopwatch = new Stopwatch();
		while ((len = fis.read(data)) > 0) {
			os.write(data, 0, len);
			os.flush();
		}
		fis.close();
		long tTime = stopwatch.elapsedTime();
		float Speed = getSpeed(tTime, FileSize);		
		System.out.println("T# "+TNumber+" finised uploading the file to the server");
		System.out.println("T# "+TNumber+" Time taken for upload	:	" + tTime + " MilliSecs");
		System.out.println("T# "+TNumber+" Avg upload speed of	:	" + Speed + " Mbps");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getDownloadSpeed(int TNumber){
		try {
		InputStream is = sock.getInputStream();		
		File output = new File("Files/FDownload.txt");
		FileOutputStream fos = new FileOutputStream(output);
		int len = 0;
		byte[] data = new byte[1 * 1024];
		System.out.println("T# "+TNumber+" File downloading started");
		Stopwatch stopwatch = new Stopwatch();
		while ((len = is.read(data)) > 0) {
			fos.write(data, 0, len);
			fos.flush();
		}
		fos.close();
		is.close();
		sock.close();
		long tTime = stopwatch.elapsedTime();
		float Speed = getSpeed(tTime, FileSize);
		System.out.println("T# "+TNumber+" finised dowanloading the file from server");
		System.out.println("T# "+TNumber+" Time taken for download	:	" + tTime + " MilliSecs");
		System.out.println("T# "+TNumber+" Avg download speed of	:	" + Speed + " Mbps");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static float getSpeed(long msec, long size){	
		float mb = (float)size/1048576;
		float seconds = (float)msec/1000;
		return mb/seconds;		
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
