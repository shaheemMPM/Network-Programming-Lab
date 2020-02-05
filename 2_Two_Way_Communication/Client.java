// A Java program for a Client
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client{
	// initialize socket and input output streams
	private Socket socket		 = null;
	private Scanner input = null;
	private DataOutputStream out	 = null;
	private DataInputStream in	 = null;
  public static String user_name, server_name="";

	// constructor to put ip address and port
	public Client(String address, int port){
		// establish a connection
		try{
			socket = new Socket(address, port);

      // takes input from the client socket
			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));

      server_name = in.readUTF();

			System.out.println("Server["+server_name+"] connection established");

			// takes input from terminal
			input = new Scanner(System.in);

			// sends output to the socket
			out = new DataOutputStream(socket.getOutputStream());

      // send username
      out.writeUTF(user_name);
		}catch(UnknownHostException u){
			System.out.println(u);
		}catch(IOException i){
			System.out.println(i);
		}

		// string to read message from input
		String line = "";

		// keep reading until "Over" is input
		while (true){
			try{
        System.out.print("me : ");
				line = input.nextLine();
        out.writeUTF(line);
        if (line.equals("Over")) {
          System.out.println("Connection closing...");
          break;
        }
        line = in.readUTF();
        if (line.equals("Over")) {
          System.out.println("Connection Closed by server!!!");
          break;
        }else{
          System.out.println(server_name+" : "+line);
        }
			}catch(IOException i){
				System.out.println(i);
			}
		}

		// close the connection
		try{
			input.close();
			out.close();
			socket.close();
		}catch(IOException i){
			System.out.println(i);
		}
	}

	public static void main(String args[]){
    String ip;
    int port;
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter user name : ");
    user_name = scanner.nextLine();
    System.out.print("Enter IP of Host : ");
    ip = scanner.nextLine();
    System.out.print("Enter port number : ");
    port = scanner.nextInt();
		Client client = new Client(ip, port);
	}
}
