package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.fft.FourierTransformerModular;
import template.numbertheory.number.IntModular;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

public class TaskG {
  static IntModular MOD = new IntModular(998244353);

  FourierTransformerModular FFT;
  int m, k;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    FFT = new FourierTransformerModular(MOD.mod, 31);
    int n = in.nextInt();
    k = in.nextInt();
    m = IntUtils.nextPow2((k << 1) | 1);
    create();
    int idx = calc(n);
    merge(coef[idx], coef[(idx + 1) & 3], coef[(idx + 2) & 3]);
    int[] res = coef[(idx + 2) & 3];
    out.print(res[1]);
    for (int i = 2; i <= k; ++i) {
      out.print(' ');
      out.print(res[i]);
    }
    out.println();
  }

  int[][] coef;
  int[] tmp1, tmp2, tmp11, tmp22, tmp12;

  void create() {
    coef = new int[4][m];
    tmp1 = new int[m];
    tmp2 = new int[m];
    tmp11 = new int[m];
    tmp12 = new int[m];
    tmp22 = new int[m];
  }

  int calc(int n) {
    if (n == 1) {
      int[] a = coef[0], b = coef[1];
      Arrays.fill(a, 0, m, 0);
      Arrays.fill(b, 1, m, 0); b[0] = 1;
      return 0;
    }
    if ((n & 1) > 0) {
      int idx = calc(n - 1);
      merge(coef[idx], coef[(idx + 1) & 3], coef[(idx + 2) & 3]);
      return (idx + 1) & 3;
    } else {
      int idx = calc(n >> 1);
      int size = Math.min(m, IntUtils.nextPow2(n));
      int[] a = coef[idx], b = coef[(idx + 1) & 3], c = coef[(idx + 2) & 3], d = coef[(idx + 3) & 3];
      for (int i = 0; i < m; ++i) {
        tmp1[i] = a[i];
        tmp2[i] = b[i];
      }
      FFT.fft(size, tmp1, false);
      FFT.fft(size, tmp2, false);
      FFT.pointwiseProduct(size, tmp1, tmp1, tmp11);
      FFT.pointwiseProduct(size, tmp1, tmp2, tmp12);
      FFT.pointwiseProduct(size, tmp2, tmp2, tmp22);
      FFT.fft(size, tmp11, true);
      FFT.fft(size, tmp12, true);
      FFT.fft(size, tmp22, true);
      Arrays.fill(c, k + 1, m, 0);
      Arrays.fill(d, k + 1, m, 0);
      c[0] = d[0] = 1;
      for (int i = 1; i <= k; ++i) {
        c[i] = MOD.add(tmp22[i], tmp11[i - 1]);
        d[i] = MOD.add(MOD.add(MOD.add(tmp22[i], tmp22[i - 1]), tmp12[i - 1]), tmp12[i - 1]);
      }
      return (idx + 2) & 3;
    }
  }

  void merge(int[] a, int[] b, int[] c) {
    Arrays.fill(c, k + 1, m, 0);
    c[0] = 1;
    for (int i = 1; i <= k; ++i) {
      c[i] = MOD.add(MOD.add(b[i], b[i - 1]), a[i - 1]);
    }
  }
}
