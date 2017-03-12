package main;

import template.collections.treap.IntAbstractTreapNode;
import template.collections.treap.IntTreap;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskF {
  int n;
  TShirt[] tshirts;

  int m;
  Query[] queries;
  IntTreapNode[] nodes;
  TShirtTreap treap;
  int[] answer;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    tshirts = new TShirt[n];
    for (int i = 0; i < n; ++i) {
      tshirts[i] = new TShirt();
      tshirts[i].price = in.nextInt();
      tshirts[i].priority = in.nextInt();
    }
    Arrays.sort(tshirts);
    m = in.nextInt();
    queries = new Query[m];
    for (int i = 0; i < m; ++i) {
      queries[i] = new Query(in.nextInt(), i);
    }
    Arrays.sort(queries);
    nodes = new IntTreapNode[m];
    treap = new TShirtTreap();
    treap.init();
    for (int i = 0; i < m; ++i) {
      nodes[i] = new IntTreapNode(queries[i]);
      treap.add(nodes[i]);
    }
    Update update = new Update();
    update.increase = 1;
    for (TShirt tshirt : tshirts) {
      update.shiftLeft = tshirt.price;
      treap.update(tshirt.price, update);
    }
    treap.pushAllDown();
    answer = new int[m];
    for (int i = 0; i < m; ++i) {
      IntTreapNode node = nodes[i];
      answer[node.idx] = node.cnt + node.increase;
    }
    out.println(answer);
  }
}

class TShirt implements Comparable<TShirt> {
  int price, priority;

  @Override
  public int compareTo(TShirt o) {
    if (priority != o.priority) return o.priority - priority;
    return price - o.price;
  }
}

class Update {
  int shiftLeft, increase;
}

class Query implements Comparable<Query> {

  int budget, idx;

  Query(int budget, int idx) {
    this.budget = budget;
    this.idx = idx;
  }


  @Override
  public int compareTo(Query o) {
    return budget - o.budget;
  }
}

class IntTreapNode extends IntAbstractTreapNode<Update, IntTreapNode> {

  int idx;
  int cnt;
  int shiftLeft, increase;

  IntTreapNode(Query query) {
    super(query.budget);
    this.idx = query.idx;
  }

  @Override
  public int getKey() {
    return key - shiftLeft;
  }

  @Override
  public void update(Update update) {
    accept(update.shiftLeft, update.increase);
  }

  @Override
  public void pushDown() {
    if (left != null) left.accept(shiftLeft, increase);
    if (right != null) right.accept(shiftLeft, increase);
    key -= shiftLeft;
    cnt += increase;
    shiftLeft = increase = 0;
  }

  @Override
  public void merge() {}

  void accept(int shiftLeft, int increase) {
    this.shiftLeft += shiftLeft;
    this.increase += increase;
  }
}

class TShirtTreap extends IntTreap<Update, IntTreapNode> {

  @SuppressWarnings({"unchecked"})
  public void update(int lowerKey, Update update) {
    if (root == null) return;
    Object[] res = root.split(lowerKey);
    IntTreapNode left = (IntTreapNode) res[0];
    IntTreapNode right = (IntTreapNode) res[1];
    if (right == null) {
      root = left;
      return;
    }
    right.update(update);
    root = slowMerge(left, right);
  }

  public IntTreapNode slowMerge(IntTreapNode left, IntTreapNode right) {
    if (left == null) return right;
    if (right == null) return left;
    IntTreapNode leftRight = left.rightMost();
    IntTreapNode rightLeft = right.leftMost();
    while (rightLeft.getKey() < leftRight.getKey()) {
      Object[] res = right.split(rightLeft.getKey() + 1);
      IntTreapNode medium = (IntTreapNode) res[0];
      IntTreapNode newRight = (IntTreapNode) res[1];
      left = left.add(medium);
      right = newRight;
      if (left == null || right == null) break;
      leftRight = left.rightMost();
      rightLeft = right.leftMost();
    }
    return left == null ? right : left.merge(right);
  }
}
