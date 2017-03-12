package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {

  int n, q, lastIdx;
  int messageCnt;
  Message[] messages;
  Message[] firstMessage;
  Message[] lastMessage;
  Message[] lastVisitedMessage;
  int unreadCnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    q = in.nextInt();
    messages = new Message[q];
    firstMessage = new Message[n];
    lastMessage = new Message[n];
    lastVisitedMessage = new Message[n];
    unreadCnt = 0;
    messageCnt = 0;
    lastIdx = 0;
    for (int i = 0; i < q; ++i) {
      int type = in.nextInt();
      int x = in.nextInt();
      if (type == 1) {
        addMessage(x - 1);
      } else if (type == 2) {
        readApplication(x - 1);
      } else {
        readMessages(x);
      }
      out.println(unreadCnt);
    }
  }

  void addMessage(int x) {
    Message newMessage = new Message();
    messages[messageCnt++] = newMessage;
    if (firstMessage[x] == null) {
      firstMessage[x] = newMessage;
    } else {
      lastMessage[x].next = newMessage;
    }
    lastMessage[x] = newMessage;
  }

  void readApplication(int x) {
    Message iteratorMessage = lastVisitedMessage[x];
    if (iteratorMessage == null) {
      iteratorMessage = firstMessage[x];
    }
    while (iteratorMessage != null) {
      iteratorMessage.read();
      if (iteratorMessage.next == null) break;
      iteratorMessage = iteratorMessage.next;
    }
    lastVisitedMessage[x] = iteratorMessage;
  }

  void readMessages(int x) {
    for ( ; lastIdx < x; ++lastIdx) {
      messages[lastIdx].read();
    }
  }

  class Message {
    boolean read;
    Message next;

    Message() {
      read = false;
      next = null;
      ++unreadCnt;
    }

    void read() {
      if (!read) {
        read = true;
        --unreadCnt;
      }
    }
  }
}
