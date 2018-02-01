package main;

import template.character.CharUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int res = 0;
    for (char c : in.next().toCharArray()) {
      if (CharUtils.isVowel(c) || (Character.isDigit(c) && IntUtils.isOdd(c - '0'))) {
        ++res;
      }
    }
    out.println(res);
  }
}
