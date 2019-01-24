package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: milk
*/
public class milk_main {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int remAmount = in.nextInt();
    int n = in.nextInt();
    int[] prices = new int[n];
    int[] amounts = new int[n];
    in.nextInts(n, prices, amounts);
    IntArrayUtils.sort(prices, amounts);
    int res = 0;
    for (int i = 0; i < n; ++i) {
      int buyAmount = Math.min(remAmount, amounts[i]);
      res += prices[i] * buyAmount;
      remAmount -= buyAmount;
    }
    out.println(res);
  }
}
