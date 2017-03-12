package main;

import template.collections.binaryindexedtree.IntAddBinaryIndexedTree;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskE {

  static int MAXF = 10000;

  int n, k;
  IntArrayList disX;
  List<Station>[] stations = new List[MAXF];
  IntAddBinaryIndexedTree biTree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    for (int i = 0; i < MAXF; ++i) {
      stations[i] = new ArrayList<>();
    }
    n = in.nextInt();
    k = in.nextInt();
    disX = new IntArrayList(n);
    for (int i = 0; i < n; ++i) {
      int x = in.nextInt();
      int r = in.nextInt();
      int f = in.nextInt() - 1;
      stations[f].add(new Station(x, r, i));
    }
    for (int i = 0; i < MAXF; ++i) {
      stations[i].sort(Station.X);
    }
    biTree = new IntAddBinaryIndexedTree(n);
    List<Station> rightStations = new ArrayList<>(n);
    long res = 0;
    for (int i = 0; i < MAXF; ++i) if (!stations[i].isEmpty()) {
      rightStations.clear();
      int lower = Math.max(i - k, 0);
      int upper = Math.min(i + k, MAXF - 1);
      for (int j = lower; j <= upper; ++j) {
        rightStations.addAll(stations[j]);
      }
      res += calc(stations[i], rightStations);
    }
    out.println(res - n);
  }

  long calc(List<Station> leftStations, List<Station> rightStations) {
    long res = 0;
    disX.clear();
    for (Station rightStation : rightStations) {
      disX.add(rightStation.x);
    }
    disX.sort();
    biTree.init(disX.size);
    rightStations.sort(Station.LOWER);
    int leftXIdx = 0;
    int rightIdx = 0;
    for (Station leftStation : leftStations) {
      for ( ; rightIdx < rightStations.size(); ++rightIdx) {
        Station rightStation = rightStations.get(rightIdx);
        if (rightStation.lower > leftStation.x) break;
        biTree.update(disX.lowerBound(rightStation.x), 1);
      }
      for ( ; leftXIdx < disX.size && disX.get(leftXIdx) < leftStation.x; ++leftXIdx) {}
      res += biTree.calcRange(leftXIdx, disX.upperBound(leftStation.upper));
    }
    return res;
  }

  static class Station {

    static Comparator<Station> X = Comparator.comparingInt(l -> l.x);
    static Comparator<Station> LOWER = Comparator.comparingInt(l -> l.lower);

    final int x, r, idx;
    final int lower, upper;

    public Station(int x, int r, int idx) {
      this.x = x;
      this.r = r;
      this.lower = x - r;
      this.upper = x + r;
      this.idx = idx;
    }
  }
}
