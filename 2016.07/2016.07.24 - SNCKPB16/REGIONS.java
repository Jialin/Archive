package main;

import template.graph.order.BfsOrder;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.function.Consumer;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static template.numbertheory.ModularUtils.add;
import static template.numbertheory.ModularUtils.mul;

public class REGIONS {

  private static final int BASE = 1000003;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    for (int remainTaskCount = in.nextInt(); remainTaskCount > 0; --remainTaskCount) {
      int n = in.nextInt();
      long A = in.nextInt();
      long B = in.nextInt();
      long C = in.nextInt();
      long D = in.nextInt();
      long E = in.nextInt();
      BidirectionalGraph<Integer> tree = new BidirectionalGraph<>(n, n - 1, Integer[]::new);
      for (int i = 1; i < n; i++) {
        int end = i;
        int otherEnd = (int) ((A + B * (i - 1)) % i);
        int length = (int) ((C * i + D) % E);
        tree.add(end, otherEnd, length);
      }
      TreeNode[] treeNodes = new TreeNode[n];
      int[] bfsOrder = BfsOrder.calc(tree, 0);
      for (int i = n - 1; i >= 0; --i) {
        int u = bfsOrder[i];
        TreeNode uNode = new TreeNode();
        treeNodes[u] = uNode;
        for (int edgeIndex : tree.outgoingEdgeIndex(u)) {
          int v = tree.toIndex[edgeIndex];
          TreeNode vNode = treeNodes[v];
          if (vNode != null) {
            uNode.top2ChildDiameter.add(vNode.diameter(), v);
            uNode.top3Length.add(vNode.top3Length.top() + tree.info[edgeIndex], v);
          }
        }
      }
      boolean[] visited = new boolean[n];
      int[] parentEdgeIndex = new int[n];
      int[] maxLength = new int[n];
      int[] diameter = new int[n];
      int[] a = new int[n - 1];
      int[] b = new int[n - 1];
      Top2 top2 = new Top2();
      Consumer<Integer> consumer = (Integer length) -> top2.add(length);
      for (int i = 0; i < n; ++i) {
        int u = bfsOrder[i];
        visited[u] = true;
        TreeNode uNode = treeNodes[u];
        if (i > 0) {
          int index = parentEdgeIndex[u];
          int d1 = diameter[u];
          int d2 = uNode.diameter();
          a[index] = min(d1, d2);
          b[index] = max(d1, d2);
        }
        for (int edgeIndex : tree.outgoingEdgeIndex(u)) {
          int v = tree.toIndex[edgeIndex];
          if (!visited[v]) {
            top2.init();
            top2.add(maxLength[u]);
            uNode.top3Length.top2(v, consumer);
            parentEdgeIndex[v] = edgeIndex >> 1;
            maxLength[v] = max(maxLength[u], uNode.top3Length.top(v)) + tree.info[edgeIndex];
            diameter[v] = max(max(diameter[u], top2.top2Sum()), uNode.top2ChildDiameter.top(v));
          }
        }
      }
      int res = 0;
      for (int i = n - 2; i >= 0; --i) {
        res = add(mul(add(mul(res, BASE), b[i]), BASE), a[i]);
      }
      out.println(res);
    }
  }

  private class TreeNode {

    public Top2 top2ChildDiameter;
    public Top3 top3Length;

    public TreeNode() {
      this.top2ChildDiameter = new Top2();
      this.top3Length = new Top3();
    }

    public int diameter() {
      return max(top2ChildDiameter.top(), top3Length.top2Sum());
    }
  }

  public class Top2 {

    private static final int DEFAULT_ID = Integer.MIN_VALUE;
    private static final int FAKE_ID = DEFAULT_ID + 1;

    private int value1;
    private int id1;
    private int value2;

    public Top2() {
      init();
    }

    public void init() {
      this.value1 = 0;
      this.id1 = DEFAULT_ID;
      this.value2 = 0;
    }

    public void add(int value) {
      add(value, DEFAULT_ID);
    }

    public void add(int value, int id) {
      if (value > value1) {
        value2 = value1;
        value1 = value;
        id1 = id;
      } else {
        value2 = max(value2, value);
      }
    }

    public int top() {
      return top(FAKE_ID);
    }

    public int top(int excludeId) {
      return id1 == excludeId ? value2 : value1;
    }

    public int top2Sum() {
      return value1 + value2;
    }
  }

  public class Top3 {

    private static final int DEFAULT_ID = Integer.MIN_VALUE;
    private static final int FAKE_ID = DEFAULT_ID + 1;

    private int value1, id1;
    private int value2, id2;
    private int value3;

    public Top3() {
      this.value1 = 0; this.id1 = DEFAULT_ID;
      this.value2 = 0; this.id2 = DEFAULT_ID;
      this.value3 = 0;
    }

    public void add(int value, int id) {
      if (value > value1) {
        value3 = value2;
        value2 = value1; id2 = id1;
        value1 = value; id1 = id;
      } else if (value > value2) {
        value3 = value2;
        value2 = value; id2 = id;
      } else {
        value3 = max(value3, value);
      }
    }

    public int top() {
      return top(FAKE_ID);
    }

    public int top(int excludeId) {
      return id1 == excludeId ? value2 : value1;
    }

    public void top2(int excludeId, Consumer<Integer> consumer) {
      if (id1 == excludeId) {
        consumer.accept(value2);
        consumer.accept(value3);
      } else if (id2 == excludeId) {
        consumer.accept(value1);
        consumer.accept(value3);
      } else {
        consumer.accept(value1);
        consumer.accept(value2);
      }
    }

    public int top2Sum() {
      return top2Sum(FAKE_ID);
    }

    public int top2Sum(int excludeId) {
      if (id1 == excludeId) {
        return value2 + value3;
      } else if (id2 == excludeId) {
        return value1 + value3;
      } else {
        return value1 + value2;
      }
    }
  }
}
