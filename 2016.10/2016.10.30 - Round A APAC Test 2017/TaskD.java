package main;

import template.array.LongArrayUtils;
import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskD {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int coin, n;
      int[] maxLevel, currentLevel;
      int[][] power, cost;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        coin = in.nextInt();
        n = in.nextInt();
        maxLevel = new int[n];
        currentLevel = new int[n];
        power = new int[n][];
        cost = new int[n][];
        for (int i = 0; i < n; ++i) {
          maxLevel[i] = in.nextInt();
          currentLevel[i] = in.nextInt() - 1;
          power[i] = new int[maxLevel[i]];
          for (int j = 0; j < maxLevel[i]; ++j) {
            power[i][j] = in.nextInt();
          }
          cost[i] = new int[maxLevel[i] - 1];
          for (int j = 0; j < maxLevel[i] - 1; ++j) {
            cost[i][j] = in.nextInt();
          }
        }
      }

      Storage[] storages;
      long res;

      @Override
      public void process(int taskIdx) {
        storages = new Storage[9];
        for (int i = 0; i < 9; ++i) storages[i] = new Storage();
        dfs(0, 0, 0, 0, n >> 1, false);
        for (int i = 0; i < 9; ++i) storages[i].process();
        res = 0;
        dfs(n >> 1, 0, 0, 0, n, true);
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %d\n", taskIdx, res);
      }

      void dfs(int idx, int cardCnt, long costCnt, long powerCnt, int stopIdx, boolean calcStage) {
        if (costCnt > coin) return;
        if (idx == stopIdx) {
          if (calcStage) {
            long currentRes = storages[8 - cardCnt].calc(coin - costCnt);
            if (currentRes >= 0) {
              res = Math.max(res, currentRes + powerCnt);
            }
          } else {
            storages[cardCnt].add(costCnt, powerCnt);
          }
          return;
        }
        dfs(idx + 1, cardCnt, costCnt, powerCnt, stopIdx, calcStage);
        long sumCost = 0;
        for (int level = currentLevel[idx]; level < maxLevel[idx]; ++level) {
          dfs(idx + 1, cardCnt + 1, costCnt + sumCost, powerCnt + power[idx][level], stopIdx, calcStage);
          if (level + 1 < maxLevel[idx]) {
            sumCost += cost[idx][level];
          }
        }
      }

      class Storage {

        List<Cost> costs;
        int n;
        long[] orderCost, orderPower;

        Storage() {
          costs = new ArrayList<>();
        }

        void add(long cost, long power) {
          costs.add(new Cost(cost, power));
        }

        void process() {
          Collections.sort(costs);
          orderCost = new long[costs.size()];
          orderPower = new long[costs.size()];
          n = 0;
          for (Cost cost : costs) if (n == 0 || orderCost[n - 1] != cost.cost) {
            orderCost[n] = cost.cost;
            orderPower[n++] = cost.power;
          }
          for (int i = 1; i < n; ++i) {
            orderPower[i] = Math.max(orderPower[i], orderPower[i - 1]);
          }
        }

        long calc(long cost) {
          int idx = LongArrayUtils.upperBound(orderCost, 0, n, cost);
          return idx == 0 ? -1 : orderPower[idx - 1];
        }

        class Cost implements Comparable<Cost> {
          final long cost, power;

          Cost(long cost, long power) {
            this.cost = cost;
            this.power = power;
          }

          @Override
          public int compareTo(Cost o) {
            return parse(cost != o.cost ? cost - o.cost : o.power - power);
          }

          int parse(long x) {
            if (x < 0) return -1;
            if (x > 0) return 1;
            return 0;
          }
        }
      }
    };
  }
}
