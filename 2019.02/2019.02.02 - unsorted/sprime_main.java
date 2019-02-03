package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.LongUtils;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: sprime
*/
public class sprime_main {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    IntArrayList prev = new IntArrayList(4);
    prev.add(2);
    prev.add(3);
    prev.add(5);
    prev.add(7);
    IntArrayList next = new IntArrayList();
    for (int remN = in.nextInt(); remN > 1; --remN) {
      next.clear();
      for (int i = 0; i < prev.size; ++i) {
        int base = prev.get(i) * 10 + 1;
        for (int digit = 1; digit < 10; digit += 2, base += 2) {
          if (LongUtils.isPrime(base)) {
            next.add(base);
          }
        }
      }
      IntArrayList tmp = prev;
      prev = next;
      next = tmp;
    }
    for (int i = 0; i < prev.size; ++i) {
      out.println(prev.get(i));
    }
  }
}
