package main;

import template.array.IntArrayUtils;
import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int n, q;
      long[] l, r;
      int[] sum;
      long[] sumsum;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        n = in.nextInt();
        q = in.nextInt();
        sum = in.nextInt(n);
        l = new long[q];
        r = new long[q];
        for (int i = 0; i < q; ++i) {
          l[i] = in.nextLong();
          r[i] = in.nextLong();
        }
      }

      long[] res;

      @Override
      public void process(int taskIdx) {
        for (int i = 1; i < n; ++i) {
          sum[i] += sum[i - 1];
        }
        sumsum = new long[n];
        for (int i = 0; i < n; ++i) {
          sumsum[i] = (i > 0 ? sumsum[i - 1] : 0) + sum[i];
        }
        res = new long[q];
        for (int i = 0; i < q; ++i) {
          res[i] = calc(r[i]) - calc(l[i] - 1);
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d:\n", taskIdx);
        out.println(res, '\n');
      }

      long calc(long cnt) {
        int gap = sum[n - 1], lower = 0, upper = gap - 1;
        long gapCnt = cnt;
        while (lower <= upper) {
          int medium = (lower + upper) >> 1;
          long mediumCnt = calcCnt(medium);
          if (mediumCnt >= cnt) {
            gap = medium;
            gapCnt = mediumCnt;
            upper = medium - 1;
          } else {
            lower = medium + 1;
          }
        }
        long res = calcSum(gap);
        res -= gap * (gapCnt - cnt);
        return res;
      }

      long calcCnt(int gap) {
        long res = 0;
        for (int i = 0; i < n; ++i) {
          res += IntArrayUtils.upperBound(sum, i, n, (i > 0 ? sum[i - 1] : 0) + gap) - i;
        }
        return res;
      }

      long calcSum(int gap) {
        long res = 0;
        for (int i = 0; i < n; ++i) {
          int idx = IntArrayUtils.upperBound(sum, i, n, (i > 0 ? sum[i - 1] : 0) + gap);
          res += calcSumSum(i, idx);
        }
        return res;
      }

      long calcSumSum(int lower, int upper) {
        long res = (upper > 0 ? sumsum[upper - 1] : 0) - (lower > 0 ? sumsum[lower - 1] : 0);
        if (lower > 0) {
          res -= (long) sum[lower - 1] * (upper - lower);
        }
        return res;
      }
    };
  }
}
