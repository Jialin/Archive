package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TaskD {
  int n;
  Map<String, IntArrayList> firstMap;
  String[] first, second, names;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    firstMap = new TreeMap<>();
    first = new String[n];
    second = new String[n];
    for (int i = 0; i < n; ++i) {
      String a = in.next();
      String b = in.next();
      first[i] = a.substring(0, 3);
      second[i] = a.substring(0, 2) + b.substring(0, 1);
      if (!firstMap.containsKey(first[i])) firstMap.put(first[i], new IntArrayList(1));
      firstMap.get(first[i]).add(i);
    }
    names = new String[n];
    if (found()) {
      out.println("YES");
      for (String name : names) out.println(name);
    } else {
      out.println("NO");
    }
  }

  Set<String> nameSet;

  boolean found() {
    nameSet = new TreeSet<>();
    for (IntArrayList list : firstMap.values()) if (list.size > 1) {
      for (int i = 0; i < list.size; ++i) {
        if (!add(list.get(i), second[list.get(i)])) return false;
      }
    }
    while (true) {
      boolean updated = false;
      for (String name : nameSet) if (firstMap.containsKey(name)) {
        int idx = firstMap.get(name).get(0);
        if (names[idx] != null) continue;
        if (!add(idx, second[idx])) return false;
        updated = true;
        break;
      }
      if (!updated) break;
    }
    for (int i = 0; i < n; ++i) if (names[i] == null) {
      if (!add(i, first[i])) {
        throw new IllegalArgumentException();
      }
    }
    return true;
  }

  boolean add(int idx, String name) {
    if (nameSet.contains(name)) return false;
    nameSet.add(name);
    names[idx] = name;
    return true;
  }
}
