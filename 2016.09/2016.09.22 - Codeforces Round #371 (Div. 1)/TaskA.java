package main;

import template.array.CharArrayUtils;
import template.collections.trie.AbstractTrie;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskA {
  static int LENGTH = 18;
  static int MAXQ = 100000;
  static int MAXNODE = MAXQ * LENGTH;

  char[] s;
  int[] letters;
  Trie trie;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    s = new char[LENGTH];
    letters = new int[LENGTH];
    trie = new Trie(2, MAXNODE);
    trie.init(2);
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      in.next(s);
      char op = s[0];
      int length = in.next(s);
      CharArrayUtils.reverse(s, 0, length);
      parse(length);
      switch (op) {
        case '+':
          for (int resIdx = trie.add(letters); resIdx >= 0; resIdx = trie.parent[resIdx]) {
            ++trie.cnt[resIdx];
          }
          break;
        case '-':
          for (int resIdx = trie.add(letters); resIdx >= 0; resIdx = trie.parent[resIdx]) {
            --trie.cnt[resIdx];
          }
          break;
        case '?':
          int resIdx = trie.access(letters);
          out.println(resIdx < 0 ? 0 : trie.cnt[resIdx]);
          break;
      }
    }
  }

  void parse(int length) {
    Arrays.fill(letters, 0);
    for (int i = 0; i < length; ++i) {
      letters[i] = (s[i] - '0') & 1;
    }
  }

  class Trie extends AbstractTrie {

    int[] cnt;

    public Trie(int letterCapacity, int nodeCapacity) {
      super(letterCapacity, nodeCapacity);
      cnt = new int[nodeCapacity];
    }

    @Override
    public void initNode(int idx, int parent, int parentLetter) {
      cnt[idx] = 0;
    }
  }
}
