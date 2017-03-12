package main;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskD {
  int n;
  int[] auctionIdx, auctionPrice;
  IntArrayList[] price;

  int m;
  int[] removedIdx;
  boolean[] removed;

  int winIdxCnt;
  int[] winIdx;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    auctionIdx = new int[n];
    auctionPrice = new int[n];
    price = new IntArrayList[n];
    for (int i = 0; i < n; ++i) {
      price[i] = new IntArrayList();
    }
    for (int i = 0; i < n; ++i) {
      auctionIdx[i] = in.nextInt() - 1;
      auctionPrice[i] = in.nextInt();
      price[auctionIdx[i]].add(auctionPrice[i]);
    }
    removed = new boolean[n];
    winIdx = new int[n];
    for (int i = n - 1; i >= 0; --i) if (!removed[auctionIdx[i]]) {
      int j = auctionIdx[i];
      removed[j] = true;
      winIdx[winIdxCnt++] = j;
    }
    Arrays.fill(removed, 0, n, false);
    removedIdx = new int[n];
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      m = in.nextInt();
      for (int i = 0; i < m; ++i) {
        removedIdx[i] = in.nextInt() - 1;
        removed[removedIdx[i]] = true;
      }
      int winIdxPnt = 0;
      for ( ; winIdxPnt < winIdxCnt && removed[winIdx[winIdxPnt]]; ++winIdxPnt) {}
      if (winIdxPnt >= winIdxCnt) {
        out.println("0 0");
      } else {
        int idx = winIdx[winIdxPnt++];
        for ( ; winIdxPnt < winIdxCnt && removed[winIdx[winIdxPnt]]; ++winIdxPnt) {}
        out.print(idx + 1);
        out.print(' ');
        int bound = 0;
        if (winIdxPnt < winIdxCnt) {
          int j = winIdx[winIdxPnt];
          bound = price[j].get(price[j].size() - 1);
        }
        int upperBound = IntArrayUtils.upperBound(price[idx].values, 0, price[idx].size(), bound);
        out.println(price[idx].get(upperBound));
      }
      for (int i = 0; i < m; ++i) {
        removed[removedIdx[i]] = false;
      }
    }
  }
}
