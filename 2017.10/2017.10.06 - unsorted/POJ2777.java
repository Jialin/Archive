package main;

import template.collections.intervaltree.AbstractIntervalTreeWithLazyPropagation;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class POJ2777 {
  int n;
  IT it;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    in.next();
    it = new IT(n);
    it.update(0, n, 0);
    for (int remQuery = in.nextInt(); remQuery > 0; --remQuery) {
      int op = in.nextNonSpaceChar();
      int x = in.nextInt(), y = in.nextInt();
      int lower = Math.min(x, y) - 1;
      int upper = Math.max(x, y);
      if (op == 'C') {
        it.update(lower, upper, in.nextInt() - 1);
      } else {
        out.println(it.calc(lower, upper));
      }
    }
  }

  class IT extends AbstractIntervalTreeWithLazyPropagation {

    int[] label;
    int[] colorMask;

    public IT(int leafCapacity) {
      super(leafCapacity);
    }

    @Override
    public void createStorage(int nodeCapacity) {
      label = new int[nodeCapacity];
      colorMask = new int[nodeCapacity];
    }

    @Override
    public void initLeaf(int idxInTree, int idx) {
      colorMask[idxInTree] = 0;
    }

    @Override
    public void copyForCalc(int fromIdxInTree, int toIdxInTree) {
      colorMask[toIdxInTree] = colorMask[fromIdxInTree];
    }

    int updateColor;

    @Override
    public void assignFakeLazyPropagation() {
      label[0] = 1 << updateColor;
    }

    @Override
    public void pushLazyPropagation(int fromIdxInTree, int toIdxInTree) {
      if (label[fromIdxInTree] > 0) {
        int label = this.label[fromIdxInTree];
        this.label[toIdxInTree] = label;
        colorMask[toIdxInTree] = label;
      }
    }

    @Override
    public void clearLazyPropagation(int idxInTree) {
      label[idxInTree] = 0;
    }

    @Override
    public void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree) {
      colorMask[idxInTree] = colorMask[leftIdxInTree] | colorMask[rightIdxInTree];
    }

    @Override
    public void clearNodeForCalc(int idxInTree) {
      colorMask[idxInTree] = 0;
    }

    @Override
    public String toDisplay(int idxInTree) {
      return null;
    }

    void update(int lower, int upper, int color) {
      updateColor = color;
      updateRange(lower, upper);
    }

    int calc(int lower, int upper) {
      calcRange(lower, upper);
      return Integer.bitCount(colorMask[0]);
    }
  }
}
