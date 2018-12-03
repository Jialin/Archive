package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA1368 {

  final int MAXN = 50;
  final int MAXM = 1000 + 1;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    char[][] dna = new char[MAXN][MAXM];
    for (int remTask = in.nextInt(); remTask > 0; --remTask) {
      int n = in.nextInt();
      int m = in.nextInt();
      for (int i = 0; i < n; ++i) {
        in.next(dna[i]);
        dna[i][m] = 0;
      }
      int res = n * m;
      for (int j = 0; j < m; ++j) {
        int a = 0;
        int c = 0;
        int g = 0;
        int t = 0;
        for (int i = 0; i < n; ++i) {
          switch (dna[i][j]) {
            case 'A':
              ++a;
              break;
            case 'C':
              ++c;
              break;
            case 'G':
              ++g;
              break;
            case 'T':
              ++t;
              break;
          }
        }
        if (a >= c && a >= g && a >= t) {
          dna[0][j] = 'A';
          res -= a;
        } else if (c >= a && c >= g && c >= t) {
          dna[0][j] = 'C';
          res -= c;
        } else if (g >= a && g >= c && g >= t) {
          dna[0][j] = 'G';
          res -= g;
        } else {
          dna[0][j] = 'T';
          res -= t;
        }
      }
      out.println(dna[0]);
      out.println(res);
    }
  }
}
