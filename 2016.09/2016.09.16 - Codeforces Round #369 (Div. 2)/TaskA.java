package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int n;
  char[][] seats;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    seats = new char[n][5];
    for (int i = 0; i < n; ++i) {
      seats[i] = in.next().toCharArray();
    }
    if (calc()) {
      out.println("YES");
      for (int i = 0; i < n; ++i) {
        out.println(String.valueOf(seats[i]));
      }
    } else {
      out.println("NO");
    }
  }

  boolean calc() {
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < 4; ++j) {
        if (seats[i][j] == 'O' && seats[i][j + 1] == 'O') {
          seats[i][j] = seats[i][j + 1] = '+';
          return true;
        }
      }
    }
    return false;
  }
}
