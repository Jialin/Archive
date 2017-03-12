package main;

import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int A, B, K;
      long N;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        A = in.nextInt();
        B = in.nextInt();
        N = in.nextLong();
        K = in.nextInt();
      }

      int[] cntA, cntB;
      int res;

      @Override
      public void process(int taskIdx) {
        cntA = new int[K];
        calc(cntA, A);
        cntB = new int[K];
        calc(cntB, B);
        res = 0;
        for (int i = 0; i < K; ++i) {
          int j = i == 0 ? 0 : K - i;
          res = IntUtils.add(res, IntUtils.mul(cntA[i], cntB[j]));
        }
        for (int i = 0; i < K; ++i) {
          int a = IntUtils.pow(i, A, K);
          int b = IntUtils.pow(i, B, K);
          if (IntUtils.add(a, b, K) > 0) continue;
          res = IntUtils.sub(res, calcCnt(i));
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %d\n", taskIdx, res);
      }

      void calc(int[] cnt, int pow) {
        for (int i = 0; i < K; ++i) {
          int idx = IntUtils.pow(i, pow, K);
          cnt[idx] = IntUtils.add(cnt[idx], calcCnt(i));
        }
      }

      int calcCnt(int remainder) {
        if (remainder > N) return 0;
        long res = (N - remainder) / K;
        if (remainder > 0) ++res;
        return (int) (res % IntUtils.MOD);
      }
    };
  }
}
