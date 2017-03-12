package main;

import template.collections.trie.ArrayTrie;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  private static final int NODE = 200000 * 30 + 1;
  private static final int LETTER = 2;
  private static final int BIT = 30;

  ArrayTrie trie;
  int[] cnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    trie = new ArrayTrie(LETTER, NODE);
    trie.init(LETTER);
    int[] bits = new int[BIT];
    cnt = new int[NODE];
    update(bits, 1);
    for (int query = in.nextInt(); query > 0; --query) {
      String operation = in.next();
      char o = operation.charAt(0);
      int value = in.nextInt();
      for (int i = BIT - 1, j = 0; i >= 0; --i, ++j) {
        bits[j] = (value >> i) & 1;
      }
      if (o == '+') {
        update(bits, 1);
      } else if (o == '-') {
        update(bits, -1);
      } else {
        int idx = trie.root, res = 0;
        for (int i = 0, j = BIT - 1; i < BIT; ++i, --j) {
          if (cnt[trie.child[bits[i] ^ 1][idx]] > 0) {
            res |= 1 << j;
            idx = trie.child[bits[i] ^ 1][idx];
          } else {
            idx = trie.child[bits[i]][idx];
          }
        }
        out.println(res);
      }
    }
  }

  private void update(int[] bits, int delta) {
    for (int idx = trie.add(bits); idx >= 0; idx = trie.parent[idx]) {
      cnt[idx] += delta;
    }
  }
}
