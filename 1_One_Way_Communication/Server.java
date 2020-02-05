// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server{
	//initialize socket and input stream
	private Socket		 socket = null;
	private ServerSocket server = null;
	private DataInputStream in	 = null;
  public static String client_name;

	// constructor with port
	public Server(int port){
		// starts server and waits for a connection
		try{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();

			// takes input from the client socket
			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));

      client_name = in.readUTF();

			System.out.println(client_name+"[client] connected");

			String line = "";

			// reads message from client until "Over" is sent
			while (!line.equals("Over")){
				try{
					line = in.readUTF();
					System.out.println(client_name+" : "+line);

				}catch(IOException i){
					System.out.println(i);
				}
			}
			System.out.println("Closing connection");

			// close connection
			socket.close();
			in.close();
		}catch(IOException i){
			System.out.println(i);
		}
	}

	public static void main(String args[]){
		Server server = new Server(3000);
	}
}
