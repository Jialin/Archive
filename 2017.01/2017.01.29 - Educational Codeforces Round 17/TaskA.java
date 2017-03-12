package main;

import template.collections.list.LongArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.LongUtils;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    long n = in.nextLong();
    LongArrayList divisors = new LongArrayList(30000);
    LongUtils.divisors(n, divisors);
    int k = in.nextInt();
    out.println(k <= divisors.size ? divisors.kth(k - 1) : -1);
  }
}
