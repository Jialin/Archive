package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

public class TaskD {
  static IntModular MOD = new IntModular();

  int K;
  int a;
  int b;
  int ab, abInv;
  int pa, paInv;
  int pb, pbInv;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    K = in.nextInt();
    a = in.nextInt();
    b = in.nextInt();
    ab = a + b;
    abInv = MOD.inverse(ab);
    pa = MOD.mul(a, abInv);
    paInv = MOD.inverse(pa);
    pb = MOD.mul(b, abInv);
    pbInv = MOD.inverse(pb);
    out.println(MOD.mul(calc(), paInv));
  }

  int[][] dpClosed, dpOpened;
  int[] paPow;

  int calc() {
    dpClosed = new int[K + 1][K + 1];
    dpOpened = new int[K + 1][K + 1];
    paPow = new int[K + 3];
    paPow[0] = 1;
    for (int i = 1; i < paPow.length; ++i) {
      paPow[i] = MOD.mul(paPow[i - 1], pa);
    }
//System.out.println(IntArrayUtils.toDisplay(paPow));
    dpClosed[K][0] = 1;
    for (int k = K; k >= 0; --k) {
      for (int aCnt = 0; aCnt <= K; ++aCnt) {
        int dp = MOD.add(dpOpened[k][aCnt], dpClosed[k][aCnt]);
        if (dp == 0) continue;
        // add a
        if (aCnt != K) {
          dpOpened[k][aCnt + 1] = MOD.add(
              dpOpened[k][aCnt + 1],
              MOD.mul(dp, pa));
        }
        // add b
        if (aCnt > 0 && k >= aCnt) {
          dpClosed[k - aCnt][aCnt] = MOD.add(
              dpClosed[k - aCnt][aCnt],
              MOD.mul(dp, pb));
        }
      }
    }
    int res = 0;
    for (int k = 0; k <= K; ++k) for (int aCnt = 0; aCnt <= K; ++aCnt) if (dpClosed[k][aCnt] > 0) {
      int dp = dpClosed[k][aCnt];
      if (k == 0) {
        res = MOD.add(res, MOD.mul(dp, K));
      } else if (k < aCnt) {
        res = MOD.add(
            res,
            MOD.mul(
                dp,
                MOD.add(
                    K + aCnt - k,
                    MOD.mul(pa, pbInv))));
      } else {
        res = MOD.add(
            res,
            MOD.mul(
                dp,
                MOD.add(
                    MOD.mul(paPow[k - aCnt + 1], K + 1),
                    MOD.mul(paPow[k - aCnt + 2], pbInv))));
      }
    }
    return res;
  }
}
