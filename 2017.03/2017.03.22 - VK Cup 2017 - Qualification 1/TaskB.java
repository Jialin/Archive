package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  int n;
  Student[] students;
  IntArrayList resX, resY;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    students = new Student[n];
    for (int i = 0; i < n; ++i) {
      students[i] = new Student(in.nextInt(), i);
    }
    Arrays.sort(students, 1, n);
    resX = new IntArrayList(n);
    resY = new IntArrayList(n);
    int j = 1;
    for (int i = 0; i < j; ++i) {
      for (int k = 0; j < n && k < students[i].cnt; ++j, ++k) {
        resX.add(students[i].idx);
        resY.add(students[j].idx);
      }
    }
    if (j == n) {
      out.println(n - 1);
      for (int i = 0; i < n - 1; ++i) {
        out.print(resX.get(i) + 1);
        out.print(' ');
        out.println(resY.get(i) + 1);
      }
    } else {
      out.println(-1);
    }
  }

  class Student implements Comparable<Student> {
    final int cnt, idx;

    public Student(int cnt, int idx) {
      this.cnt = cnt;
      this.idx = idx;
    }

    @Override
    public int compareTo(Student o) {
      return o.cnt - cnt;
    }
  }
}
