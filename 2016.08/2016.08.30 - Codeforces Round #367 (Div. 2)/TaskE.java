package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int[][] idx;
  int[] values, rightIdx, downIdx;

  IntArrayList row1, row2;
  IntArrayList column1, column2;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    int q = in.nextInt();
    idx = new int[n + 2][m + 2];
    int idxCnt = 0;
    for (int i = n + 1; i >= 0; --i) for (int j = m + 1; j >= 0; --j) {
      idx[i][j] = idxCnt++;
    }
    int n2m2 = (n + 2) * (m + 2);
    values = new int[n2m2];
    rightIdx = new int[n2m2];
    downIdx = new int[n2m2];
    for (int i = 0; i <= n; ++i) for (int j = 0; j <= m; ++j) {
      if (i > 0 && j > 0) {
        values[idx[i][j]] = in.nextInt();
      }
      rightIdx[idx[i][j]] = idx[i][j + 1];
      downIdx[idx[i][j]] = idx[i + 1][j];
    }
    row1 = new IntArrayList(n << 1);
    row2 = new IntArrayList(n << 1);
    column1 = new IntArrayList(m << 1);
    column2 = new IntArrayList(m << 1);
    for (int i = 0; i < q; ++i) {
      int x1 = in.nextInt();
      int y1 = in.nextInt();
      int x2 = in.nextInt();
      int y2 = in.nextInt();
      int h = in.nextInt();
      int w = in.nextInt();
      update(x1, y1, x2, y2, h, w);
    }
    int rowHead = idx[0][0];
    for (int i = 0; i < n; ++i) {
      rowHead = downIdx[rowHead];
      int cell = rowHead;
      for (int j = 0; j < m; ++j) {
        cell = rightIdx[cell];
        out.print(values[cell]);
        out.print(j + 1 == m ? '\n' : ' ');
      }
    }
  }

  private void update(int x1, int y1, int x2, int y2, int h, int w) {
    extract(x1, y1, h, w, row1, column1);
    extract(x2, y2, h, w, row2, column2);
    for (int i = 0; i < row1.size(); ++i) {
      int tmp = rightIdx[row1.get(i)];
      rightIdx[row1.get(i)] = rightIdx[row2.get(i)];
      rightIdx[row2.get(i)] = tmp;
    }
    for (int i = 0; i < column1.size(); ++i) {
      int tmp = downIdx[column1.get(i)];
      downIdx[column1.get(i)] = downIdx[column2.get(i)];
      downIdx[column2.get(i)] = tmp;
    }
  }

  private void extract(int x, int y, int h, int w, IntArrayList row, IntArrayList column) {
    row.clear();
    column.clear();
    int corner = goRight(goDown(idx[0][0], x - 1), y - 1), pnt;
    // down, right
    pnt = corner;
    for (int i = 0; i < h; ++i) {
      pnt = downIdx[pnt];
      row.add(pnt);
    }
    for (int i = 0; i < w; ++i) {
      pnt = rightIdx[pnt];
      column.add(pnt);
    }
    // right, down
    pnt = corner;
    for (int i = 0; i < w; ++i) {
      pnt = rightIdx[pnt];
      column.add(pnt);
    }
    for (int i = 0; i < h; ++i) {
      pnt = downIdx[pnt];
      row.add(pnt);
    }
  }

  int goRight(int idx, int cnt) {
    for (int i = 0; i < cnt; ++i) {
      idx = rightIdx[idx];
    }
    return idx;
  }

  int goDown(int idx, int cnt) {
    for (int i = 0; i < cnt; ++i) {
      idx = downIdx[idx];
    }
    return idx;
  }
}
