package main;

import template.collections.disjointset.DisjointSet;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskF {
  int n, m, s, t, ds, dt;
  int[] from, to;
  IntArrayList notST, orST;
  int andST;

  DisjointSet dset;
  IntArrayList res;

  int[] toS, toT;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    from = new int[m];
    to = new int[m];
    for (int i = 0; i < m; ++i) {
      from[i] = in.nextInt() - 1;
      to[i] = in.nextInt() - 1;
    }
    s = in.nextInt() - 1;
    t = in.nextInt() - 1;
    ds = in.nextInt();
    dt = in.nextInt();
    initEdges();
    res = new IntArrayList(n - 1);
    if (found()) {
      out.println("Yes");
      for (int i = 0; i + 1 < n; ++i) {
        int idx = res.get(i);
        out.println(from[idx] + 1, to[idx] + 1);
      }
    } else {
      out.println("No");
    }
  }

  void initEdges() {
    notST = new IntArrayList(m);
    orST = new IntArrayList(m);
    andST = -1;
    for (int i = 0; i < m; ++i) {
      if (from[i] == s || from[i] == t || to[i] == s || to[i] == t) {
        if ((from[i] == s && to[i] == t)
            || (from[i] == t && to[i] == s)) {
          andST = i;
        } else {
          orST.add(i);
        }
      } else {
        notST.add(i);
      }
    }
  }

  boolean found() {
    dset = new DisjointSet(n);
    for (int i = 0; i < notST.size(); ++i) {
      int idx = notST.get(i);
      if (!dset.isFriend(from[idx], to[idx])) {
        dset.setFriend(from[idx], to[idx]);
        res.add(idx);
      }
    }
    toS = new int[n];
    toT = new int[n];
    initTo(s, toS);
    initTo(t, toT);
    for (int i = 0; i < n; ++i) if (s != i && t != i && dset.calcRoot(i) == i) {
      if (toS[i] < 0 && toT[i] < 0) return false;
      if (toS[i] >= 0 && toT[i] >= 0) continue;
      if (toS[i] >= 0) {
        --ds;
        res.add(toS[i]);
      } else if (toT[i] >= 0) {
        --dt;
        res.add(toT[i]);
      }
    }
    boolean connected = false;
    for (int i = 0; i < n; ++i) if (dset.calcRoot(i) == i && toS[i] >= 0 && toT[i] >= 0) {
      if (!connected) {
        connected = true;
        --ds;
        res.add(toS[i]);
        --dt;
        res.add(toT[i]);
      } else if (ds > dt) {
        --ds;
        res.add(toS[i]);
      } else {
        --dt;
        res.add(toT[i]);
      }
    }
    if (!connected && andST >= 0) {
      connected = true;
      --ds;
      --dt;
      res.add(andST);
    }
    return connected && ds >= 0 && dt >= 0;
  }

  void initTo(int x, int[] toX) {
    Arrays.fill(toX, -1);
    for (int i = 0; i < orST.size(); ++i) {
      int idx = orST.get(i);
      if (from[idx] == x) {
        toX[dset.calcRoot(to[idx])] = idx;
      } else if (to[idx] == x) {
        toX[dset.calcRoot(from[idx])] = idx;
      }
    }
  }
}
