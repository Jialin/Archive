package main;

import template.numbertheory.number.IntModular;

public class SimpleMazeEasy {
  static IntModular MOD = new IntModular();

  public int findSum(long nn) {
    int n = MOD.fix(nn);
    return MOD.mul(MOD.add(calcShort(n), calcLong(n)), 2);
  }

  int calcShort(int n) {
    return MOD.mul(MOD.mul(MOD.mul(MOD.mul(MOD.mul(
        MOD.inverse(3),
        MOD.sub(n, 1)),
        n),
        n),
        n),
        MOD.add(MOD.mul(13, n), 1));
  }

  int calcLong(int n) {
    return MOD.mul(MOD.mul(MOD.mul(MOD.mul(MOD.mul(
        MOD.inverse(2),
        n),
        n),
        n),
        MOD.add(n, 1)),
        MOD.sub(MOD.mul(11, n), 3));
  }
}
