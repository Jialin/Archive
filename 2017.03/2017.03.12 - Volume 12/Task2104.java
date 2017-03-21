package main;

import template.array.CharArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class Task2104 {
  int n, n2;
  char[] board;
  int[] sameLength;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    n2 = n << 1;
    board = new char[n2];
    in.next(board);
    in.next(board, n);
    CharArrayUtils.reverse(board, n, n2);
    sameLength = new int[n2];
    sameLength[n2 - 1] = 1;
    for (int i = n2 - 2; i >= 0; --i) {
      sameLength[i] = board[i] == board[i + 1] ? sameLength[i + 1] + 1 : 1;
    }
    int res = calc(true, 0, n);
    if (res > 0) {
      out.println("Alice");
    } else if (res < 0) {
      out.println("Bob");
    } else {
      out.println("Draw");
    }
  }

  int calc(boolean alice, int startIdx, int length) {
    if (sameLength[startIdx] >= (length << 1)) {
      if (board[startIdx] == 'A') {
        return alice ? 1 : -1;
      } else {
        return alice ? -1 : 1;
      }
    }
    if ((length & 1) > 0) {
      return 0;
    }
    int left = calc(!alice, startIdx, length >> 1);
    int right = calc(!alice, startIdx + length, length >> 1);
    if (left == -1 || right == -1) return 1;
    if (left == 0 || right == 0) return 0;
    return -1;
  }
}
