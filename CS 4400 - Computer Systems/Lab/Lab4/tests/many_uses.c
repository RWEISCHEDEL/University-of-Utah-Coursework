extern int a[];
int b[] = {1, 2, 3};
int c, d;

int e(int i);

int f(int i) {
  return e(a[b[i]]);
}

int g(int i) {
  c = i;
  return b[d];
}

__attribute((__noinline__)) static int j(int i)
{
  return a[i];
}

int h(int i) {
  d = i;
  return j(c);
}

__attribute((__noinline__)) int k(int i)
{
  return a[i];
}

int m(int i) {
  return k(i);
}
