package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: milk3
*/
public class milk3_main {
  boolean[][][] visited;
  boolean[] answer;
  int[] size;
  int[] amount;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    size = new int[3];
    amount = new int[3];
    size[0] = in.nextInt();
    size[1] = in.nextInt();
    size[2] = in.nextInt();
    visited = new boolean[size[0] + 1][size[1] + 1][size[2] + 1];
    answer = new boolean[size[2] + 1];
    dfs(0, 0, size[2]);
    boolean first = true;
    for (int i = 0; i <= size[2]; ++i) if (answer[i]) {
      if (!first) {
        out.print(' ');
      }
      first = false;
      out.print(i);
    }
    out.println();
  }

  void dfs(int a, int b, int c) {
    if (visited[a][b][c]) {
      return;
    }
    visited[a][b][c] = true;
    if (a == 0) {
      answer[c] = true;
    }
    for (int i = 0; i < 3; ++i) for (int j = 0; j < 3; ++j) if (i != j) {
      amount[0] = a;
      amount[1] = b;
      amount[2] = c;
      if (amount[i] + amount[j] <= size[j]) {
        amount[j] += amount[i];
        amount[i] = 0;
      } else {
        amount[i] = amount[i] + amount[j] - size[j];
        amount[j] = size[j];
      }
      dfs(amount[0], amount[1], amount[2]);
    }
  }
}
