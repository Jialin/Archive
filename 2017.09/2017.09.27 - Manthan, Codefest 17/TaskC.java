package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

import java.util.Arrays;

public class TaskC {
  static IntModular MOD = new IntModular();

  int vertexCnt, typeCnt, kType, kCnt;
  Tree tree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    vertexCnt = in.nextInt();
    typeCnt = in.nextInt();
    tree = new Tree(vertexCnt);
    tree.init(vertexCnt);
    for (int i = 1; i < vertexCnt; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    kType = in.nextInt();
    kCnt = in.nextInt();
    less = new int[kCnt + 1][vertexCnt];
    equal = new int[kCnt + 1][vertexCnt];
    greater = new int[kCnt + 1][vertexCnt];
    tmpLess = new int[2][kCnt + 1];
    tmpEqual = new int[2][kCnt + 1];
    tmpGreater = new int[2][kCnt + 1];
    dfs(0, -1);
    int res = 0;
    for (int cnt = 0; cnt <= kCnt; ++cnt) {
      res = MOD.add(MOD.add(MOD.add(
          res,
          less[cnt][0]),
          equal[cnt][0]),
          greater[cnt][0]);
    }
    out.println(res);
  }

  int[][] less, equal, greater;
  int[][] tmpLess, tmpEqual, tmpGreater;

  void dfs(int u, int parent) {
    tree.forEachOutNodes(u, v -> {
      if (v != parent) dfs(v, u);
    });
    int t = 0;
    Arrays.fill(tmpLess[t], 0); tmpLess[t][0] = kType - 1;
    Arrays.fill(tmpEqual[t], 0); tmpEqual[t][1] = 1;
    Arrays.fill(tmpGreater[t], 0); tmpGreater[t][0] = typeCnt - kType;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      Arrays.fill(tmpLess[t ^ 1], 0);
      Arrays.fill(tmpEqual[t ^ 1], 0);
      Arrays.fill(tmpGreater[t ^ 1], 0);
      for (int oldCnt = 0; oldCnt <= kCnt; ++oldCnt) {
        for (int delta = 0, newCnt = oldCnt; newCnt <= kCnt; ++delta, ++newCnt) {
          tmpLess[t ^ 1][newCnt] = MOD.add(
              tmpLess[t ^ 1][newCnt],
              MOD.mul(
                  tmpLess[t][oldCnt],
                  MOD.add(MOD.add(
                      less[delta][v],
                      equal[delta][v]),
                      greater[delta][v])));
          tmpEqual[t ^ 1][newCnt] = MOD.add(
              tmpEqual[t ^ 1][newCnt],
              MOD.mul(
                  tmpEqual[t][oldCnt],
                  less[delta][v]));
          tmpGreater[t ^ 1][newCnt] = MOD.add(
              tmpGreater[t ^ 1][newCnt],
              MOD.mul(
                  tmpGreater[t][oldCnt],
                  MOD.add(
                      less[delta][v],
                      greater[delta][v])));
        }
      }
      t ^= 1;
    }
    for (int cnt = 0; cnt <= kCnt; ++cnt) {
      less[cnt][u] = tmpLess[t][cnt];
      equal[cnt][u] = tmpEqual[t][cnt];
      greater[cnt][u] = tmpGreater[t][cnt];
    }
  }
}
