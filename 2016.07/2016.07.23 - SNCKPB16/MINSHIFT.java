package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import static java.lang.Math.max;

public class MINSHIFT {

  private static final int MAXL = 4;

  private char[] s;
  private NoWrapComparator noWrapComparator = new NoWrapComparator();

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    s = in.next().toCharArray();
    IntervalTree<Integer, IntervalTreeNode> intervalTree = new IntervalTree<>(
        IntegerArrayUtils.range(s.length), () -> new IntervalTreeNode());
    List<Integer> candidates = new ArrayList<>(MAXL + 1);
    for (int query = in.nextInt(); query > 0; --query) {
      int operation = in.nextInt();
      if (operation == 0) {
        int j = in.nextInt();
        s[j - 1] = in.next().charAt(0);
        intervalTree.update(max(j - MAXL, 0), j);
      } else {
        int l = in.nextInt() - 1;
        int r = in.nextInt();
        candidates.clear();
        if (l < r - MAXL) {
          candidates.add(intervalTree.calc(l, r - MAXL).index);
        }
        for (int i = max(l, r - MAXL); i < r; ++i) {
          candidates.add(i);
        }
        Collections.sort(candidates, new WrapComparator(l, r));
        int res = candidates.get(0) + in.nextInt() - 1;
        if (res >= r) {
          res = res - r + l;
        }
        out.println(s[res]);
      }
    }
  }

  public class NoWrapComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
      int i = o1.intValue();
      int j = o2.intValue();
      if (i == j) return 0;
      for ( ; i < s.length && j < s.length; ++i, ++j) {
        if (s[i] < s[j]) {
          return -1;
        }
        if (s[i] > s[j]) {
          return 1;
        }
      }
      return i >= s.length ? -1 : 1;
    }
  }

  public class WrapComparator implements Comparator<Integer> {

    int fromIndex;
    int toIndex;

    public WrapComparator(int fromIndex, int toIndex) {
      this.fromIndex = fromIndex;
      this.toIndex = toIndex;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
      int i = o1.intValue();
      int j = o2.intValue();
      for (int k = fromIndex; k < toIndex; ++k) {
        if (s[i] < s[j]) {
          return -1;
        }
        if (s[i] > s[j]) {
          return 1;
        }
        i = i + 1 == toIndex ? fromIndex : i + 1;
        j = j + 1 == toIndex ? fromIndex : j + 1;
      }
      return 0;
    }
  }

  class IntervalTreeNode implements IntervalTreeNodeInterface<Integer, IntervalTreeNode> {

    int index;

    @Override
    public void init() {
      index = -1;
    }

    @Override
    public void init(Integer initialValue) {
      index = initialValue;
    }

    @Override
    public void update(int updateValue) {
      index = updateValue;
    }

    @Override
    public void append(IntervalTreeNode o) {
      if (index == -1) {
        index = o.index;
      } else {
        index = noWrapComparator.compare(index, o.index) < 0 ? index : o.index;
      }
    }
  }


  public class IntervalTree<INIT, NODE extends IntervalTreeNodeInterface<INIT, NODE>> {

    public IntervalTree(INIT[] initValues, Supplier<NODE> nodeFactory) {
      this(initValues, 0, initValues.length, nodeFactory);
    }

    public IntervalTree(
        INIT[] initValues,
        int fromIndex,
        int toIndex,
        Supplier<NODE> nodeFactory) {

      this.n = toIndex - fromIndex;
      this.nodeFactory = nodeFactory;
      this.nodes = new ArrayList<>(n << 2);
      for (int i = n << 2; i > 0; --i) {
        nodes.add(nodeFactory.get());
      }
      this.initValues = initValues;
      this.fromIndex = fromIndex;
      init(1, 0, n);
    }

    public void update(int left, int right) {
      this.left = left;
      this.right = right;
      update(1, 0, n);
    }

    public NODE calc(int left, int right) {
      NODE res = nodeFactory.get();
      res.init();
      this.left = left;
      this.right = right;
      calc(res, 1, 0, n);
      return res;
    }

    private int n;
    private List<NODE> nodes;
    private Supplier<NODE> nodeFactory;
    private INIT[] initValues;
    private int fromIndex;
    private int left, right;

    private void init(int nodeIndex, int lower, int upper) {
      if (lower + 1 == upper) {
        nodes.get(nodeIndex).init(initValues[fromIndex + lower]);
        return;
      }
      int medium = (lower + upper) >> 1;
      init(toLeft(nodeIndex), lower, medium);
      init(toRight(nodeIndex), medium, upper);
      merge(nodeIndex);
    }

    private void update(int nodeIndex, int lower, int upper) {
      if (right <= lower || upper <= left) {
        return;
      }
      if (lower + 1 == upper) {
        nodes.get(nodeIndex).update(lower);
        return;
      }
      int medium = (lower + upper) >> 1;
      update(toLeft(nodeIndex), lower, medium);
      update(toRight(nodeIndex), medium, upper);
      merge(nodeIndex);
    }

    private void calc(NODE res, int nodeIndex, int lower, int upper) {
      if (left <= lower && upper <= right) {
        res.append(nodes.get(nodeIndex));
        return;
      }
      int medium = (lower + upper) >> 1;
      if (left < medium) {
        calc(res, toLeft(nodeIndex), lower, medium);
      }
      if (medium < right) {
        calc(res, toRight(nodeIndex), medium, upper);
      }
    }

    private void merge(int nodeIndex) {
      NODE node = nodes.get(nodeIndex);
      node.init();
      node.append(nodes.get(toLeft(nodeIndex)));
      node.append(nodes.get(toRight(nodeIndex)));
    }

    private int toLeft(int nodeIndex) {
      return nodeIndex << 1;
    }

    private int toRight(int nodeIndex) {
      return (nodeIndex << 1) | 1;
    }
  }

  public interface IntervalTreeNodeInterface<INIT, SELF extends IntervalTreeNodeInterface<INIT, SELF>> {

    void init();

    void init(INIT initialValue);

    void update(int updateValue);

    void append(SELF o);
  }
}
