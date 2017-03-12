package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.math.BigInteger;

public class TaskD {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    out.println(calc(in.nextInt()));
  }

  int calc(int n) {
    if (n == 2) return 1;
    if (BigInteger.valueOf(n).isProbablePrime(100)) return 1;
    if (BigInteger.valueOf(n - 2).isProbablePrime(100)) return 2;
    return (n & 1) > 0 ? 3 : 2;
  }
}
