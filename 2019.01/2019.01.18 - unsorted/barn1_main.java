package main;

import template.array.IntArrayUtils;
import template.collections.priorityqueue.IntPriorityQueue;
import template.collections.top.IntTopValues;
import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: barn1
*/
public class barn1_main {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int boardCnt = in.nextInt();
    in.nextInt();
    int cowCnt = in.nextInt();
    int[] cows = in.nextInt(cowCnt);
    IntArrayUtils.sort(cows);
    IntTopValues gaps = IntTopValues.topMax(boardCnt - 1);
    for (int i = 1; i < cowCnt; ++i) {
      gaps.add(cows[i] - cows[i - 1] - 1);
    }
    int res = cows[cowCnt - 1] - cows[0] + 1;
    IntPriorityQueue maxGaps = gaps.heap();
    for ( ; maxGaps.isNotEmpty(); res -= maxGaps.poll()) {}
    out.println(res);
  }
}