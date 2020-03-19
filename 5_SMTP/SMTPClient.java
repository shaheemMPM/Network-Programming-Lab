// Java implementation for multithreaded chat client 
// Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.*; 

public class SMTPClient 
{ 
	final static int ServerPort = 1234; 

	public static void main(String args[]) throws UnknownHostException, IOException 
	{ 
        Scanner scn = new Scanner(System.in); 
        //To stop the running threads
	
		// getting localhost ip 
		InetAddress ip = InetAddress.getByName("localhost"); 
		
		// establish the connection 
		Socket s = new Socket(ip, ServerPort); 
		
		// obtaining input and out streams 
		DataInputStream dis = new DataInputStream(s.getInputStream()); 
		DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

		// sendMessage thread 
		Thread sendMessage = new Thread(new Runnable() 
		{ 
            @Override
            public void run() { 
				System.out.println("Enter your SMTP address(someone@mail.com)");
				String address = scn.nextLine(); 
				try{
					dos.writeUTF(address);
				}
				catch (IOException e) { 
					e.printStackTrace(); 
				} 
				while (true) { 
					try { 
						System.out.println("Enter name of recipient of message(logout to exit)");
                        String recipient = scn.nextLine(); 
                        if(recipient.equals("logout")){
                            dos.writeUTF("logout");
                            System.exit(0);
                        }
                        else{
                            System.out.println("Enter subject of message");
                            String subject = scn.nextLine();
                            dos.writeUTF(subject + "#" + recipient); 
                        }
						
					} catch (IOException e) { 
						//e.printStackTrace(); 
					} 
				} 
			} 
		}); 
		
		// readMessage thread 
		Thread readMessage = new Thread(new Runnable() 
		{ 
			@Override
			public void run() { 

				while (true) { 
					try { 
						// read the message sent to this client 
                        String received = dis.readUTF(); 
                        try{
                            StringTokenizer st = new StringTokenizer(received, "#");
                            String MsgToSend = st.nextToken(); 
                            String sender = st.nextToken();
                            System.out.println("New message from " + sender );
                            System.out.println(MsgToSend); 
                            System.out.println("Enter name of recipient of message(logout to exit)");
                        }
                        catch(NoSuchElementException e){
                            System.out.println(received);
                            System.out.println("Enter name of recipient of message(logout to exit)");
                        }
					} catch (IOException e) { 
                        //e.printStackTrace(); 
                    } 
                } 
			} 
		}); 

		sendMessage.start(); 
		readMessage.start(); 
	} 
} 
