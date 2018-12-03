package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.pair.IntIntPair;

import java.util.Arrays;

public class UVA1587 {

  IntIntPair[] faces;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    faces = new IntIntPair[6];
    while (true) {
      int w = in.nextIntOrDefault(-1);
      if (w < 0) {
        break;
      }
      int h = in.nextIntOrDefault(-1);
      faces[0] = new IntIntPair(Math.min(w, h), Math.max(w, h));
      for (int i = 1; i < 6; ++i) {
        w = in.nextInt();
        h = in.nextInt();
        faces[i] = new IntIntPair(Math.min(w, h), Math.max(w, h));
      }
      Arrays.sort(faces);
      out.println(isPossible() ? "POSSIBLE" : "IMPOSSIBLE");
    }
  }

  boolean isPossible() {
    for (int i = 1; i < 6; i += 2) {
      if (!faces[i - 1].equals(faces[i])) {
        return false;
      }
    }
    int a = faces[0].first;
    int b = faces[0].second;
    if ((faces[2].first == a || faces[2].first == b) && isPossible(a, b, faces[2].second)) {
      return true;
    }
    return (faces[2].second == a || faces[2].second == b) && isPossible(a, b, faces[2].first);
  }

  boolean isPossible(int a, int b, int c) {
    if (faces[2].first == Math.min(a, c)
        && faces[2].second == Math.max(a, c)
        && faces[4].first == Math.min(b, c)
        && faces[4].second == Math.max(b, c)) {
      return true;
    }
    return faces[2].first == Math.min(b, c)
        && faces[2].second == Math.max(b, c)
        && faces[4].first == Math.min(a, c)
        && faces[4].second == Math.max(a, c);
  }
}
