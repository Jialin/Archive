package main;

import template.collections.list.IntArrayList;
import template.collections.queue.IntArrayQueue;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class Task2108 {
  int n, m;
  char[] token;

  int[] ifCnt;
  IntArrayList[] ifColumn;
  IntArrayQueue ifQueue;

  IntArrayList[] thenSet;

  boolean[] bought;
  IntArrayQueue boughtQueue;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    token = new char[n];
    ifCnt = new int[m];
    thenSet = new IntArrayList[m];
    ifColumn = new IntArrayList[n];
    for (int i = 0; i < n; ++i) {
      ifColumn[i] = new IntArrayList();
    }
    for (int i = 0; i < m; ++i) {
      thenSet[i] = new IntArrayList();
    }
    ifQueue = new IntArrayQueue(m);
    for (int i = 0; i < m; ++i) {
      in.next(token);
      for (int j = 0; j < n; ++j) {
        if (token[j] == '1') {
          ++ifCnt[i];
          ifColumn[j].add(i);
        }
      }
      if (ifCnt[i] == 0) ifQueue.add(i);
      in.next(token);
      for (int j = 0; j < n; ++j) {
        if (token[j] == '1') {
          thenSet[i].add(j);
        }
      }
    }
    bought = new boolean[n];
    boughtQueue = new IntArrayQueue(n);
    in.next(token);
    for (int i = 0; i < n; ++i) {
      if (token[i] == '1') {
        bought[i] = true;
        boughtQueue.add(i);
      }
    }
    calc();
    for (int i = 0; i < n; ++i) {
      out.print(bought[i] ? '1' : '0');
    }
    out.println();
  }

  void calc() {
    while (!ifQueue.isEmpty() || !boughtQueue.isEmpty()) {
      while (!ifQueue.isEmpty()) {
        int idx = ifQueue.poll();
        IntArrayList thenList = thenSet[idx];
        for (int i = 0; i < thenList.size; ++i) {
          int j = thenList.get(i);
          if (bought[j]) continue;
          bought[j] = true;
          boughtQueue.add(j);
        }
      }
      while (!boughtQueue.isEmpty()) {
        int idx = boughtQueue.poll();
        IntArrayList ifList = ifColumn[idx];
        for (int i = 0; i < ifList.size; ++i) {
          int j = ifList.get(i);
          --ifCnt[j];
          if (ifCnt[j] == 0) {
            ifQueue.add(j);
          }
        }
      }
    }
  }
}
