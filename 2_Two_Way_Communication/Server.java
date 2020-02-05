// A Java program for a Server
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server{
	//initialize socket and input stream
	private Socket		 socket = null;
	private DataOutputStream out	 = null;
	private ServerSocket server = null;
	private DataInputStream in	 = null;
  public static String client_name, user_name;

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

      // takes input from terminal
			Scanner input = new Scanner(System.in);

			// sends output to the socket
			out = new DataOutputStream(socket.getOutputStream());

			out.writeUTF(user_name);

      client_name = in.readUTF();

			System.out.println(client_name+"[client] connected");

			String line = "";

			// reads message from client until "Over" is sent
			while (true){
				try{
					line = in.readUTF();
					if (line.equals("Over")) {
						System.out.println("Connection closed by Client!!!");
						break;
					}
					System.out.println(client_name+" : "+line);
          System.out.print("me : ");
          line = input.nextLine();
          out.writeUTF(line);
					if (line.equals("Over")) {
						System.out.println("Connection closing...");
						break;
					}
				}catch(IOException i){
					System.out.println(i);
				}
			}

			// close connection
			socket.close();
			in.close();
			input.close();
		}catch(IOException i){
			System.out.println(i);
		}
	}

	public static void main(String args[]){
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter user name : ");
    user_name = scanner.nextLine();
		Server server = new Server(3000);
	}
}
