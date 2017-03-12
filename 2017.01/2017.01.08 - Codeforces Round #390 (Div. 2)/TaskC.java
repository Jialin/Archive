package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class TaskC {
  static int MAXN = 100;
  static int MAXM = 100;
  static Pattern PATTERN = Pattern.compile("[.,!? ]");

  int n, m;
  String[] name = new String[MAXN];
  Map<String, Integer> nameMap = new TreeMap<>();
  String[] message = new String[MAXM];
  boolean[][] blocked = new boolean[MAXM][MAXN];
  boolean[][] valid = new boolean[MAXM][MAXN];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      n = in.nextInt();
      in.next(n, name);
      nameMap.clear();
      for (int i = 0; i < n; ++i) {
        nameMap.put(name[i], i);
      }
      m = in.nextInt();
      for (int i = 0; i < m; ++i) {
        String[] tokens;
        while (true) {
          tokens = in.nextLine().split(":");
          if (tokens.length > 1) break;
        }
        message[i] = tokens[1];
        if ("?".equals(tokens[0])) {
          Arrays.fill(blocked[i], 0, n, false);
          for (String token : PATTERN.split(message[i])) if (nameMap.containsKey(token)) {
            blocked[i][nameMap.get(token)] = true;
          }
        } else {
          int idx = -1;
          for (int j = 0; j < n; ++j) if (tokens[0].equals(name[j])) {
            idx = j;
            break;
          }
          if (idx < 0) throw new IllegalArgumentException();
          Arrays.fill(blocked[i], 0, n, true);
          blocked[i][idx] = false;
        }
      }
      for (int i = m - 1; i >= 0; --i) {
        Arrays.fill(valid[i], 0, n, false);
        int idx1 = -1, idx2 = -1;
        if (i + 1 < m) {
          for (int j = 0; j < n; ++j) if (valid[i + 1][j]) {
            if (idx1 < 0) {
              idx1 = j;
            } else {
              idx2 = j;
              break;
            }
          }
        }
        for (int j = 0; j < n; ++j) if (!blocked[i][j]) {
          if (i + 1 == m || (idx1 >= 0 && idx1 != j) || (idx2 >= 0 && idx2 != j)) {
            valid[i][j] = true;
          }
        }
      }
      int res = -1;
      for (int j = 0; j < n; ++j) if (valid[0][j]) {
        res = j;
        break;
      }
      if (res < 0) {
        out.println("Impossible");
      } else {
        out.print(name[res]);
        out.print(":");
        out.println(message[0]);
        for (int i = 1; i < m; ++i) {
          for (int j = 0; j < n; ++j) if (valid[i][j] && j != res) {
            res = j;
            break;
          }
          out.print(name[res]);
          out.print(":");
          out.println(message[i]);
        }
      }
    }
  }
}
