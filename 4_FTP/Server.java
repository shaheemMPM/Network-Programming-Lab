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

			System.out.println("Server["+user_name+"] started running on port "+port);
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

			System.out.println("client["+client_name+"] connected");

			String line = "";
			String fileName = "";
			String fileContent ="";

			// reads message from client until "Over" is sent
			while (true){
				try{
					line = in.readUTF();
					if (line.equals("Over")) {
						System.out.println("Connection closed by Client!!!");
						break;
					}
					fileName = line;
					// open file with fileName and read content and write it to out
					try {
						File myObj = new File("./ServerFiles/"+fileName);
			      Scanner myReader = new Scanner(myObj);
			      while (myReader.hasNextLine()) {
			        String currentLine = myReader.nextLine();
			        fileContent += (currentLine+"\n");
			      }
			      myReader.close();
					}catch (FileNotFoundException e) {
						fileContent = "No File with given fileName";
					}
          out.writeUTF(fileContent);
				}catch(EOFException eo){
					System.out.println("Seems like the Connection is closed by the Client["+client_name+"]");
					break;
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
