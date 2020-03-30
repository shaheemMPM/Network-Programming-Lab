# Experiment 11 (a) - Display Header Informations

Submitted By : Mohammed Shaheem P (45) <br>
Repository : [@HeaderInfo](https://github.com/shaheemMPM/Network-Programming-Lab/tree/master/6_Header_Info_of_TCP_and_UDP)

Aim: Configure the following services in the network- FTP server, Web server, File server - Implementation and Demonstration. Download, install and run any of the leading open-source FTP server, Web server and File server Expected output: The interaction messages and screenshots between servers (Web server and FTP server) using a web browser and a File server

## Programs

### Webserver
```zsh
python3 -m http.server
```
### Fileserver
```zsh
brew install vsftpd
```

### FtpClient.c

```c
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include<netinet/in.h>
#include<time.h>
#define PORT_FTP        21              /* FTP connection port */
#define SERVER_ADDR     "192.168.1.6"     /* localhost */
#define MAXBUF          1024

int main()
{   int sockfd;
    struct sockaddr_in dest;
    char buffer[MAXBUF];

    /*---Open socket for streaming---*/
    if ( (sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0 )
    {
        perror("Socket");
        exit(errno);
    }

    /*---Initialize server address/port struct---*/
    bzero(&dest, sizeof(dest));
    dest.sin_family = AF_INET;
    dest.sin_port = htons(PORT_FTP);
    if ( inet_aton(SERVER_ADDR, &dest.sin_addr.s_addr) == 0 )
    {
        perror(SERVER_ADDR);
        exit(errno);
    }

    /*---Connect to server---*/
    if ( connect(sockfd, (struct sockaddr*)&dest, sizeof(dest)) != 0 )
    {
        perror("Connect ");
        exit(errno);
    }

    /*---Get "Hello?"---*/
    bzero(buffer, MAXBUF);
    recv(sockfd, buffer, sizeof(buffer), 0);
    printf("%s", buffer);

    /*---Clean up---*/
    close(sockfd);
    return 0;
}
```

## Outputs

### Screenshots

#### webserver

![webserver terminal](https://raw.githubusercontent.com/shaheemMPM/Network-Programming-Lab/master/8_FTP_Client/term1.png)

#### Browser

![webserver browser](https://raw.githubusercontent.com/shaheemMPM/Network-Programming-Lab/master/8_FTP_Client/browser1.png)

#### Directory

![webserver finder](https://raw.githubusercontent.com/shaheemMPM/Network-Programming-Lab/master/8_FTP_Client/finder1.png)

#### File Server (Host & Client)

![webserver browser](https://raw.githubusercontent.com/shaheemMPM/Network-Programming-Lab/master/8_FTP_Client/term2.png)

### Output File

#### webserver

```zsh
127.0.0.1 - - [30/Mar/2020 16:22:35] "GET / HTTP/1.1" 304 -
127.0.0.1 - - [30/Mar/2020 16:22:35] "GET /scripts/scripts.js HTTP/1.1" 304 -
127.0.0.1 - - [30/Mar/2020 16:22:35] "GET /styles/style.css HTTP/1.1" 304 -
127.0.0.1 - - [30/Mar/2020 16:22:45] "GET /scripts/scripts.js HTTP/1.1" 304 -
127.0.0.1 - - [30/Mar/2020 16:22:45] "GET / HTTP/1.1" 304 -
127.0.0.1 - - [30/Mar/2020 16:22:45] "GET /styles/style.css HTTP/1.1" 304 -
```
#### File Server (Host & Client)

```zsh
shaheem@Mohammeds-Air 8_FTP_Client % brew services start pure-ftpd
==> Successfully started `pure-ftpd` (label: homebrew.mxcl.pure-ftpd)
shaheem@Mohammeds-Air 8_FTP_Client % ./a.out
220---------- Welcome to Pure-FTPd [privsep] [TLS] ----------
220-You are user number 1 of 50 allowed.
220-Local time is now 16:37. Server port: 21.
220-IPv6 connections are also welcome on this server.
220 You will be disconnected after 15 minutes of inactivity.
shaheem@Mohammeds-Air 8_FTP_Client % brew services stop pure-ftpd
Stopping `pure-ftpd`... (might take a while)
==> Successfully stopped `pure-ftpd` (label: homebrew.mxcl.pure-ftpd)
```

## Readme

### Todos

#### webserver

1. Place the html files to be hosted in a single directory named webserver.
2. Navigate to webserver and run the following command: <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; python3 -m http.server
3. Open 127.0.0.1:8000 and it will display the webpage from that folder.

#### File Server

1. Install File Server in MacOS using Command<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; brew install pure-ftpd
2. Run File Server using Command<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; brew services start pure-ftpd
3. In FtpClient.c add your machines ip (line 8)
4. Compile FtpClient.c<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; gcc FtpClient.c
5. Run FtpClient.c<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ./a.out
