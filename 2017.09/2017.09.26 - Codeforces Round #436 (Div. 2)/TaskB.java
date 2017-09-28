package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.HashSet;
import java.util.Set;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    Set<Character> set = new HashSet<>();
    in.nextInt();
    int res = 0;
    for (char ch : in.next().toCharArray()) {
      if (Character.isLowerCase(ch)) {
        set.add(ch);
        res = Math.max(res, set.size());
      } else {
        set.clear();
      }
    }
    out.println(res);
  }
}
