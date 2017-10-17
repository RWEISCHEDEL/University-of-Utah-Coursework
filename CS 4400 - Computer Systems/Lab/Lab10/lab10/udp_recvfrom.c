#include "csapp.h"

int main(int argc, char **argv) {
  char *portno;
  struct addrinfo hints, *addrs;
  char host[256], serv[32];
  int s;
  int counter = 0;

  if (argc != 2)
    app_error("need <port>");

  portno = argv[1];

  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET;        /* Request IPv4 */
  hints.ai_socktype = SOCK_DGRAM;   /* Accept UDP connections */
  hints.ai_flags = AI_PASSIVE;      /* ... on any IP address */
  Getaddrinfo(NULL, portno, &hints, &addrs);

  s = Socket(addrs->ai_family, addrs->ai_socktype, addrs->ai_protocol);
  Bind(s, addrs->ai_addr, addrs->ai_addrlen);
  Freeaddrinfo(addrs);

  while (1) {
    char buffer[MAXBUF];
    size_t amt;
    struct sockaddr_in from_addr;
    unsigned int from_len = sizeof(from_addr);
    amt = Recvfrom(s, buffer, MAXBUF, 0,
                   (struct sockaddr *)&from_addr, &from_len);
    Write(1, buffer, amt);
    Write(1, "\n", 1);

    Getnameinfo((struct sockaddr *)&from_addr, from_len,
                host, sizeof(host),
                serv, sizeof(serv),
                NI_NUMERICHOST | NI_NUMERICSERV);
    printf(" from %s:%s [%d]\n", host, serv, ++counter);
  }

  return 0;
}
