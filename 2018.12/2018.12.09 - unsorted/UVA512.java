package main;

import template.array.Int2DArrayUtils;
import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class UVA512 {
  final int MAXN = 50;
  final int MAXN_SQR = MAXN * MAXN;
  final int MAXM = 10;

  int n, m;
  int[][] values;
  int[] rows;
  int[] answer;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    values = new int[MAXN][MAXN];
    rows = new int[MAXM];
    answer = new int[MAXN_SQR];
    int spreadsheetIdx = 0;
    while (true) {
      n = in.nextInt();
      m = in.nextInt();
      if (n == 0 && m == 0) {
        break;
      }
      int oN = n;
      int oM = m;
      for (int i = 0; i < MAXN; ++i) {
        Arrays.fill(values[i], -1);
      }
      for (int i = 0, v = 0; i < n; ++i, v += m) {
        IntArrayUtils.fillRange(values[i], v, v + m);
      }
      for (int remOp = in.nextInt(); remOp > 0; --remOp) {
        char op1 = (char) in.nextNonSpaceChar();
        char op2 = (char) in.nextNonSpaceChar();
        if (op1 == 'E') {
          int x1 = in.nextInt() - 1;
          int y1 = in.nextInt() - 1;
          int x2 = in.nextInt() - 1;
          int y2 = in.nextInt() - 1;
          Int2DArrayUtils.swap(values, x1, y1, x2, y2);
          continue;
        }
        if (op2 == 'C') {
          Int2DArrayUtils.diagonalFlip(n, m, values);
          n ^= m;
          m ^= n;
          n ^= m;
        }
        int rowCnt = in.nextInt();
        for (int i = 0; i < rowCnt; ++i) {
          rows[i] = in.nextInt() - 1;
        }
        IntArrayUtils.sort(rows, 0, rowCnt);
        for ( ; rowCnt > 0 && rows[rowCnt - 1] >= n; --rowCnt) {}
        rows[rowCnt] = n;
        if (op1 == 'D') {
          int pnt = rows[0];
          for (int i = 1; i <= rowCnt; ++i) {
            for (int j = rows[i - 1] + 1; j < rows[i]; ++j, ++pnt) {
              int[] tmp = values[pnt];
              values[pnt] = values[j];
              values[j] = tmp;
            }
          }
          for (int i = n - rowCnt; i < n; ++i) {
            Arrays.fill(values[i], 0, m, -1);
          }
          n -= rowCnt;
        } else {
          int pnt = n + rowCnt - 1;
          for (int i = rowCnt; i > 0; --i, --pnt) {
            for (int j = rows[i] - 1; j >= rows[i - 1]; --j, --pnt) {
              int[] tmp = values[pnt];
              values[pnt] = values[j];
              values[j] = tmp;
            }
          }
          n += rowCnt;
        }
        if (op2 == 'C') {
          Int2DArrayUtils.diagonalFlip(n, m, values);
          n ^= m;
          m ^= n;
          n ^= m;
        }
      }
      Arrays.fill(answer, 0, oN * oM, -1);
      for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) if (values[i][j] >= 0) {
        answer[values[i][j]] = i * m + j;
      }
      if (spreadsheetIdx > 0) {
        out.println();
      }
      out.printf("Spreadsheet #%d\n", ++spreadsheetIdx);
      for (int remQ = in.nextInt(); remQ > 0; --remQ) {
        int x = in.nextInt();
        int y = in.nextInt();
        int idx = (x - 1) * oM + y - 1;
        out.printf("Cell data in (%d,%d) ", x, y);
        if (x > oN || y > oM || answer[idx] < 0) {
          out.println("GONE");
        } else {
          out.printf("moved to (%d,%d)\n", answer[idx] / m + 1, answer[idx] % m + 1);
        }
      }
    }
  }
}
