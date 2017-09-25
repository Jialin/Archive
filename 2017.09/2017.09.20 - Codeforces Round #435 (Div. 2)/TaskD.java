package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;
import template.truth.Truth;

import java.util.Arrays;
import java.util.Random;

public class TaskD {

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
//    Random random = new Random(97);
//    for (int task = 0; task < 10000; ++task) {
//      int n = random.nextInt(100) + 2;
//      Server server = new MockServer(n, null, null);
//      calc(n, server);
//    }
    int n = in.nextInt();
    calc(n, new Server(n, in, out));
  }

  int zeroPos, onePos;

  // len:         '000...000'       prevH -> zero: [len-prevH]  one: [prevH]
  // len0, len1:  '11..11' '00.00'      H ->
  //              z0 o0    z1 o1
  // o0+o1=prevH
  // z0+o1=H
  // z0-o0=z0-(len0-z0)=H-prevH
  //
  // z0=(len0+H-prevH)/2
  void calc(int n, Server server) {
    zeroPos = onePos = -1;
    int allZeroD = calcFirst(n, server);
    int lower = 0, upper = n;
    while (lower + 1 < upper) {
      int medium = (lower + upper) >> 1;
      int D = server.query(lower, medium);
      int zeroCnt = medium - lower + D - allZeroD;
//System.out.printf("[%d,%d): medium=%d D=%d zeroCnt=%d\n", lower, upper, medium, D, zeroCnt);
      Truth.assertThat(IntUtils.isEven(zeroCnt));
      zeroCnt >>= 1;
      int oneCnt = medium - lower - zeroCnt;
      if ((zeroPos < 0 && zeroCnt > 0) || (onePos < 0 && oneCnt > 0)) {
        upper = medium;
      } else {
        lower = medium;
      }
    }
    if (zeroPos < 0) {
      zeroPos = lower;
    } else {
      onePos = lower;
    }
    server.answer(zeroPos, onePos);
  }

  int calcFirst(int n, Server server) {
    int res = server.query(0, 0);
    if (server.query(n - 1, n) < res) {
      onePos = n - 1;
    } else {
      zeroPos = n - 1;
    }
    return res;
  }

  static class Server {
    final char[] s;
    final QuickScanner in;
    final QuickWriter out;

    Server(int n, QuickScanner in, QuickWriter out) {
      this.in = in;
      this.out = out;
      s = new char[n];
      Arrays.fill(s, '0');
    }

    int query(int lower, int upper) {
//System.out.printf("[%d,%d)\n", lower, upper);
      out.print("? ");
      for (int i = lower; i < upper; ++i) s[i] = '1';
      out.println(s);
      for (int i = lower; i < upper; ++i) s[i] = '0';
      out.flush();
      return in.nextInt();
    }

    void answer(int zero, int one) {
      out.printf("! %d %d\n", zero + 1, one + 1);
      out.flush();
    }
  }

  static class MockServer extends Server {

    static final Random RAND = new Random(101);
    static final int MAX_CNT = 15;

    int n, cnt;
    boolean[] isZero;

    MockServer(int n, QuickScanner in, QuickWriter out) {
      super(n, in, out);
      this.n = n;
      cnt = 0;
      isZero = new boolean[n];
      boolean zero, one;
      do {
        for (int i = 0; i < n; ++i) {
          isZero[i] = RAND.nextBoolean();
        }
        zero = one = false;
        for (int i = 0; i < n; ++i) {
          if (isZero[i]) zero = true;
          else one = true;
        }
      } while (!zero || !one);
//System.out.print("==");
//for (int i = 0; i < n; ++i) System.out.print(isZero[i] ? '0' : '1');
//System.out.println("==");
    }

    @Override
    int query(int lower, int upper) {
      ++cnt;
      if (cnt > MAX_CNT) throw new IllegalArgumentException();
      int res = 0;
      for (int i = 0; i < n; ++i) {
        if (lower <= i && i < upper) {
          if (isZero[i]) ++res;
        } else {
          if (!isZero[i]) ++res;
        }
      }
      return res;
    }

    @Override
    void answer(int zeroPos, int onePos) {
      Truth.assertThat(isZero[zeroPos]);
      Truth.assertThat(!isZero[onePos]);
    }
  }
}
