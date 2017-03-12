package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskF {
  int n, m, k;
  char[][] board;
  char[] token = new char[2];

  int[] a, b, c, d, letter;
  long[][][] cnt;
  long[][] score;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    k = in.nextInt();
    board = new char[n][m];
    for (int i = 0; i < n; ++i) {
      in.next(board[i]);
    }
    cnt = new long[26][n + 1][m + 1];
    a = new int[k];
    b = new int[k];
    c = new int[k];
    d = new int[k];
    letter = new int[k];
    for (int i = 0; i < k; ++i) {
      int a = in.nextInt() - 1;
      int b = in.nextInt() - 1;
      int c = in.nextInt();
      int d = in.nextInt();
      in.next(token);
      int letter = token[0] - 'a';
      ++cnt[letter][a][b];
      --cnt[letter][a][d];
      --cnt[letter][c][b];
      ++cnt[letter][c][d];
      this.a[i] = a;
      this.b[i] = b;
      this.c[i] = c;
      this.d[i] = d;
      this.letter[i] = letter;
    }
    score = new long[n + 1][m + 1];
    calcCnt();
    long res = Long.MAX_VALUE;
    for (int i = 0; i < k; ++i) {
      res = Math.min(res, calc(a[i], b[i], c[i] - 1, d[i] - 1, letter[i]));
    }
    out.println(res);
  }

  void calcCnt() {
    for (long[][] value : cnt) aggregate(value);
    for (int x = 0; x < n; ++x) for (int y = 0; y < m; ++y) {
      int letter = board[x][y] - 'a', rem = k;
      long curScore = 0;
      for (int l = 0; l < 26; ++l) {
        rem -= cnt[l][x][y];
        curScore += Math.abs(letter - l) * cnt[l][x][y];
      }
      cnt[board[x][y] - 'a'][x][y] += rem;
      score[x][y] = curScore;
    }
    for (long[][] value : cnt) aggregate(value);
    aggregate(score);
  }

  void aggregate(long[][] value) {
    for (int x = 1; x < n; ++x) value[x][0] += value[x - 1][0];
    for (int y = 1; y < m; ++y) value[0][y] += value[0][y - 1];
    for (int x = 1; x < n; ++x) for (int y = 1; y < m; ++y) {
      value[x][y] += value[x - 1][y] + value[x][y - 1] - value[x - 1][y - 1];
    }
  }

  long calc(int x1, int y1, int x2, int y2, int letter) {
    long res = score[n - 1][m - 1] - calc(score, x1, y1, x2, y2);
    for (int l = 0; l < 26; ++l) {
      res += calc(cnt[l], x1, y1, x2, y2) * Math.abs(letter - l);
    }
    return res;
  }

  long calc(long[][] value, int x1, int y1, int x2, int y2) {
    long res = value[x2][y2];
    if (x1 > 0) res -= value[x1 - 1][y2];
    if (y1 > 0) res -= value[x2][y1 - 1];
    if (x1 > 0 && y1 > 0) res += value[x1 - 1][y1 - 1];
    return res;
  }
}
