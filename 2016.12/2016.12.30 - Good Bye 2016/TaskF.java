package main;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;
import java.util.Random;

public class TaskF {
  static int MAXH = 7;
  static int MAXN = 1 << MAXH;

  Server server;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    server = new Server(in, out);
    nodes = new IntArrayList[MAXN];
    lst = new IntArrayList(MAXN);
    height = new int[MAXN];
    asked = new boolean[MAXN];
    for (int i = 0; i < MAXN; ++i) nodes[i] = new IntArrayList(3);
    for (int remTask = server.getTask(); remTask > 0; --remTask) {
      server.init();
      h = server.getHeight();
      if (h == 0) break;
      server.answer(calc());
    }
  }

  int n, h;
  int[] height;
  boolean[] asked;
  IntArrayList[] nodes;
  IntArrayList lst;

  int calc() {
    n = (1 << h) - 1;
    for (int i = 0; i < n; ++i) nodes[i].clear();
    Arrays.fill(asked, false);
    int subRoot = findSubRoot();
    if (height[subRoot] == 0) return subRoot;
    return calc(notAskedNode(subRoot), subRoot, height[subRoot] - 1);
  }

  int calc(int subRoot, int prev, int height) {
    if (height == 0) {
      return subRoot;
    }
    ask(subRoot);
    if (height == 1) {
      return calcRoot(nodes[subRoot]);
    }
    if (height == 2) {
      lst.clear();
      for (int i = 0; i < nodes[subRoot].size; ++i) {
        int u = nodes[subRoot].get(i);
        ask(u);
        for (int j = 0; j < nodes[u].size; ++j) {
          int v = nodes[u].get(j);
          if (v != subRoot) lst.add(v);
        }
      }
      return calcRoot(lst);
    }
    boolean first = true;
    for (int i = 0; i < nodes[subRoot].size; ++i) {
      int u = nodes[subRoot].get(i);
      if (u == prev) continue;
      if (first) {
        first = false;
        if (!findLeaf(u, subRoot, h - 1 - height)) {
          return calc(u, subRoot, height - 1);
        }
      } else {
        return calc(u, subRoot, height - 1);
      }
    }
    throw new IllegalArgumentException();
  }

  boolean findLeaf(int u, int prev, int rem) {
    --rem;
    ask(u);
    for ( ; rem > 0; --rem) {
      for (int i = 0; i < nodes[u].size; ++i) {
        int v = nodes[u].get(i);
        if (v != prev) {
          prev = u;
          u = v;
          break;
        }
      }
      ask(u);
    }
    return nodes[u].size == 1;
  }

  int calcRoot(IntArrayList rootLst) {
    rootLst.sortAndUnique();
    int unAskedCnt = 0;
    for (int i = 0; i < rootLst.size; ++i) {
      int u = rootLst.get(i);
      if (asked[u]) {
        if (nodes[u].size == 2) return u;
      } else {
        ++unAskedCnt;
      }
    }
    for (int i = 0; i < rootLst.size; ++i) {
      int u = rootLst.get(i);
      if (asked[u]) continue;
      if (unAskedCnt > 1) {
        --unAskedCnt;
        if (ask(u) == 2) return u;
      } else {
        return u;
      }
    }
    throw new IllegalArgumentException();
  }

  int findSubRoot() {
    ask(0);
    if (nodes[0].size == 1) {
      height[0] = h - 1;
      return 0;
    }
    if (nodes[0].size == 2) {
      height[0] = 0;
      return 0;
    }
    lst.clear();
    walkToEnd(nodes[0].get(0), 0, lst);
    lst.reverse();
    lst.add(0);
    walkToEnd(nodes[0].get(1), 0, lst);
    if ((lst.size & 1) == 0) {
      System.out.printf("lst:%s\n", lst.toString());
      throw new IllegalArgumentException();
    }
    int res = lst.get(lst.size >> 1);
    height[res] = h - 1 - (lst.size >> 1);
    return res;
  }

  void walkToEnd(int u, int lastU, IntArrayList lst) {
    while (true) {
      lst.add(u);
      ask(u);
      if (nodes[u].size == 1) break;
      for (int i = 0; i < nodes[u].size; ++i) if (nodes[u].get(i) != lastU) {
        lastU = u;
        u = nodes[u].get(i);
        break;
      }
    }
  }

  int notAskedNode(int u) {
    for (int i = 0; i < nodes[u].size; ++i) {
      int v = nodes[u].get(i);
      if (!asked[v]) return v;
    }
    throw new IllegalArgumentException();
  }

  int ask(int u) {
    if (asked[u]) return nodes[u].size;
    asked[u] = true;
    server.ask(u);
    return nodes[u].size;
  }

  class Server {
    final QuickScanner in;
    final QuickWriter out;

    Server(QuickScanner in, QuickWriter out) {
      this.in = in;
      this.out = out;
    }

    void init() {}

    int getTask() {
      return in.nextInt();
    }

    int getHeight() {
      return in.nextInt();
    }

    void ask(int u) {
      out.println("? " + (u + 1));
      out.flush();
      int n = in.nextInt();
      if (n == 0) System.exit(0);
      for (int i = 0; i < n; ++i) {
        nodes[u].add(in.nextInt() - 1);
      }
    }

    void answer(int u) {
      out.println("! " + (u + 1));
      out.flush();
    }
  }

  class FakeServer extends Server {

    final int MAX_HEIGHT = 7;
    final int MAX_TASK = 1000;
    final Random RANDOM = new Random(1000000007);

    int n, h, chance;
    int[] location;
    int[] tree;

    FakeServer() {
      super(null, null);
      tree = new int[MAXN];
      location = new int[MAXN];
    }

    @Override
    void init() {
      h = RANDOM.nextInt(MAX_HEIGHT - 1) + 2;
      n = (1 << h) - 1;
      chance = 16;
      for (int i = 0; i < n; ++i) tree[i] = i;
      IntArrayUtils.shuffle(tree, 0, n);
      for (int i = 0; i < n; ++i) location[tree[i]] = i;
      System.out.printf("======tree:%s\n", IntArrayUtils.toString(tree, 0, n));
    }

    @Override
    int getTask() {
      return MAX_TASK;
    }

    @Override
    int getHeight() {
      return h;
    }

    @Override
    void ask(int u) {
      --chance;
      if (chance < 0) {
        System.out.printf("Too many query\n");
        System.exit(1);
      }
      int ul = location[u];
      IntArrayList res = nodes[u];
      if (ul > 0) {
        res.add(tree[(ul - 1) >> 1]);
      }
      if (((ul << 1) | 1) < n) {
        res.add(tree[(ul << 1) | 1]);
      }
      if ((ul << 1) + 2 < n) {
        res.add(tree[(ul << 1) + 2]);
      }
      System.out.printf("ask(%d):%s\n", u, res.toString());
    }

    @Override
    void answer(int u) {
      if (tree[0] == u) {
        System.out.println("correct");
      } else {
        System.out.println("!!!wrong!!!");
        System.out.println(IntArrayUtils.toString(tree, 0, n));
        System.exit(1);
      }
    }
  }
}
