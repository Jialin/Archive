package main;

import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

public class TaskD {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int N, MOD;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        N = in.nextInt();
        MOD = in.nextInt();
      }

      int[] fact;
      int[] f, s1, s2;

      @Override
      public void process(int taskIdx) {
        fact = new int[N + 1];
        fact[0] = fix(1);
        for (int i = 1; i <= N; ++i) {
          fact[i] = mul(fact[i - 1], fix(i));
        }
        f = new int[N + 1];
        s1 = new int[N + 1];
        s2 = new int[N + 1];
        f[1] = s1[1] = s2[1] = 1;
        for (int n = 2; n <= N; ++n) {
          f[n] = fact[n];
          for (int i = 1; i < n; ++i) {
            f[n] = sub(f[n], mul(f[i], fact[n - i]));
            s1[n] = add(s1[n], mul(f[i], add(s1[n - i], fact[n - i])));
            s2[n] = add(s2[n], mul(f[i], add(add(add(s2[n - i], s1[n - i]), s1[n - i]), fact[n - i])));
          }
          s1[n] = add(s1[n], f[n]);
          s2[n] = add(s2[n], f[n]);
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %d\n", taskIdx, s2[N] % MOD);
      }

      int fix(int a) {
        return IntUtils.fix(a, MOD);
      }

      int add(int a, int b) {
        return IntUtils.add(a, b, MOD);
      }

      int sub(int a, int b) {
        return IntUtils.sub(a, b, MOD);
      }

      int mul(int a, int b) {
        return IntUtils.mul(a, b, MOD);
      }
    };
  }
}
