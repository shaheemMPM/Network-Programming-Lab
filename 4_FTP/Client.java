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
		String fileName = "";

		// keep reading until "Over" is input
		while (true){
			try{
        System.out.print("Required File name with extension (Over for Closing connection) : ");
				line = input.nextLine();
        out.writeUTF(line);
        if (line.equals("Over")) {
          System.out.println("Connection closing....");
          break;
        }
				fileName = line;
        line = in.readUTF();
        if (line.equals("Over")) {
          System.out.println("Connection Closed by server!!!");
          break;
        }else{
					// Here the coming value is the content of file
					// Open file with fileName and write line to it
					try {
						FileWriter myWriter = new FileWriter("./ClientFiles/"+fileName);
			      myWriter.write(line);
			      myWriter.close();
			      System.out.println("\nSuccessfully Downloaded the File");
					}catch (IOException e) {
						System.out.println(e);
					}
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
    System.out.print("Enter port number of Host : ");
    port = scanner.nextInt();
		Client client = new Client(ip, port);
	}
}
