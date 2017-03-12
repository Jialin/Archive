package main;

import template.collections.list.CharArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Map;
import java.util.TreeMap;

public class TaskB {
  static int MAXL = 1000;

  int n;
  char[] s = new char[MAXL];
  char[] t = new char[MAXL];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.next(s);
    in.next(t);
    if (found()) {
      out.println(listS.size());
      for (int i = 0; i < listS.size(); ++i) {
        out.printf("%c %c\n", listS.get(i), listT.get(i));
      }
    } else {
      out.println(-1);
    }
  }

  Map<Character, Character> m;
  CharArrayList listS, listT;

  boolean found() {
    listS = new CharArrayList(26);
    listT = new CharArrayList(26);
    m = new TreeMap<>();
    for (int i = 0; i < n; ++i) {
      if (m.getOrDefault(s[i], t[i]) != t[i]
          || m.getOrDefault(t[i], s[i]) != s[i]) {
        return false;
      }
      m.put(s[i], t[i]);
      m.put(t[i], s[i]);
    }
    while (!m.isEmpty()) {
      for (char key : m.keySet()) {
        char value = m.get(key);
        m.remove(key);
        if (key == value) break;
        m.remove(value);
        listS.add(key);
        listT.add(value);
        break;
      }
    }
    return true;
  }
}
