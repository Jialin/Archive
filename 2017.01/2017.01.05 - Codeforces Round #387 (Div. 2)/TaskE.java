package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  static int MAXN = (1000000 >> 2) + 10;

  QuickScanner in;
  StringBuilder[] res = new StringBuilder[MAXN];
  boolean ended = false;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.in = in;
    for (int i = 0; i < MAXN; ++i) {
      res[i] = new StringBuilder();
    }
    while (!ended) {
      dfs(0);
    }
    int cnt;
    for (cnt = 0; res[cnt].length() > 0; ++cnt) {}
    out.println(cnt);
    for (int i = 0; i < cnt; ++i) {
      out.println(res[i].toString());
    }
  }

  void dfs(int depth) {
    StringBuilder sb = res[depth];
    if (sb.length() > 0) {
      sb.append(' ');
    }
    next(sb);
    for (int i = nextInt(); i > 0; --i) {
      dfs(depth + 1);
    }
  }

  void next(StringBuilder sb) {
    int c = in.nextChar();
    do {
      sb.appendCodePoint(c);
      c = in.nextChar();
    } while (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'));
  }

  int nextInt() {
    int res = 0;
    int c = in.nextChar();
    do {
      res = res * 10 + c - '0';
      c = in.nextChar();
    } while ('0' <= c && c <= '9');
    ended = c != ',';
    return res;
  }
}
