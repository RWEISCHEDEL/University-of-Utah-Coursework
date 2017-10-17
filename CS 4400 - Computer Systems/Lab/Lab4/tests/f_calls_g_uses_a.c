extern int a[];

__attribute((__noinline__)) int g(int i)
{
  return a[i];
}

int f(int i) {
  return g(i);
}
