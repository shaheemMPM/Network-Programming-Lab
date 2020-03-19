import java.io.*; 
import java.util.*; 
import java.util.concurrent.*;
import java.net.*; 
  
// Server class 
public class SMTPServer  
{ 
  
    // Vector to store active clients 
    static Vector<ClientHandler> ar = new Vector<>(); 
      
    // counter for clients 
    static int i = 0; 
  
    public static void main(String[] args) throws IOException  
    { 
        // server is listening on port 1234 
        ServerSocket ss = new ServerSocket(1234); 
          
        Socket s; 
        ArrayBlockingQueue<MessageQueue> queue = new ArrayBlockingQueue<MessageQueue>(10); 
        // running infinite loop for getting 
        // client request 
        while (true)  
        { 
            // Accept the incoming request 
            s = ss.accept(); 
  
            System.out.println("New client request received : " + s); 
              
            // obtain input and output streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
              
            System.out.println("Creating a new handler for this client..."); 
  
            // Create a new handler object for handling this request. 
            ClientHandler mtch = new ClientHandler(s, dis, dos,queue); 
  
            // Create a new Thread with this object. 
            Thread t = new Thread(mtch); 
              
            System.out.println("Adding this client to active client list"); 
  
            // add this client to active clients list 
            ar.add(mtch); 
  
            // start the thread. 
            t.start(); 
  
            // increment i for new client. 
            // i is used for naming only, and can be replaced 
            // by any naming scheme 
            i++; 
  
        } 
    } 
} 
// ClientHandler class 
class ClientHandler implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    Socket s; 
    boolean isloggedin; 
    ArrayBlockingQueue<MessageQueue> queue;
      
    // constructor 
    public ClientHandler(Socket s, 
                            DataInputStream dis, DataOutputStream dos,ArrayBlockingQueue<MessageQueue> queue) { 
        this.dis = dis; 
        this.dos = dos;  
        this.s = s; 
        this.isloggedin=true; 
        this.queue = queue;
    } 
  
    @Override
    public void run() { 
        
        String received; 
        try{
            //reading the address
            this.name = dis.readUTF(); 
        }
        catch (IOException e) { 
                  
            e.printStackTrace(); 
        } 
        while (true)  
        { 
            try
            { 
                for (MessageQueue q : queue) {
                    if (q.name.equals(name)) {
                        System.out.println(q);
                      dos.writeUTF(q.message + "#" + q.sender);
                      queue.remove(q);
                    }
                  }
                // receive the string 
                received = dis.readUTF(); 
                System.out.println(received); 
                if(received.equals("logout")){ 
                    this.isloggedin=false; 
                    this.s.close(); 
                    break; 
                } 
                try{
                    // break the string into message and recipient part 
                    StringTokenizer st = new StringTokenizer(received, "#"); 
                    String MsgToSend = st.nextToken(); 
                    String recipient = st.nextToken(); 
                    boolean found = false;
                    // search for the recipient in the connected devices list. 
                    // ar is the vector storing client of active users 
                    for (ClientHandler mc : SMTPServer.ar)  
                    { 
                        // if the recipient is found, write on its 
                        // output stream 
                        if (mc.name.equals(recipient) && mc.isloggedin==true)  
                        { 
                            mc.dos.writeUTF(MsgToSend + "#" + this.name);
                            found = true; 
                            break; 
                        } 
                    }
                    if( found == false){
                        dos.writeUTF("Recipient not online, will retry later");
                        MessageQueue q = new MessageQueue();
                        q.name = recipient;
                        q.sender = name;
                        q.message = MsgToSend;
                        queue.add(q);
                    } 
                }
                catch(NoSuchElementException e){
                    System.out.println(received);
                    System.out.println("Enter name of recipient of message");
                }
                
            } catch (IOException e) { 
                  
                //e.printStackTrace(); 
            } 
              
        } 
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            //e.printStackTrace(); 
        } 
    } 
} 
class MessageQueue{
    public String name;
    public String sender;
    public String message;
}