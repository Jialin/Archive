package main;

import template.collections.trie.AbstractTrie;
import template.graph.weighted.IntEdgeWeightedDirectedGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskF {
  char[] token = new char[2];

  int n, n2;
  IntEdgeWeightedDirectedGraph tree;
  Trie trie;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    n2 = n << 1;
    tree = new IntEdgeWeightedDirectedGraph(n, n - 1);
    for (int i = 1; i < n; ++i) {
      int x = in.nextInt() - 1;
      int y = in.nextInt() - 1;
      in.next(token);
      tree.add(x, y, token[0] - 'a');
    }
    trie = new Trie(26, n2);
    buildTrie(0, 0);
System.out.println(trie);
  }

  void buildTrie(int trieIdx, int treeIdx) {
    for (int edgeIdx = tree.lastOut(treeIdx); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int nxtTrieIdx = trie.add(trieIdx, tree.edgeWeight(edgeIdx));
      buildTrie(nxtTrieIdx, tree.toIdx(edgeIdx));
    }
  }

  class Trie extends AbstractTrie {

    public Trie(int letterCapacity, int nodeCapacity) {
      super(letterCapacity, nodeCapacity);
    }

    @Override
    public void createSubclass(int letterCapacity, int nodeCapacity) {}

    @Override
    public void initNode(int idx, int parent, int parentLetter) {}

    @Override
    public String toString(int nodeIdx) {
      return null;
    }
  }
}
