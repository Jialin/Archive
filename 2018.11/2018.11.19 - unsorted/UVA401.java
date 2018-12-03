package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA401 {
  private static final String FROM =
      "AEHIJLMOSTUVWXYZ12358";
  private static final String TO =
      "A3HILJMO2TUVWXY51SEZ8";
  private static final char[] MIRROR = new char[256];
  {
    for (int i = 0; i < FROM.length(); ++i) {
      MIRROR[FROM.charAt(i)] = TO.charAt(i);
    }
  }

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    while (true) {
      String s = in.next();
      if (s.isEmpty()) {
        break;
      }
      boolean isPalindrome = isPalindrome(s);
      boolean isMirrored = isMirrored(s);
      out.printf("%s -- is ", s);
      if (isPalindrome) {
        out.println(isMirrored ? "a mirrored palindrome." : "a regular palindrome.");
      } else {
        out.println(isMirrored ? "a mirrored string." : "not a palindrome.");
      }
      out.println();
    }
  }

  private static boolean isPalindrome(String s) {
    for (int i = 0, j = s.length() - 1; i < j; ++i, --j) {
      if (s.charAt(i) != s.charAt(j)) {
        return false;
      }
    }
    return true;
  }

  private static boolean isMirrored(String s) {
    for (int i = 0, j = s.length() - 1; i <= j; ++i, --j) {
      char x = s.charAt(i);
      char y = s.charAt(j);
      if (MIRROR[x] == 0 || MIRROR[x] != y) {
        return false;
      }
    }
    return true;
  }
}
