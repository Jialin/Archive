package main;



import template.collections.intervaltree.AbstractIntervalTree;
import template.collections.intervaltree.impl.IntMaxIntervalTree;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskF {

  private static int CUT = 50;

  int n, query;
  String[] s;

  int[] H;
  IntMaxIntervalTree maxIT;
  AreaIT areaIT;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    query = in.nextInt();
    s = in.next(n);
    initH();
    initMaxIT();
    initAreaIT();
    for (int remQ = query; remQ > 0; --remQ) {
      int op = in.nextInt();
      if (op == 1) {
        int lower = in.nextInt() - 1;
        int upper = in.nextInt();
        out.println(calc(lower, upper));
      } else {
        int idx = in.nextInt() - 1;
        s[idx] = in.next();
        update(idx);
      }
    }
  }

  void initH() {
    H = new int[n - 1];
    for (int i = 1; i < n; ++i) H[i - 1] = calcLCP(s[i - 1], s[i]);
  }

  int calcLCP(String a, String b) {
    int length = Math.min(a.length(), b.length());
    for (int i = 0; i < length; ++i) if (a.charAt(i) != b.charAt(i)) {
      return i;
    }
    return length;
  }

  void initMaxIT() {
    int[] initLength = new int[n];
    for (int i = 0; i < n; ++i) initLength[i] = s[i].length();
    maxIT = new IntMaxIntervalTree(n);
    maxIT.init(n, initLength);
  }

  void initAreaIT() {
    areaIT = new AreaIT(n - 1);
    areaIT.init(n - 1, H);
  }

  int calc(int lower, int upper) {
    int res = maxIT.calc(lower, upper);
    if (lower < upper) {
      res = Math.max(res, areaIT.calc(lower, upper - 1));
    }
    return res;
  }

  void update(int idx) {
    maxIT.update(idx, s[idx].length());
    if (n == 1) return;
    int lower, upper;
    if (idx == 0) {
      H[idx] = calcLCP(s[idx], s[idx + 1]);
      lower = idx;
      upper = idx + 1;
    } else if (idx == n - 1) {
      H[idx - 1] = calcLCP(s[idx - 1], s[idx]);
      lower = idx - 1;
      upper = idx;
    } else {
      H[idx - 1] = calcLCP(s[idx - 1], s[idx]);
      H[idx] = calcLCP(s[idx], s[idx + 1]);
      lower = idx - 1;
      upper = idx + 1;
    }
    areaIT.update(lower, upper);
  }

  static class AreaIT extends AbstractIntervalTree {

    int[] H;

    IntArrayList[] leftH, leftD;
    IntArrayList[] rightH, rightD;
    int[] best;

    public AreaIT(int leafCapacity) {
      super(leafCapacity);
    }

    @Override
    public void createStorage(int nodeCapacity) {
      leftH = new IntArrayList[nodeCapacity];
      leftD = new IntArrayList[nodeCapacity];
      rightH = new IntArrayList[nodeCapacity];
      rightD = new IntArrayList[nodeCapacity];
      best = new int[nodeCapacity];
      for (int i = 0; i < nodeCapacity; ++i) {
        leftH[i] = new IntArrayList();
        leftD[i] = new IntArrayList();
        rightH[i] = new IntArrayList();
        rightD[i] = new IntArrayList();
      }
    }

    @Override
    public void initLeaf(int nodeIdx, int idx) {
      leftH[nodeIdx].clear();
      leftD[nodeIdx].clear();
      leftH[nodeIdx].add(H[idx]);
      leftD[nodeIdx].add(1);
      rightH[nodeIdx].clear();
      rightD[nodeIdx].clear();
      rightH[nodeIdx].add(H[idx]);
      rightD[nodeIdx].add(1);
      best[nodeIdx] = H[idx] << 1;
    }

    @Override
    public void merge(int leftIdx, int rightIdx, int idx) {
      mergeOneSide(
          leftH[idx], leftD[idx],
          leftH[leftIdx], leftD[leftIdx],
          leftH[rightIdx], leftD[rightIdx]);
      mergeOneSide(
          rightH[idx], rightD[idx],
          rightH[rightIdx], rightD[rightIdx],
          rightH[leftIdx], rightD[leftIdx]);
      best[idx] = Math.max(Math.max(Math.max(Math.max(
          best[leftIdx],
          best[rightIdx]),
          calcTwoSide(
              rightH[leftIdx], rightD[leftIdx],
              leftH[rightIdx], leftD[rightIdx])),
          calcOneSide(leftH[idx], leftD[idx])),
          calcOneSide(rightH[idx], rightD[idx]));
    }

    @Override
    public void copyForCalc(int sourceIdx, int destIdx) {
      leftD[destIdx].clear();
      leftD[destIdx].addAll(leftD[sourceIdx]);
      leftH[destIdx].clear();
      leftH[destIdx].addAll(leftH[sourceIdx]);
      rightD[destIdx].clear();
      rightD[destIdx].addAll(rightD[sourceIdx]);
      rightH[destIdx].clear();
      rightH[destIdx].addAll(rightH[sourceIdx]);
      best[destIdx] = best[sourceIdx];
    }

    @Override
    public void clearNode(int idx) {
      leftD[idx].clear();
      leftH[idx].clear();
      rightD[idx].clear();
      rightH[idx].clear();
      best[idx] = 0;
    }

    @Override
    public String toDisplay(int idx) {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("best:%d\n", best[idx]));
      sb.append(String.format("leftD:%s\n", leftD[idx].toDisplay()));
      sb.append(String.format("leftH:%s\n", leftH[idx].toDisplay()));
      sb.append(String.format("rightD:%s\n", rightD[idx].toDisplay()));
      sb.append(String.format("rightH:%s\n", rightH[idx].toDisplay()));
      return sb.toString();
    }

    public void init(int n, int[] H) {
      this.H = H;
      super.init(n);
    }

    public void update(int lower, int upper) {
      for (int i = lower; i < upper; ++i) {
        initLeaf(i + leafCnt(), i);
      }
      populateRangeUpdateToRoot(lower, upper);
    }

    public int calc(int lower, int upper) {
      calcRange(lower, upper);
      return best[0];
    }

    private void mergeOneSide(
        IntArrayList resH, IntArrayList resD,
        IntArrayList leftH, IntArrayList leftD,
        IntArrayList rightH, IntArrayList rightD) {

      resH.clear();
      resD.clear();
      resH.addAll(leftH);
      resD.addAll(leftD);
      for (int i = 0; i < rightH.size; ++i) {
        int h = rightH.get(i);
        int d = rightD.get(i);
        if (h >= resH.peekLast()) {
          resD.add(resD.pollLast() + d);
        } else {
          resH.add(h);
          resD.add(d);
        }
      }
    }

    private int calcOneSide(IntArrayList H, IntArrayList D) {
      int sumD = 0;
      int res = 0;
      for (int i = 0; i < H.size; ++i) {
        sumD += D.get(i);
        res = Math.max(res, H.get(i) * (sumD + 1));
      }
      return res;
    }

    private int calcTwoSide(
        IntArrayList rightH, IntArrayList rightD,
        IntArrayList leftH, IntArrayList leftD) {

      int i = 0, j = 0;
      int sumD = 0;
      int res = 0;
      while (i < rightH.size && j < leftH.size) {
        int rh = rightH.get(i), lh = leftH.get(j);
        if (rh > lh) {
          sumD += rightD.get(i++);
          res = Math.max(res, rh * (sumD + 1));
        } else {
          sumD += leftD.get(j++);
          res = Math.max(res, lh * (sumD + 1));
        }
      }
      for ( ; i < rightH.size; ++i) {
        sumD += rightD.get(i);
        res = Math.max(res, rightH.get(i) * (sumD + 1));
      }
      for ( ; j < leftH.size; ++j) {
        sumD += leftD.get(j);
        res = Math.max(res, leftH.get(j) * (sumD + 1));
      }
      return res;
    }
  }
}
