package main;

import template.array.LongArrayUtils;
import template.collections.list.IntArrayList;
import template.collections.list.LongArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.hash.HashUtils;

public class TaskD {
  int n, m;
  char[] s;
  int[] a;
  long[] value, hash, revHash;

  LongArrayList disHash;
  IntArrayList[] aLists;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    a = new int[n];
    hash = new long[n];
    revHash = new long[n];
    s = new char[m];
    value = new long[m];
    disHash = new LongArrayList(n << 1);
    for (int i = 0; i < n; ++i) {
      in.next(s);
      a[i] = in.nextInt();
      for (int j = 0; j < m; ++j) {
        value[j] = s[j] - 'a';
      }
      hash[i] = HashUtils.calc(value);
      LongArrayUtils.reverse(value);
      revHash[i] = HashUtils.calc(value);
      disHash.add(hash[i]);
      disHash.add(revHash[i]);
    }
    disHash.sortAndUnique();
    aLists = new IntArrayList[disHash.size];
    for (int i = 0; i < aLists.length; ++i) {
      aLists[i] = new IntArrayList();
    }
    for (int i = 0; i < n; ++i) {
      hash[i] = disHash.lowerBound(hash[i]);
      revHash[i] = disHash.lowerBound(revHash[i]);
      aLists[(int) hash[i]].add(a[i]);
    }
    for (IntArrayList aList : aLists) {
      aList.sort();
    }
    out.println(calc());
  }

  int calc() {
    int res = 0, deltaRes = 0;
    for (int i = 0; i < n; ++i) {
      IntArrayList lst = aLists[(int) hash[i]];
      IntArrayList revLst = aLists[(int) revHash[i]];
      if (lst == revLst) {
        while (!lst.isEmpty()) {
          int a = lst.pollLast();
          if (a <= 0) break;
          if (lst.isEmpty()) {
            deltaRes = Math.max(deltaRes, a);
            break;
          }
          int b = lst.pollLast();
          if (a + b > 0) {
            res += a + b;
            deltaRes = Math.max(deltaRes, -b);
          } else {
            deltaRes = Math.max(deltaRes, a);
          }
        }
        continue;
      }
      while (!lst.isEmpty() && !revLst.isEmpty()) {
        int sum = lst.pollLast() + revLst.pollLast();
        if (sum <= 0) break;
        res += sum;
      }
    }
    return res + deltaRes;
  }
}
