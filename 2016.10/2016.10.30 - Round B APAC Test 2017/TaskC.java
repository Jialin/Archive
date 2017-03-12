package main;

import template.array.IntArrayUtils;
import template.collections.intervaltree.AbstractIntervalTree;
import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskC {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int n, MOD;
      int[] ls, rs;
      long a, b, c1, c2;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        n = in.nextInt();
        ls = new int[n];
        ls[0] = in.nextInt();
        rs = new int[n];
        rs[0] = in.nextInt();
        a = in.nextInt();
        b = in.nextInt();
        c1 = in.nextInt();
        c2 = in.nextInt();
        MOD = in.nextInt();
      }

      int disCnt;
      int[] dis;
      IntervalTree tree;
      int res;

      @Override
      public void process(int taskIdx) {
        dis = new int[n << 1];
        disCnt = 0;
        int x = ls[0], y = rs[0];
        dis[disCnt++] = ls[0];
        dis[disCnt++] = rs[0] + 1;
        for (int i = 1; i < n; ++i) {
          int newX = (int) ((a * x + b * y + c1) % MOD);
          int newY = (int) ((a * y + b * x + c2) % MOD);
          ls[i] = Math.min(newX, newY);
          rs[i] = Math.max(newX, newY);
          x = newX;
          y = newY;
          dis[disCnt++] = ls[i];
          dis[disCnt++] = rs[i] + 1;
        }
        Arrays.sort(dis, 0, disCnt);
        disCnt = IntArrayUtils.unique(dis, 0, disCnt);
        tree = new IntervalTree(disCnt - 1);
//        System.out.printf("disCnt(%d): %s\n", disCnt, IntArrayUtils.toString(dis, 0, disCnt));
//        for (int i = 0; i < n; ++i) {
//          System.out.printf("lr(%d): [%d, %d)\n", i, ls[i], rs[i]);
//        }
        for (int i = 0; i < n; ++i) {
          ls[i] = IntArrayUtils.lowerBound(dis, 0, disCnt, ls[i]);
          rs[i] = IntArrayUtils.lowerBound(dis, 0, disCnt, rs[i] + 1);
//          System.out.printf("lr(%d): [%d, %d)\n", i, ls[i], rs[i]);
          tree.updateRange(ls[i], rs[i]);
        }
//        System.out.printf("size(0):%d zeroCnt(0):%d\n", tree.size[0], tree.zeroCnt[0]);
        res = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
          res = Math.max(res, tree.calc(ls[i], rs[i]));
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %d\n", taskIdx, tree.size[0] - tree.zeroCnt[0] - res);
      }

      class IntervalTree extends AbstractIntervalTree {

        int[] zeroCnt;
        int[] oneCnt;
        int[] delta;
        int[] size;

        IntervalTree(int leafCapacity) {
          super(leafCapacity);
        }

        @Override
        public void createSubclass(int nodeCapacity) {
          zeroCnt = new int[nodeCapacity];
          oneCnt = new int[nodeCapacity];
          delta = new int[nodeCapacity];
          size = new int[nodeCapacity];
        }

        @Override
        public void initLeaf(int nodeIdx, int idx) {
          zeroCnt[nodeIdx] = size[nodeIdx] = dis[idx + 1] - dis[idx];
          oneCnt[nodeIdx] = 0;
          delta[nodeIdx] = 0;
        }

        @Override
        public void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx) {
          zeroCnt[nodeIdx] = zeroCnt[leftNodeIdx] + zeroCnt[rightNodeIdx];
          oneCnt[nodeIdx] = oneCnt[leftNodeIdx] + oneCnt[rightNodeIdx];
          delta[nodeIdx] = 0;
          size[nodeIdx] = size[leftNodeIdx] + size[rightNodeIdx];
        }

        @Override
        public void updateNode(int nodeIdx, int lower, int upper) {
          oneCnt[nodeIdx] = zeroCnt[nodeIdx];
          zeroCnt[nodeIdx] = 0;
          ++delta[nodeIdx];
        }

        @Override
        public void calcAppend(int nodeIdx, int lower, int upper) {
          res += oneCnt[nodeIdx];
        }

        @Override
        public void pushLazyPropagation(int fromNodeIdx, int toNodeIdx) {
          if (delta[fromNodeIdx] == 0) return;
          if (delta[fromNodeIdx] == 1) {
            oneCnt[toNodeIdx] = zeroCnt[toNodeIdx];
            zeroCnt[toNodeIdx] = 0;
          } else {
            zeroCnt[toNodeIdx] = oneCnt[toNodeIdx] = 0;
          }
          delta[toNodeIdx] += delta[fromNodeIdx];
        }

        @Override
        public void clearLazyPropagation(int nodeIdx) {
          delta[nodeIdx] = 0;
        }

        int res;

        int calc(int lower, int upper) {
          res = 0;
          calcRange(lower, upper);
          return res;
        }
      }
    };
  }
}
