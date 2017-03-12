package main;

import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Set;
import java.util.TreeSet;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int n;
      Person[] persons;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        n = in.nextInt();
        persons = new Person[n];
        for (int i = 0; i < n; ++i) {
          persons[i] = new Person(in.nextLine());
        }
      }

      Person res;

      @Override
      public void process(int taskIdx) {
        res = persons[0];
        for (int i = 1; i < n; ++i) {
          if (persons[i].compareTo(res) < 0) {
            res = persons[i];
          }
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %s\n", taskIdx, res.name);
      }
    };
  }

  class Person implements Comparable<Person> {
    final String name;
    final int cnt;

    Person(String name) {
      this.name = name;
      Set<Character> s = new TreeSet<>();
      for (int i = 0; i < name.length(); ++i) {
        char ch = name.charAt(i);
        if ('A' <= ch && ch <= 'Z') {
          s.add(ch);
        }
      }
      cnt = s.size();
    }

    @Override
    public int compareTo(Person o) {
      return cnt == o.cnt ? name.compareTo(o.name) : o.cnt - cnt;
    }
  }
}
