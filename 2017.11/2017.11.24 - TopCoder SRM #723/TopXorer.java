package main;

public class TopXorer {
  public int maximalRating(int[] x) {
    int res = 0;
    for (int bit = 30; bit >= 0; --bit) {
      boolean found = false;
      for (int i = 0; i < x.length; ++i) {
        if (x[i] >= 1 << bit) {
          x[i] -= 1 << bit;
          found = true;
          break;
        }
      }
      if (found) {
        res |= 1 << bit;
      }
    }
    return res;
  }
}
