#include "csapp.h"

int main(int argc, char **argv){
    char *hostname, *portno;
    struct addrinfo hints, *addrs;
    char host[256], serv[32];
    int s;
    size_t amt;

    if(argc != 3){
        app_error("need stuff");
    }

    hostname = argv[1];
    portno = argv[2];

    memset(&hints, 0, sizeof(struct addrinfo));
    hints.ai_family = AF_INET;
    hints.ai_socktype = SOCK_DGRAM;
    Getaddrinfo(hostname, portno, &hints, &addrs);

    Getnameinfo(addrs->ai_addr, addrs->addrlen, host, sizeof(host), serv, sizeof(serv), NI_NUMERICHOST | NI_NUMERICSERV);

    printf("sending to %s:%s\n", host, serv);

    s = Socket(addrs->ai_family, addrs->ai_socktype, addrs->ai_protocol);
    amt = Sendto(s, argv[3], strlen(argv[3]), 0, addrs->ai_addr, addrs->ai_addrlen);
    Freeaddrinto(addrs);


}
