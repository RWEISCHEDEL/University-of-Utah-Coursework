extern int a[];

__attribute((__noinline__)) static int e(int i)
{
  return a[i];
}

int f(int i) {
  return e(i);
}
