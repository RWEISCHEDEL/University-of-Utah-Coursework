extern int a[];
int b[] = {1, 2, 3};

int f(int i) {
  return a[b[i]];
}

int g(int i) {
  return b[a[i]];
}
