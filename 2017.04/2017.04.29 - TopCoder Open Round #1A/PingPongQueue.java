package main;

import template.collections.deque.IntArrayDeque;

public class PingPongQueue {
  public int[] whoPlaysNext(int[] skills, int N, int K) {
    IntArrayDeque q = new IntArrayDeque(skills.length);
    for (int skill : skills) q.addLast(skill);
    int winSkill = -1, winCnt = 0;
    for (int i = 1; i < K; ++i) {
      int skill1 = q.pollFirst();
      int skill2 = q.pollFirst();
      int staySkill = Math.max(skill1, skill2);
      int leaveSkill = Math.min(skill1, skill2);
      if (winSkill == staySkill) {
        ++winCnt;
      } else {
        winSkill = staySkill;
        winCnt = 1;
      }
      if (winCnt == N) {
        q.addLast(leaveSkill);
        q.addLast(staySkill);
        winSkill = -1;
        winCnt = 0;
      } else {
        q.addFirst(staySkill);
        q.addLast(leaveSkill);
      }
    }
    int skill1 = q.pollFirst();
    int skill2 = q.pollFirst();
    return new int[]{
        Math.min(skill1, skill2),
        Math.max(skill1, skill2)};
  }
}
