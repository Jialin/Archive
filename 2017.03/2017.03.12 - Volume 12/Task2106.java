package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Map;
import java.util.TreeMap;

public class Task2106 {
  int n, m, queryLength;
  String[] description;
  Map<String, Integer> idxMap;
  Structure[] structures;
  char[] query;
  QuickWriter out;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.out = out;
    n = in.nextInt();
    m = in.nextInt();
    description = new String[m];
    for (int i = 0; i < m; ++i) {
      description[i] = in.nextLine(true);
    }
    idxMap = new TreeMap<>();
    structures = new Structure[n];
    int last = 0;
    for (int i = 0; i < n; ++i) {
      int j = last + 1;
      for ( ; j < m && description[j].charAt(0) == ' '; ++j) {}
      structures[i] = new Structure(last, j);
      last = j;
      idxMap.put(structures[i].name, i);
    }
    query = new char[100000];
    queryLength = in.next(query);
    calc();
  }

  int pnt;

  void calc() {
    for (pnt = 0; pnt < queryLength; ) {
      structures[(int) (getHex() - 1)].handle(0);
    }
  }

  long getHex() {
    long res = 0;
    for (int i = 0; i < 8; ++i) {
      res = (res << 4) | getValue(query[pnt++]);
    }
    return res;
  }

  int getValue(char c) {
    return '0' <= c && c <= '9' ? c - '0' : c - 'A' + 10;
  }

  class Structure {
    String name;
    IntArrayList fields;

    Structure(int fromIdx, int toIdx) {
      name = description[fromIdx].substring(7);
      fields = new IntArrayList();
      for (int i = fromIdx + 1; i < toIdx; ++i) {
        String subField = description[i].substring(1);
        if ("int".equals(subField)) {
          fields.add(-1);
        } else if ("string".equals(subField)) {
          fields.add(-2);
        } else {
          fields.add(idxMap.get(subField));
        }
      }
    }

    void handle(int depth) {
      printSpaces(depth);
      out.println(name);
      for (int i = 0; i < fields.size; ++i) {
        int field = fields.get(i);
        if (field < 0) {
          printSpaces(depth + 1);
          if (field == -2) {
            out.print("string ");
            printString();
          } else {
            out.print("int ");
            printInt();
          }
          out.println();
        } else {
          structures[field].handle(depth + 1);
        }
      }
    }

    void printSpaces(int cnt) {
      for (int i = 0; i < cnt; ++i) {
        out.print(' ');
      }
    }
  }

  void printString() {
    for (int i = (int) getHex(); i > 0; --i) {
      printChar();
    }
  }

  void printChar() {
    int res = (getValue(query[pnt]) << 4) | getValue(query[pnt + 1]);
    pnt += 2;
    out.print((char) res);
  }

  void printInt() {
    out.print(getHex());
  }
}
