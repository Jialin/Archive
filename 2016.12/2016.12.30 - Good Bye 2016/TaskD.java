package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayDeque;
import java.util.Queue;

public class TaskD {
  static int[] DX = new int[]{1, 1, 0, -1, -1, -1,  0,  1};
  static int[] DY = new int[]{0, 1, 1,  1,  0, -1, -1, -1};

  static int MAXN = 310;
  static int OFFSET = 155;

  Flame[][][] flames = new Flame[8][MAXN][MAXN];
  FlameQueue[] queues = new FlameQueue[]{new FlameQueue(), new FlameQueue()};

  int n;
  int[] length;

  int coveredCnt;
  boolean[][] covered;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    length = in.nextInt(n);
    for (int dir = 0; dir < 8; ++dir) for (int x = 0; x < MAXN; ++x) for (int y = 0; y < MAXN; ++y) {
      flames[dir][x][y] = new Flame(dir, x, y);
    }
    covered = new boolean[MAXN][MAXN];
    coveredCnt = 0;
    int t = 0;
    queues[t].addInternal(flames[0][OFFSET][OFFSET]);
    for (int depth = 0; depth < n; ++depth) {
      while (!queues[t].queue.isEmpty()) {
        queues[t ^ 1].add(queues[t].poll(), length[depth]);
      }
      t ^= 1;
    }
    out.println(coveredCnt);
  }

  class Flame {
    final int dir, x, y;

    Flame(int dir, int x, int y) {
      this.dir = dir;
      this.x = x;
      this.y = y;
    }
  }

  class FlameQueue {
    boolean[][][] inQueue;
    Queue<Flame> queue;

    FlameQueue() {
      inQueue = new boolean[8][MAXN][MAXN];
      queue = new ArrayDeque<>(MAXN * MAXN * 8);
    }

    Flame poll() {
      Flame res = queue.poll();
      inQueue[res.dir][res.x][res.y] = false;
      return res;
    }

    void add(Flame flame, int length) {
      int x = flame.x, y = flame.y;
      for (int i = 0; i < length; ++i) {
        x += DX[flame.dir];
        y += DY[flame.dir];
        mark(x, y);
      }
      addInternal(flames[(flame.dir + 1) & 7][x][y]);
      addInternal(flames[(flame.dir + 7) & 7][x][y]);
    }

    void addInternal(Flame flame) {
      if (inQueue[flame.dir][flame.x][flame.y]) return;
      inQueue[flame.dir][flame.x][flame.y] = true;
      queue.add(flame);
    }
  }

  void mark(int x, int y) {
    if (!covered[x][y]) {
      covered[x][y] = true;
      ++coveredCnt;
    }
  }
}
