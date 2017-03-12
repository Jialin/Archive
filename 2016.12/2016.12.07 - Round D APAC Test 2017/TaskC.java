package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.hash.HashUtils;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class TaskC {
  static int MAX_WORD_LENGTH = 20;
  static int MAX_SENTENSE_LENGTH = 4000;

  Map<Long, Integer> wordMap;
  long[] cnt = new long[26];
  int[] way = new int[MAX_SENTENSE_LENGTH + 1];

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    int n = in.nextInt();
    int q = in.nextInt();
    wordMap = new TreeMap<>();
    for (int i = 0; i < n; ++i) {
      String s = in.next();
      calcCnt(s);
      long hash = HashUtils.calc(cnt);
      wordMap.put(hash, wordMap.getOrDefault(hash, 0) + 1);
    }
    out.printf("Case #%d:", taskIdx);
    for (int qIdx = 0; qIdx < q; ++qIdx) {
      String s = in.next();
      Arrays.fill(way, 0);
      way[0] = 1;
      for (int i = 0; i < s.length(); ++i) if (way[i] > 0) {
        Arrays.fill(cnt, 0);
        for (int j = 0; j < MAX_WORD_LENGTH && i + j < s.length(); ++j) {
          ++cnt[s.charAt(i + j) - 'a'];
          long hash = HashUtils.calc(cnt);
          if (wordMap.containsKey(hash)) {
            way[i + j + 1] = IntUtils.add(way[i + j + 1], IntUtils.mul(way[i],wordMap.get(hash)));
          }
        }
      }
      out.printf(" %d", way[s.length()]);
    }
    out.println();
  }

  void calcCnt(String s) {
    Arrays.fill(cnt, 0);
    for (int i = 0; i < s.length(); ++i) {
      ++cnt[s.charAt(i) - 'a'];
    }
  }
}
