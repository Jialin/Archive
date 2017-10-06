package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.HashSet;
import java.util.Set;

public class TaskA {
  Set<String> names;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    names = new HashSet<>();
    for (int i = in.nextInt(); i > 0; --i) {
      out.println(names.add(in.next()) ? "NO" : "YES");
    }
  }
}
