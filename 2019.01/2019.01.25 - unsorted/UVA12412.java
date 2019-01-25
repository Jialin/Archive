package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.*;

public class UVA12412 {

  static final String[] COURSES = new String[]{"Chinese", "Mathematics", "English", "Programming"};
  static final Comparator<Student> BY_TS = Comparator.comparingInt(x -> x.ts);
  static final Comparator<Student> BY_SCORE = Comparator.comparingInt(x -> -x.sumScore);

  QuickScanner in;
  QuickWriter out;

  Storage storage;
  int addTs;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.in = in;
    this.out = out;
    storage = new Storage();
    addTs = 0;
    while (true) {
      mainMenu();
      int op = in.nextInt();
      if (op == 1) {
        add();
      } else if (op == 2) {
        remove();
      } else if (op == 3) {
        query();
      } else if (op == 4) {
        out.println("Showing the ranklist hurts students' self-esteem. Don't do that.");
      } else if (op == 5) {
        stat();
      } else {
        break;
      }
    }
  }

  void add() {
    while (true) {
      out.println("Please enter the SID, CID, name and four scores. Enter 0 to finish.");
      String sid = in.next();
      if ("0".equals(sid)) {
        break;
      }
      Student s = new Student(sid, ++addTs);
      storage.add(s);
    }
    storage.rerank();
  }

  void remove() {
    while (true) {
      out.println("Please enter SID or name. Enter 0 to finish.");
      String sidName = in.next();
      if ("0".equals(sidName)) {
        break;
      }
      storage.remove(sidName);
    }
    storage.rerank();
  }

  void query() {
    while (true) {
      out.println("Please enter SID or name. Enter 0 to finish.");
      String sidName = in.next();
      if ("0".equals(sidName)) {
        break;
      }
      storage.query(sidName);
    }
  }

  void stat() {
    out.println("Please enter class ID, 0 for the whole statistics.");
    storage.stat(in.nextInt());
  }

  void mainMenu() {
    out.println("Welcome to Student Performance Management System (SPMS).");
    out.println();
    out.println("1 - Add");
    out.println("2 - Remove");
    out.println("3 - Query");
    out.println("4 - Show ranking");
    out.println("5 - Show Statistics");
    out.println("0 - Exit");
    out.println();
  }

  class Student {
    String sid;
    int cid;
    String name;
    int[] scores;
    int passBucket;
    int ts;
    int rank;
    int sumScore;
    double avgScore;

    Student(String sid, int ts) {
      this.sid = sid;
      this.ts = ts;
      cid = in.nextInt();
      name = in.next();
      scores = new int[4];
      passBucket = 0;
      sumScore = 0;
      for (int i = 0; i < 4; ++i) {
        scores[i] = in.nextInt();
        if (scores[i] >= 60) {
          ++passBucket;
        }
        sumScore += scores[i];
      }
      avgScore = sumScore / 4.;
    }

    void display() {
      out.printf("%d %s %d %s %d %d %d %d %d %.2f\n", rank, sid, cid, name, scores[0], scores[1], scores[2], scores[3], sumScore, avgScore);
    }
  }

  class Class {
    int cid;
    int[] scoreSum;
    int[] passCnt;
    int[] failedCnt;
    int[] passBucket;

    Class(int cid) {
      this.cid = cid;
      scoreSum = new int[4];
      passCnt = new int[4];
      failedCnt = new int[4];
      passBucket = new int[5];
    }

    void add(Student s) {
      for (int i = 0; i < 4; ++i) {
        int score = s.scores[i];
        scoreSum[i] += score;
        if (score >= 60) {
          ++passCnt[i];
        } else {
          ++failedCnt[i];
        }
      }
      if (s.passBucket == 0) {
        ++passBucket[0];
      } else {
        for (int i = s.passBucket; i > 0; --i) {
          ++passBucket[i];
        }
      }
    }

    void remove(Student s) {
      for (int i = 0; i < 4; ++i) {
        int score = s.scores[i];
        scoreSum[i] -= score;
        if (score >= 60) {
          --passCnt[i];
        } else {
          --failedCnt[i];
        }
      }
      if (s.passBucket == 0) {
        --passBucket[0];
      } else {
        for (int i = s.passBucket; i > 0; --i) {
          --passBucket[i];
        }
      }
    }

    void display() {
      for (int i = 0; i < 4; ++i) {
        out.println(COURSES[i]);
        int cnt = passCnt[i] + failedCnt[i];
        out.printf("Average Score: %.2f\n", cnt == 0 ? 0 : scoreSum[i] / (double) cnt);
        out.printf("Number of passed students: %d\n", passCnt[i]);
        out.printf("Number of failed students: %d\n", failedCnt[i]);
        out.println();
      }
      out.println("Overall:");
      out.printf("Number of students who passed all subjects: %d\n", passBucket[4]);
      for (int i = 3; i > 0; --i) {
        out.printf("Number of students who passed %d or more subjects: %d\n", i, passBucket[i]);
      }
      out.printf("Number of students who failed all subjects: %d\n\n", passBucket[0]);
    }
  }

  class Storage {
    Map<String, Map<String, Student>> students;
    Map<Integer, Class> classes;
    List<Student> ranks;
    Class allClass;

    Storage() {
      allClass = new Class(-1);
      students = new HashMap<>();
      classes = new HashMap<>();
      ranks = new ArrayList<>();
    }

    void add(Student s) {
      // sid
      String sid = s.sid;
      if (students.containsKey(sid)) {
        out.println("Duplicated SID.");
        return;
      }
      students.put(sid, new HashMap<>());
      students.get(sid).put(sid, s);
      // name
      String name = s.name;
      if (!students.containsKey(name)) {
        students.put(name, new HashMap<>());
      }
      students.get(name).put(sid, s);
      // cid
      int cid = s.cid;
      if (!classes.containsKey(cid)) {
        classes.put(cid, new Class(cid));
      }
      classes.get(cid).add(s);
      allClass.add(s);
    }

    void remove(String sidName) {
      ranks.clear();
      if (Character.isDigit(sidName.charAt(0))) {
        if (students.containsKey(sidName)) {
          ranks.add(students.get(sidName).get(sidName));
          out.println("1 student(s) removed.");
        } else {
          out.println("0 student(s) removed.");
        }
      } else if (students.containsKey(sidName)) {
        Map<String, Student> ss = students.get(sidName);
        ranks.addAll(ss.values());
        out.printf("%d student(s) removed.\n", ss.size());
      } else {
        out.println("0 student(s) removed.");
      }
      for (Student s : ranks) {
        remove(s);
      }
    }

    void remove(Student s) {
      classes.get(s.cid).remove(s);
      allClass.remove(s);
      students.remove(s.sid);
      students.get(s.name).remove(s.sid);
    }

    void query(String sidName) {
      ranks.clear();
      if (Character.isDigit(sidName.charAt(0))) {
        if (students.containsKey(sidName)) {
          ranks.add(students.get(sidName).get(sidName));
        }
      } else if (students.containsKey(sidName)) {
        ranks.addAll(students.get(sidName).values());
      }
      ranks.sort(BY_TS);
      for (Student s : ranks) {
        s.display();
      }
    }

    void stat(int cid) {
      if (cid == 0) {
        allClass.display();
      } else if (classes.containsKey(cid)) {
        classes.get(cid).display();
      }
    }

    void rerank() {
      ranks.clear();
      for (String sid : students.keySet()) {
        if (Character.isDigit(sid.charAt(0))) {
          ranks.add(students.get(sid).get(sid));
        }
      }
      ranks.sort(BY_SCORE);
      for (int i = 0; i < ranks.size(); ++i) {
        if (i == 0 || ranks.get(i - 1).sumScore != ranks.get(i).sumScore) {
          ranks.get(i).rank = i + 1;
        } else {
          ranks.get(i).rank = ranks.get(i - 1).rank;
        }
      }
    }
  }
}
