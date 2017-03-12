package main;

import template.graph.OneRegularGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;

import static template.numbertheory.IntUtils.mul;
import static template.numbertheory.IntUtils.pow;
import static template.numbertheory.IntUtils.sub;

public class TaskD {
  int n;
  OneRegularGraph graph;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    graph = new OneRegularGraph(n);
    graph.init(n);
    for (int i = 0; i < n; ++i) {
      graph.add(i, in.nextInt() - 1);
    }
    graph.calc();
    int res = 1;
    for (int i = 0; i < graph.compCnt; ++i) {
      int a = graph.coreSize[i], b = graph.compSize[i] - a;
      res = mul(
          res,
          mul(
              sub(pow(2, a), 2),
              pow(2, b)));
    }
    out.println(res);
  }
}
