package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    char[] dir = new char[10];
    int position = 0;
    boolean valid = true;
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      int length = in.nextInt();
      in.next(dir);
      if (dir[0] == 'N') {
        position -= length;
      } else if (dir[0] == 'S') {
        position += length;
      } else if (position == 0 || position == 20000) {
        valid = false;
        break;
      }
      if (0 <= position && position <= 20000) continue;
      valid = false;
      break;
    }
    out.println(valid && position == 0 ? "YES" : "NO");
  }
}
