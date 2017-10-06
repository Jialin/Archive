package main;

import template.array.IntArrayUtils;
import template.collections.intervaltree.impl.IntMinIntervalTreeSupportSet;
import template.collections.list.IntArrayList;
import template.collections.rmq.IntMinimumRMQ;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskG {
  QuickWriter out;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.out = out;
    input(in);
    discrete();
    rmq();
    oldMinValue();
    calc();
  }

  IntMinIntervalTreeSupportSet itree;

  void calc() {
    itree = new IntMinIntervalTreeSupportSet(oldMinValue.length, false);
    itree.init(oldMinValue.length, oldMinValue);
    for (int queryIdx = 0; queryIdx < queryCnt; ++queryIdx) {
      if (op[queryIdx] == 1) {
        itree.update(left[queryIdx], right[queryIdx], setValue[queryIdx]);
      } else {
        out.println(itree.calc(left[queryIdx], right[queryIdx]));
      }
    }
  }

  int globalMin;
  int[] oldMinValue;

  void oldMinValue() {
    globalMin = IntArrayUtils.min(value, 0, valueCnt);
    oldMinValue = new int[disIdx.size - 1];
    for (int i = 1; i < disIdx.size; ++i) {
      int left = disIdx.get(i - 1);
      int right = disIdx.get(i);
      int res;
      if (right - left >= valueCnt) {
        res = globalMin;
      } else {
        left %= valueCnt;
        right %= valueCnt;
        res = left < right ? rmq.calc(left, right) : rmq.calc(left, right + valueCnt);
      }
      oldMinValue[i - 1] = res;
    }
  }

  IntMinimumRMQ rmq;

  void rmq() {
    rmq = new IntMinimumRMQ(valueCnt << 1);
    rmq.init(valueCnt << 1, value);
  }

  int[] left, right;

  void discrete() {
    disIdx.sortAndUnique();
    left = new int[queryCnt];
    right = new int[queryCnt];
    for (int i = 0; i < queryCnt; ++i) {
      left[i] = disIdx.lowerBound(oldLeft[i]);
      right[i] = disIdx.lowerBound(oldRight[i]);
    }
  }

  int valueCnt, repeatCnt;
  int[] value;
  int queryCnt;
  int[] oldLeft, oldRight;
  int[] op, setValue;
  IntArrayList disIdx;

  void input(QuickScanner in) {
    valueCnt = in.nextInt();
    repeatCnt = in.nextInt();
    value = new int[valueCnt << 1];
    for (int i = 0, j = valueCnt; i < valueCnt; ++i, ++j) {
      value[i] = in.nextInt();
      value[j] = value[i];
    }
    queryCnt = in.nextInt();
    op = new int[queryCnt];
    oldLeft = new int[queryCnt];
    oldRight = new int[queryCnt];
    setValue = new int[queryCnt];
    disIdx = new IntArrayList(queryCnt << 1);
    for (int i = 0; i < queryCnt; ++i) {
      op[i] = in.nextInt();
      oldLeft[i] = in.nextInt() - 1;
      oldRight[i] = in.nextInt();
      disIdx.add(oldLeft[i]);
      disIdx.add(oldRight[i]);
      if (op[i] == 1) {
        setValue[i] = in.nextInt();
      }
    }
  }
}
