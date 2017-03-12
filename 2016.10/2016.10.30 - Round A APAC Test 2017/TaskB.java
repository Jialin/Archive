package main;

import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayList;
import java.util.List;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  static int[] DX = new int[]{-1, 1,  0, 0};
  static int[] DY = new int[]{ 0, 0, -1, 1};

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int R, C;
      int[][] H;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        R = in.nextInt();
        C = in.nextInt();
        H = new int[R + 1][C + 1];
        for (int i = 1; i <= R; ++i) for (int j = 1; j <= C; ++j) {
          H[i][j] = in.nextInt();
        }
      }

      List<Position> queue;
      boolean[][] visited;
      int res;

      @Override
      public void process(int taskIdx) {
        visited = new boolean[R + 1][C + 1];
        queue = new ArrayList<>((R + 1) * (C + 1));
        for (int i = 1; i <= R; ++i) {
          queue.add(new Position(i, 0));
          queue.add(new Position(i, C + 1));
        }
        for (int j = 1; j <= C; ++j) {
          queue.add(new Position(0, j));
          queue.add(new Position(R + 1, j));
        }
        res = 0;
        for (int h = 1; h <= 1000; ++h) {
          for (int i = 0; i < queue.size(); ++i) {
            Position position = queue.get(i);
            position.spread(h);
          }
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %d\n", taskIdx, res);
      }

      class Position {

        final int x, y;

        Position(int x, int y) {
          this.x = x;
          this.y = y;
        }

        void spread(int height) {
          for (int dir = 0; dir < 4; ++dir) {
            int newX = x + DX[dir], newY = y + DY[dir];
            if (!valid(newX, newY) || visited[newX][newY] || H[newX][newY] > height) continue;
            res += height - H[newX][newY];
            visited[newX][newY] = true;
            queue.add(new Position(newX, newY));
          }
        }

        boolean valid(int x, int y) {
          return 1 <= x && x <= R && 1 <= y && y <= C;
        }
      }
    };
  }
}
