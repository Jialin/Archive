package main;

import template.array.IntArrayUtils;
import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int n, k, m;
      int[][] a;
      int[] sum, sumCnt;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        n = in.nextInt();
        k = in.nextInt();
        a = new int[4][n];
        for (int i = 0; i < 4; ++i) for (int j = 0; j < n; ++j) {
          a[i][j] = in.nextInt();
        }
      }

      long res;

      @Override
      public void process(int taskIdx) {
        int nn = n * n;
        sum = new int[nn];
        int sumIdx = 0;
        for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) {
          sum[sumIdx++] = a[0][i] ^ a[1][j];
        }
        sumCnt = new int[nn];
        Arrays.sort(sum, 0, nn);
        m = IntArrayUtils.unique(sum, 0, nn, sumCnt, 0);
        res = 0;
        for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) {
          int value = a[2][i] ^ a[3][j] ^ k;
          int idx = IntArrayUtils.lowerBound(sum, 0, m, value);
          if (idx < m && sum[idx] == value) res += sumCnt[idx];
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %d\n", taskIdx, res);
      }
    };
  }
}
