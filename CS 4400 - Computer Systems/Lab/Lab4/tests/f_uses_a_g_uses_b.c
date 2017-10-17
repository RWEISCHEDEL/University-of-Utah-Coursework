extern int a[];
int b[] = {1, 2, 3};

int f(int i) {
  return a[i];
}

int g(int i) {
  b[i] = i;
  return i;
}
