package main;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList2D;
import template.collections.queue.IntArrayQueue;
import template.graph.basic.DirectedGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

public class TaskF {

  static int BIT = 13;

  int vertexCnt;
  int edgeCnt;
  int queryCnt;
  DirectedGraph graph;
  IntArrayList2D queries;
  int[] fromIdx;
  int[] toIdx;
  int[] stepIdx;
  int[] answer;
  boolean[] reachToSink;
  IntArrayQueue q;
  int[] distToSink;
  int[][] nxtNode;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    vertexCnt = in.nextInt();
    edgeCnt = in.nextInt();
    queryCnt = in.nextInt();
    graph = new DirectedGraph(vertexCnt, edgeCnt);
    for (int i = 0; i < edgeCnt; ++i) {
      graph.add(in.nextInt() - 1, in.nextInt() - 1);
    }

    fromIdx = new int[queryCnt];
    toIdx = new int[queryCnt];
    stepIdx = new int[queryCnt];
    in.nextInts(queryCnt, fromIdx, toIdx, stepIdx);
    IntArrayUtils.decreaseOne(fromIdx);
    IntArrayUtils.decreaseOne(toIdx);
    IntArrayUtils.decreaseOne(stepIdx);
    queries = new IntArrayList2D(vertexCnt, queryCnt);
    queries.init(vertexCnt);
    for (int i = 0; i < queryCnt; ++i) {
      queries.add(toIdx[i], i);
    }
    answer = new int[queryCnt];
    for (int i = 0; i < vertexCnt; ++i) {
      calc(i);
    }
    out.println(answer, '\n');
  }

  void calc(int sink) {
    if (reachToSink == null) {
      reachToSink = new boolean[vertexCnt];
      q = new IntArrayQueue(vertexCnt);
      nxtNode = new int[BIT][vertexCnt];
      distToSink = new int[vertexCnt];
    }
    Arrays.fill(reachToSink, false);
    for (int bit = 0; bit < BIT; ++bit) {
      Arrays.fill(nxtNode[bit], Integer.MAX_VALUE);
    }
    Arrays.fill(distToSink, Integer.MAX_VALUE);

    // init reachToSink
    q.clear();
    reachToSink[sink] = true;
    for (q.add(sink); q.isNotEmpty(); ) {
      int u = q.poll();
      graph.forEachInNodes(u, v -> {
        if (!reachToSink[v]) {
          reachToSink[v] = true;
          q.add(v);
        }
      });
    }

    // init nxtNode
    for (int i = 0; i < vertexCnt; ++i)
      if (i != sink && reachToSink[i]) {
        final int u = i;
        graph.forEachOutNodes(i, v -> {
          if (reachToSink[v]) {
            nxtNode[0][u] = Math.min(nxtNode[0][u], v);
          }
        });
      }
    for (int bit = 0, span = 2; span < vertexCnt; ++bit, span <<= 1) {
      for (int u = 0; u < vertexCnt; ++u)
        if (nxtNode[bit][u] != Integer.MAX_VALUE) {
          nxtNode[bit + 1][u] = nxtNode[bit][nxtNode[bit][u]];
        }
    }

    // init distToSink
    q.clear();
    distToSink[sink] = 0;
    for (q.add(sink); q.isNotEmpty(); ) {
      final int u = q.poll();
      graph.forEachInNodes(u, v -> {
        if (nxtNode[0][v] == u && distToSink[v] == Integer.MAX_VALUE) {
          distToSink[v] = distToSink[u] + 1;
          q.add(v);
        }
      });
    }
    IntArrayUtils.replaceAll(distToSink, Integer.MAX_VALUE, -1);

    // update answer
    queries.forEach(sink, queryIdx -> {
      int source = fromIdx[queryIdx];
      int idx = stepIdx[queryIdx];
      answer[queryIdx] = idx <= distToSink[source]
          ? queryStepIdx(source, idx) + 1
          : -1;
    });
  }

  int queryStepIdx(int u, int idx) {
    for (int bit = 0; idx > 0; ++bit, idx >>= 1)
      if (IntUtils.isOdd(idx)) {
        u = nxtNode[bit][u];
      }
    return u;
  }
}
