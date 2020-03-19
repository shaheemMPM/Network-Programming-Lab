## FTP Client Server Communication over TCP
![dependencies](https://camo.githubusercontent.com/cdc54d1641f5e11e246a2707063ecad092c96d11/68747470733a2f2f64617669642d646d2e6f72672f6477796c2f657374612e737667)

Implemented using Java Sockets

### Usage

Run Server and Client programs in separate machines or separate terminals

#### Server

```html
javac Server.java
java Server

Enter user name : [username_of_server]
Server[username_of_server] started running on port 3000
Waiting for a client ...
```

#### Client
```html
javac Client.java
java Client

Enter user name : [client_username]
Enter IP of Host (127.0.0.1 for localhost) : [ip_of_host_machine]
Enter port number of Host : [program_runnig_port]
Server[username_of_server] connection established
```

### Todos

 - Create a folder named ClientFiles if not exists
 - Create a folder named ServerFiles if not exists
 - Add Necessary files to ServerFiles folder

### Preview

![example of run](./exm.png)
