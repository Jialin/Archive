package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int usb, ps2, both;
  long resCost;

  IntArrayList usbCosts, ps2Costs;
  char[] token = new char[16];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    usb = in.nextInt();
    ps2 = in.nextInt();
    both = in.nextInt();
    int m = in.nextInt();
    usbCosts = new IntArrayList(m);
    ps2Costs = new IntArrayList(m);
    for (int i = 0; i < m; ++i) {
      int cost = in.nextInt();
      in.next(token);
      (token[0] == 'U' ? usbCosts : ps2Costs).add(cost);
    }
    ps2Costs.sort();
    ps2Costs.reverse();
    usbCosts.sort();
    usbCosts.reverse();
    resCost = 0;
    int res = calc(usb, usbCosts) + calc(ps2, ps2Costs);
    res += calcBoth();
    out.println(res, resCost);
  }

  int calc(int cnt, IntArrayList costs) {
    int res = 0;
    for (int i = 0; i < cnt && !costs.isEmpty(); ++i) {
      ++res;
      resCost += costs.pollLast();
    }
    return res;
  }

  int calcBoth() {
    int res = 0;
    for (int i = 0; i < both && (!usbCosts.isEmpty() || !ps2Costs.isEmpty()); ++i) {
      ++res;
      if (usbCosts.isEmpty()) {
        resCost += ps2Costs.pollLast();
      } else if (ps2Costs.isEmpty()) {
        resCost += usbCosts.pollLast();
      } else if (usbCosts.peekLast() < ps2Costs.peekLast()) {
        resCost += usbCosts.pollLast();
      } else {
        resCost += ps2Costs.pollLast();
      }
    }
    return res;
  }
}
