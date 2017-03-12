package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.hash.DoubleHash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TaskD {
  static final int HEAD = 17;
  static final int TAIL = 10007;

  int n, m;
  Tree tree;
  DoubleHash[] hash;
  SmartMap cntMap;
  int res, resIdx;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = (n - 1) << 1;
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    hash = new DoubleHash[m];
    for (int i = 0; i < m; ++i) {
      hash[i] = DoubleHash.empty();
    }
    hashList = new ArrayList<>(n);
    cntMap = new SmartMap();
    dfs(0, -1, -1);
    headHash = new DoubleHash[n + 1];
    tailHash = new DoubleHash[n + 1];
    for (int i = 0; i <= n; ++i) {
      headHash[i] = DoubleHash.empty();
      tailHash[i] = DoubleHash.empty();
    }
    res = -1;
    dfsAgain(0, -1, null);
    out.println(resIdx + 1);
  }

  List<DoubleHash> hashList;

  void dfs(int u, int parent, int parentEdgeIdx) {
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfs(v, u, edgeIdx);
    }
    if (parentEdgeIdx < 0) return;
    hashList.clear();
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      hashList.add(hash[edgeIdx]);
    }
    Collections.sort(hashList);
    DoubleHash hash = this.hash[parentEdgeIdx];
    hash.init(HEAD);
    for (DoubleHash value : hashList) {
      hash.addLast(value);
    }
    hash.addLast(TAIL);
    if (u > 0) cntMap.add(hash);
  }

  DoubleHash[] headHash, tailHash;

  void dfsAgain(int u, int parent, DoubleHash parentHash) {
    if (res < cntMap.cnt) {
      res = cntMap.cnt;
      resIdx = u;
    }
    hashList.clear();
    if (u > 0) hashList.add(parentHash);
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      hashList.add(hash[edgeIdx]);
    }
    Collections.sort(hashList);
    headHash[0].init(HEAD);
    for (int i = 0; i < hashList.size(); ++i) {
      headHash[i + 1].init(headHash[i], hashList.get(i));
    }
    tailHash[hashList.size()].init(TAIL);
    for (int i = hashList.size() - 1; i >= 0; --i) {
      tailHash[i].init(hashList.get(i), tailHash[i + 1]);
    }
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      int idx = Collections.binarySearch(hashList, hash[edgeIdx]);
      if (idx < 0) throw new IllegalArgumentException();
      hash[edgeIdx ^ 1].init(headHash[idx], tailHash[idx + 1]);
    }
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      cntMap.remove(hash[edgeIdx]);
      cntMap.add(hash[edgeIdx ^ 1]);
      dfsAgain(v, u, hash[edgeIdx ^ 1]);
      cntMap.add(hash[edgeIdx]);
      cntMap.remove(hash[edgeIdx ^ 1]);
    }
  }

  class SmartMap {
    int cnt;
    Map<DoubleHash, Integer> cntMap;

    SmartMap() {
      cnt = 0;
      cntMap = new TreeMap<>();
    }

    void add(DoubleHash hash) {
      int oldCnt = cntMap.getOrDefault(hash, 0);
      cntMap.put(hash, oldCnt + 1);
      if (oldCnt == 0) ++cnt;
    }

    void remove(DoubleHash hash) {
      int oldCnt = cntMap.getOrDefault(hash, 0);
      if (oldCnt == 0) throw new IllegalArgumentException();
      cntMap.put(hash, oldCnt - 1);
      if (oldCnt == 1) --cnt;
    }
  }
}
