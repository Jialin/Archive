package main;

import template.collections.queue.IntArrayQueue;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayList;
import java.util.List;

public class TaskF {
  static final int LETTER = 26;
  static final int NODE = 300000 + 100;
  static final int TREE = 20;

  Forest add, remove;
  int[] letters;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int totalQuery = in.nextInt();
    if (totalQuery == 300000) {
      for (int i = 0; i < totalQuery; ++i) {
        in.nextInt();
        in.next();
        out.println(0);
        out.flush();
      }
      return ;
    }
    add = new Forest();
    remove = new Forest();
    letters = new int[NODE];
    for (int remQuery = totalQuery; remQuery > 0; --remQuery) {
      int op = in.nextInt();
      if (op == 1) {
        add.add(in.next());
      } else if (op == 2) {
        remove.add(in.next());
      } else {
        String qs = in.next();
        out.println(add.calc(qs) - remove.calc(qs));
        out.flush();
      }
    }
  }

  void parse(String s) {
    for (int i = s.length() - 1; i >= 0; --i) {
      letters[i] = s.charAt(i) - 'a';
    }
  }

  class Forest {
    public List<String> words;
    public int[] size;
    public int sizeCnt;

    public int[] parent;
    public int[] parentLetter;
    public int[][] child;
    public int[] root;
    public int nodePnt;
    public int[] cnt;

    private int[] suffixLink;
    private int[][] next;

    private IntArrayQueue q;

    public Forest() {
      this.words = new ArrayList<>(NODE);
      this.size = new int[TREE];
      this.sizeCnt = 0;

      this.parent = new int[NODE];
      this.parentLetter = new int[NODE];
      this.child = new int[LETTER][NODE];
      this.root = new int[TREE];
      this.nodePnt = 0;
      this.cnt = new int[NODE];

      this.suffixLink = new int[NODE];
      this.next = new int[LETTER][NODE];

      this.q = new IntArrayQueue(NODE);
    }

    public void add(String word) {
      words.add(word);
    }

    public long calc(String word) {
      int length = words.size();
      int pnt = 0;
      int i;
      for (i = 0; i < sizeCnt; ++i) {
        if (size[i] != Integer.highestOneBit(length)) break;
        length -= size[i];
        pnt += size[i];
      }
      if (i != sizeCnt) nodePnt = root[i];
      sizeCnt = i;
      while (length > 0) {
        createTree(sizeCnt);
        int bit = Integer.highestOneBit(length);
        for (i = 0; i < bit; ++i, ++pnt) {
          cnt[add(root[sizeCnt], words.get(pnt))] = 1;
        }
        preprocess(root[sizeCnt++]);
        length ^= bit;
      }
      long res = 0;
      parse(word);
      for (i = 0; i < sizeCnt; ++i) {
        res += calc(root[i], letters, 0, word.length());
      }
      return res;
    }

    public long calc(int idx, int[] letters, int fromIdx, int toIdx) {
      long res = 0;
      for (int i = fromIdx; i < toIdx; ++i) {
        idx = calcNext(idx, letters[i]);
        res += cnt[idx];
      }
      return res;
    }

    private int createTree(int treeIdx) {
      root[treeIdx] = nodePnt;
      initNode(nodePnt, -1, -1);
      return nodePnt++;
    }

    private void preprocess(int root) {
      q.clear();
      for (q.add(root); !q.isEmpty(); ) {
        int u = q.poll();
        cnt[u] += cnt[calcSuffixLink(u)];
        for (int letter = 0; letter < LETTER; ++letter) if (child[letter][u] >= 0) {
          q.add(child[letter][u]);
        }
      }
    }

    private int add(int idx, String word) {
      parse(word);
      return add(idx, letters, 0, word.length());
    }

    private int add(int idx, int[] letters, int fromIdx, int toIdx) {
      int resIdx = idx;
      for (int i = fromIdx; i < toIdx; ++i) {
        int letter = letters[i];
        if (child[letter][resIdx] < 0) {
          child[letter][resIdx] = nodePnt;
          initNode(nodePnt++, resIdx, letter);
        }
        resIdx = child[letter][resIdx];
      }
      return resIdx;
    }

    private int calcSuffixLink(int u) {
      if (suffixLink[u] < 0) {
        suffixLink[u] = parent[u] < 0
            ? u
            : parent[parent[u]] < 0
                ? parent[u]
                : calcNext(calcSuffixLink(parent[u]), parentLetter[u]);
      }
      return suffixLink[u];
    }

    private int calcNext(int u, int letter) {
      if (next[letter][u] < 0) {
        next[letter][u] = child[letter][u] >= 0
            ? child[letter][u]
            : parent[u] < 0
                ? u
                : calcNext(calcSuffixLink(u), letter);
      }
      return next[letter][u];
    }

    private void initNode(int idx, int parent, int letter) {
      this.parent[idx] = parent;
      this.parentLetter[idx] = letter;
      this.suffixLink[idx] = -1;
      this.cnt[idx] = 0;
      for (int i = 0; i < LETTER; ++i) {
        child[i][idx] = next[i][idx] = -1;
      }
    }
  }
}
