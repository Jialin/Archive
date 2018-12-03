package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class UVA340 {
  private static final int MAXN = 1000;

  int n;
  int[] secret, guess;
  int[] cntSecret, cntGuess;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    secret = new int[MAXN];
    guess = new int[MAXN];
    cntSecret = new int[10];
    cntGuess = new int[10];
    int taskIdx = 0;
    while (true) {
      n = in.nextInt();
      if (n == 0) {
        break;
      }
      in.nextInt(n, secret);
      out.printf("Game %d:\n", ++taskIdx);
      while (true) {
        in.nextInt(n, guess);
        if (guess[0] == 0) {
          break;
        }
        out.printf("    (%d,%d)\n", strong(), weak());
      }
    }
  }

  int strong() {
    int res = 0;
    for (int i = 0; i < n; ++i) {
      if (secret[i] == guess[i]) {
        ++res;
      }
    }
    return res;
  }

  int weak() {
    Arrays.fill(cntSecret, 0);
    Arrays.fill(cntGuess, 0);
    for (int i = 0; i < n; ++i) {
      if (secret[i] != guess[i]) {
        ++cntSecret[secret[i]];
        ++cntGuess[guess[i]];
      }
    }
    int res = 0;
    for (int i = 1; i < 10; ++i) {
      res += Math.min(cntSecret[i], cntGuess[i]);
    }
    return res;
  }
}
