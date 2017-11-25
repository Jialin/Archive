package main;

public class TopXorerEasy {
  public int maximalRating(int A, int B, int C) {
    int res = 0;
    for (int bit = 30; bit >= 0; --bit) {
      int two = 1 << bit;
      if (A >= two) {
        res |= two;
        A -= two;
      } else if (B >= two) {
        res |= two;
        B -= two;
      } else if (C >= two) {
        res |= two;
        C -= two;
      }
    }
    return res;
  }
}
