import java.io.*;
import java.net.*;

public class Client {
    private DatagramSocket socket = null;
    private FileEvent event = null;
    private String sourceFilePath = "file.txt";
    private String destinationPath = "Files/";
    private String hostName = "localHost";
    private int portNumber = 3478;

    public Client() {

    }

    public void createConnection() {
        try {
			System.out.println("Client I/O initiating...");
            socket = new DatagramSocket();
		
            InetAddress IPAddress = InetAddress.getByName(hostName);
            byte[] incomingData = new byte[1024];
            event = getFileEvent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(event);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, portNumber);
			
			System.out.println("Size of the file : 10000 Bytes \n");
            long start = System.nanoTime();
            socket.send(sendPacket);
            long now = System.nanoTime();
            long ttime = now - start;
    		
            System.out.println("File sent from client");
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            socket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());
            System.out.println("Response from server:" + response.trim());
            System.out.println("Time Take for 1 Thread upload	: "+ttime+" nanosec");
    		float speed = 10000000/ttime;
    		System.out.println("Bandwidth for 1 Thread upload	: "+speed+" Mbps");
            Thread.sleep(2000);
            System.exit(0);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FileEvent getFileEvent() {
        FileEvent fileEvent = new FileEvent();
        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath);
        fileEvent.setFilename(fileName);
        fileEvent.setSourceDirectory(sourceFilePath);
        File file = new File(sourceFilePath);
        if (file.isFile()) {
            try {
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }
                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Success");
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {
            System.out.println("path specified is not pointing to a file");
            fileEvent.setStatus("Error");
        }
        return fileEvent;
    }
    
    public static void main(String[] args) {
        Client client = new Client();
        client.createConnection();
    }
}
