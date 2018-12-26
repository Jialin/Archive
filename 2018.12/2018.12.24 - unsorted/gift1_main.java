package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.HashMap;
import java.util.Map;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: gift1
*/
public class gift1_main {
  Map<String, Integer> nameLookup;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    String[] names = new String[n];
    nameLookup = new HashMap<>(n);
    for (int i = 0; i < n; ++i) {
      names[i] = in.next();
      nameLookup.put(names[i], i);
    }
    int[] res = new int[n];
    for (int i = 0; i < n; ++i) {
      int fromIdx = getIndex(in);
      int cost = in.nextInt();
      int m = in.nextInt();
      int piece = m > 0 ? cost / m : 0;
      int remain = cost - piece * m;
      res[fromIdx] -= cost - remain;
      for (int j = 0; j < m; ++j) {
        res[getIndex(in)] += piece;
      }
    }
    for (int i = 0; i < n; ++i) {
      int idx = nameLookup.get(names[i]);
      out.println(names[i], res[idx]);
    }
  }

  int getIndex(QuickScanner in) {
    return nameLookup.get(in.next());
  }
}
