package main;

import template.calendar.Calendar;
import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: friday
*/
public class friday_main {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int[] cnt = new int[7];
    int n = in.nextInt();
    for (int i = 0, year = 1900; i < n; ++i, ++year) {
      for (int month = 1; month <= 12; ++month) {
        int weekday = (Calendar.weekday(year, month, 13) + 1) % 7;
        ++cnt[weekday];
      }
    }
    out.println(cnt);
  }
}
