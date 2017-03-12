package main;

import template.graph.basic.DirectedGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskC {
  static int MAXN = 10000;
  static Pattern PATTERN = Pattern.compile("(.*)=.*[(](.*)[)]");

  DirectedGraph graph;
  Map<String, Integer> idxMap;
  Queue<Integer> q;
  int[] remOutDegree;
  boolean[] known;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    graph = new DirectedGraph(MAXN, MAXN);
    known = new boolean[MAXN];
    q = new ArrayBlockingQueue<>(MAXN);
    remOutDegree = new int[MAXN];
    idxMap = new TreeMap<>();
    for (int i = in.nextInt(); i > 0; --i) {
      String line = in.nextLine();
      Matcher match = PATTERN.matcher(line);
      if (match.find()) {
        int fromIdx = calcIdx(match.group(1));
        String list = match.group(2);
        if (list.isEmpty()) {
          known[fromIdx] = true;
        } else {
          for (String s : list.split(",")) {
            int toIdx = calcIdx(s);
            graph.add(fromIdx, toIdx);
          }
        }
      } else {
        throw new IllegalArgumentException();
      }
    }
    for (int i = 0; i < MAXN; ++i) {
      remOutDegree[i] = graph.outDegree(i);
      if (known[i]) {
        q.add(i);
      }
    }
    int cnt = 0;
    for ( ; !q.isEmpty(); ++cnt) {
      int u = q.poll();
//      System.out.printf("u:%d\n", u);
      for (int edgeIdx = graph.lastIn(u); edgeIdx >= 0; edgeIdx = graph.nextIn(edgeIdx)) {
        int v = graph.fromIdx(edgeIdx);
        --remOutDegree[v];
        if (remOutDegree[v] == 0) {
          q.add(v);
        }
      }
    }
//    System.out.printf("cnt:%d\n", cnt);
    out.printf("Case #%d: %s\n", taskIdx, cnt == idxMap.size() ? "GOOD" : "BAD");
  }

  int calcIdx(String s) {
    if (idxMap.containsKey(s)) {
      return idxMap.get(s);
    }
    int res = idxMap.size();
    idxMap.put(s, res);
    return res;
  }
}
