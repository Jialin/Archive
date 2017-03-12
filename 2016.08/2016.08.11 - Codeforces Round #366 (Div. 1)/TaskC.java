package main;

import template.graph.BidirectionalGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.IntUtils;

import static java.lang.Math.abs;
import static main.Way.way;

public class TaskC {
  int n, m;
  int[] singlePositive;
  int[] singleNegative;
  boolean[] visited;
  BidirectionalGraph<XnfEdge> graph;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    m = in.nextInt();
    n = in.nextInt();
    graph = new BidirectionalGraph<>(n, m);
    graph.init(n);
    singlePositive = new int[n];
    singleNegative = new int[n];
    visited = new boolean[n];

    for (int i = 0; i < m; ++i) {
      if (in.nextInt() == 1) {
        int x = in.nextInt();
        if (x > 0) {
          ++singlePositive[x - 1];
        } else {
          ++singleNegative[-x - 1];
        }
      } else {
        int x = in.nextInt();
        int y = in.nextInt();
        graph.add(
            new XnfEdge(abs(x) - 1, abs(y) - 1, x < 0, y < 0),
            new XnfEdge(abs(y) - 1, abs(x) - 1, y < 0, x < 0));
      }
    }

    Way res = way[1][0];
    // Single
    for (int i = 0; i < n; ++i) if (graph.outDegree[i] == 0) {
      visited[i] = true;
      res = res.mul(Way.createSingle(singlePositive[i], singleNegative[i]));
    }
    // Chain
    for (int i = 0; i < n; ++i) if (!visited[i] && graph.outDegree[i] == 1) {
      res = res.mul(dfsChain(i, null, null));
    }
    // Self loop
    for (int i = 0; i < n; ++i) if (!visited[i] && graph.lastOutgoingEdge(i).toIdx == i) {
      visited[i] = true;
      XnfEdge edge = graph.lastOutgoingEdge(i);
      res = res.mul(Way.createSelfLoop(
          (edge.fromNeg ? 1 : 0) | (edge.toNeg ? 1 : 0),
          (edge.fromNeg ? 0 : 1) | (edge.toNeg ? 0 : 1)));
    }
    // Loop
    for (int i = 0; i < n; ++i) if (!visited[i]) {
      res = res.mul(dfsLoop(i, null, null));
    }
    out.println(res.odd);
  }

  Way dfsChain(int u, Way[] ways, XnfEdge prevEdge) {
    visited[u] = true;
    Way[] nxtWays;
    if (prevEdge == null) {
      nxtWays = new Way[]{
          Way.createChain(0, singlePositive[u], singleNegative[u]),
          Way.createChain(1, singlePositive[u], singleNegative[u])};
    } else {
      nxtWays = new Way[]{
          ways[0].mul(Way.createChain(0, 0, prevEdge, singlePositive[u], singleNegative[u])).add(
              ways[1].mul(Way.createChain(1, 0, prevEdge, singlePositive[u], singleNegative[u]))),
          ways[0].mul(Way.createChain(0, 1, prevEdge, singlePositive[u], singleNegative[u])).add(
              ways[1].mul(Way.createChain(1, 1, prevEdge, singlePositive[u], singleNegative[u])))};
    }
    for (XnfEdge edge = graph.lastOutgoingEdge(u); edge != null; edge = (XnfEdge) edge.nextOutgoing) {
      if (edge.reverse != prevEdge) {
        return dfsChain(edge.toIdx, nxtWays, edge);
      }
    }
    return nxtWays[0].add(nxtWays[1]);
  }

  Way dfsLoop(int u, Way[][] ways, XnfEdge prevEdge) {
    visited[u] = true;
    XnfEdge nxtEdge = null;
    for (XnfEdge edge = graph.lastOutgoingEdge(u); edge != null; edge = (XnfEdge) edge.nextOutgoing) {
      if (edge.reverse != prevEdge) {
        nxtEdge = edge;
        break;
      }
    }
    if (prevEdge == null) {
      return dfsLoop(
          nxtEdge.toIdx,
          new Way[][]{
              {Way.way[1][0], Way.way[0][0]},
              {Way.way[0][0], Way.way[1][0]}},
          nxtEdge);
    }
    Way[][] nxtWays = new Way[][]{{Way.way[0][0], Way.way[0][0]}, {Way.way[0][0], Way.way[0][0]}};
    if (!visited[nxtEdge.toIdx]) {
      for (int start = 0; start < 2; ++start) {
        for (int prevValue = 0; prevValue < 2; ++prevValue) {
          for (int value = 0; value < 2; ++value) {
            nxtWays[start][value] = nxtWays[start][value].add(
                ways[start][prevValue].mul(Way.createLoop(prevValue, value, prevEdge)));
          }
        }
      }
      return dfsLoop(nxtEdge.toIdx, nxtWays, nxtEdge);
    }
    Way res = Way.way[0][0];
    for (int start = 0; start < 2; ++start) {
      for (int prevValue = 0; prevValue < 2; ++prevValue) {
        for (int value = 0; value < 2; ++value) {
          res = res.add(ways[start][prevValue].mul(Way.createLoopEnd(prevValue, value, start, prevEdge, nxtEdge)));
        }
      }
    }
    return res;
  }
}

class Way {
  public static final Way[][] way;

  static {
    way = new Way[3][3];
    for (int i = 0; i < 3; ++i) for (int j = 0; j < 3; ++j) {
      way[i][j] = new Way(i, j);
    }
  }

  final int even, odd;

  public Way() {
    this(0, 0);
  }

  private Way(int even, int odd) {
    this.even = even;
    this.odd = odd;
  }

  public static Way createSingle(int positive, int negative) {
    if (positive + negative == 0) return way[2][0];
    if (positive + negative == 1) return way[1][1];
    if (positive == 1 && negative == 1) return way[0][2];
    if (positive + negative == 2) return way[2][0];
    throw new RuntimeException();
  }

  public static Way createChain(int value, int positive, int negative) {
    if (positive == 0 && negative == 0) return way[1][0];
    if (positive + negative == 1) {
      int res = positive == 1 ? value : (value ^ 1);
      return res == 0 ? way[1][0] : way[0][1];
    }
    throw new RuntimeException();
  }

  public static Way createChain(int prevValue, int value, XnfEdge edge, int positive, int negative) {
    int res = 0;
    res ^= calc(prevValue, value, edge);
    if (positive + negative == 0) {
    } else if (positive + negative == 1) {
      res ^= positive == 1 ? value : (value ^ 1);
    } else {
      throw new RuntimeException();
    }
    return res == 0 ? way[1][0] : way[0][1];
  }

  public static Way createSelfLoop(int value1, int value2) {
    int even = (value1 == 0 ? 1 : 0) + (value2 == 0 ? 1 : 0);
    int odd = (value1 == 1 ? 1 : 0) + (value2 == 1 ? 1 : 0);
    return way[even][odd];
  }

  public static Way createLoop(int prevValue, int value, XnfEdge edge) {
    return createChain(prevValue, value, edge, 0, 0);
  }

  public static Way createLoopEnd(int prevValue, int value, int nxtValue, XnfEdge prevEdge, XnfEdge nxtEdge) {
    int res = calc(prevValue, value, prevEdge) ^ calc(value, nxtValue, nxtEdge);
    return res == 0 ? way[1][0] : way[0][1];
  }

  public Way add(Way o) {
    return new Way(
        IntUtils.add(even, o.even),
        IntUtils.add(odd, o.odd));
  }

  public Way mul(Way o) {
    return new Way(
        IntUtils.add(
            IntUtils.mul(even, o.even),
            IntUtils.mul(odd, o.odd)),
        IntUtils.add(
            IntUtils.mul(even, o.odd),
            IntUtils.mul(odd, o.even)));
  }

  private static int calc(int prevValue, int value, XnfEdge edge) {
    return (edge.fromNeg ? prevValue ^ 1 : prevValue) | (edge.toNeg ? value ^ 1 : value);
  }
}

class XnfEdge extends BidirectionalGraphEdge {

  final boolean fromNeg;
  final boolean toNeg;

  public XnfEdge(int fromIdx, int toIdx, boolean fromNeg, boolean toNeg) {
    super(fromIdx, toIdx);
    this.fromNeg = fromNeg;
    this.toNeg = toNeg;
  }
}
