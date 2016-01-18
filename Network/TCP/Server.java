import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server implements Runnable {
    private static ServerSocket server;
    private Socket sock;
	public static void main(String[] args) {
        try {
            System.out.println("Server Input/Output initiating...");
            server = new ServerSocket(1111); 
            //server.setSoTimeout(10*1000);	
            while (true) {
                Socket connection = server.accept();
                if (connection != null) {
                	Server sv = new Server();
                	sv.sock=connection;
                    new Thread(sv).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public void run() {
        System.out.println("Server client connection establised successfully \n");
        try {
            InputStream is = sock.getInputStream();
            OutputStream os = sock.getOutputStream();
            File output = new File("Files/FUpload.txt");
            FileOutputStream fos = new FileOutputStream(output);
            byte[] data = new byte[1 * 1024];
            int len = 0;
			System.out.println("Initiating Server to Client communication");		
            Stopwatch stopwatch1 = new Stopwatch();
            int readtimeout = 2 * 1000;
            sock.setSoTimeout(readtimeout);
            try {
                while ((len = is.read(data)) > 0) {
                    fos.write(data, 0, len);
                    fos.flush();
                }
                fos.close();
            } catch (Exception e) {
            }
            long end = System.currentTimeMillis();
            System.out.println("Network I / O Transfer Time	:	" + (stopwatch1.elapsedTime())+ " ms");
            System.out.println("Client to server upload completed! \n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Initiating Client to Server communication");
            Stopwatch stopwatch2 = new Stopwatch();
            FileInputStream fis = new FileInputStream(output);
            len = 0;
            while ((len = fis.read(data)) > 0) {
                os.write(data, 0, len);
                os.flush();
            }
            fis.close();
            os.close();
            sock.close();
            System.out.println("Network I / O Transfer Time	:	" + (stopwatch2.elapsedTime())+ " ms");
			System.out.println("Server to Client upload completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
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