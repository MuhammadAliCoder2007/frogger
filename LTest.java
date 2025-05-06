/**
 * Name: Muhammad Ali
 * 
 * Description:
 * This Java program defines a doubly linked list implementation with multiple functionalities. 
 * The main method demonstrates how to:
 * - Enqueue elements (insert at tail)
 * - Insert an element in sorted order
 * - Remove duplicate elements
 * - Reverse the list
 * The `LList` class represents the doubly linked list with various methods like `enqueue`, 
 * `sortedInsert`, `removeDuplicates`, `reverse`, and more. The `LNode` class represents a node 
 * in the doubly linked list.
 * Online resource was used for commenting
 */

import java.util.*;

public class LTest {
  public static void main(String[] args) {
    LList nums = new LList();

    // Adding elements to the list
    nums.enqueue(10);
    nums.enqueue(20);
    nums.enqueue(30);
    nums.enqueue(40);
    nums.enqueue(40);

    // Inserting 25 in sorted position
    nums.sortedInsert(25);
    System.out.println(nums); // Output list after sorted insertion

    // Removing duplicate values
    nums.removeDuplicates();
    System.out.println(nums); // Output list after removing duplicates

    // Reversing the list
    nums.reverse();
    System.out.println(nums); // Output list after reversing
  }
}

// Doubly linked list class
class LList {
  private LNode head, tail;

  public LList() {
    head = null;
    tail = null;
  }

  // Removes and returns the value from the front of the list
  public int pop() {
    int h = head.getVal();
    head = head.getNext();
    return h;
  }

  // Deletes a specific node from the list
  public void delete(LNode tmp) {
    LNode prev = tmp.getPrev();
    LNode next = tmp.getNext();

    if (head == tmp) {
      dequeue(); // If node is head, use dequeue
    } else if (tail == tmp) {
      deleteTail(); // If node is tail, delete tail
    } else {
      prev.setNext(next);
      if (next != null) {
        next.setPrev(prev);
      }
    }
  }

  // Deletes the last node in the list
  public void deleteTail() {
    tail = tail.getPrev();
    if (tail == null) {
      head = null;
    } else {
      tail.setNext(null);
    }
  }

  // Deletes the first node that matches value `v`
  public void delete(int v) {
    LNode tmp = head;
    while (tmp != null) {
      if (tmp.getVal() == v) {
        delete(tmp);
        break;
      }
      tmp = tmp.getNext();
    }
  }

  // Adds a node to the end of the list
  public void enqueue(int v) {
    LNode tmp = new LNode(tail, v, null);
    if (tail != null) {
      tail.setNext(tmp);
    } else {
      head = tmp;
    }
    tail = tmp;
  }

  // Inserts a node in sorted order (ascending)
  public void sortedInsert(int v) {
    LNode tmp = new LNode(null, v, null);

    if (head == null || head.getVal() >= v) {
      tmp.setNext(head);
      if (head != null) {
        head.setPrev(tmp);
      } else {
        tail = tmp;
      }
      head = tmp;
      return;
    }

    LNode c = head;
    while (c != null && c.getVal() <= v) {
      c = c.getNext();
    }

    if (c != null) {
      LNode prev = c.getPrev();
      tmp.setNext(c);
      tmp.setPrev(prev);
      prev.setNext(tmp);
      c.setPrev(tmp);
    } else {
      tmp.setPrev(tail);
      tail.setNext(tmp);
      tail = tmp;
    }
  }

  // Removes all duplicate values from the list
  public void removeDuplicates() {
    LNode tmp = head;

    while (tmp != null && tmp.getNext() != null) {
      int c = tmp.getVal();
      LNode tmp2 = tmp;

      while (tmp2.getNext() != null) {
        if (tmp2.getNext().getVal() != c) {
          tmp2 = tmp2.getNext();
        } else {
          tmp2.setNext(tmp2.getNext().getNext());
          if (tmp2.getNext() != null) {
            tmp2.getNext().setPrev(tmp2); // update backward link
          } else {
            tail = tmp2; // update tail if last duplicate was removed
          }
        }
      }

      tmp = tmp.getNext();
    }
  }

  // Deletes the node at a specific position (0-indexed)
  public void deleteAt(int pos) {
    LNode tmp = head;
    if (tmp != null) {
      for (int i = 0; i < pos; i++) {
        tmp = tmp.getNext();
      }
      delete(tmp);
    }
  }

  // Reverses the linked list
  public void reverse() {
    LNode c = head;
    LNode prev = null;
    LNode next = null;
    tail = head; // Old head becomes new tail

    while (c != null) {
      next = c.getNext();
      c.setNext(prev);
      c.setPrev(next);
      prev = c;
      c = next;
    }

    head = prev;
  }

  // Creates and returns a shallow clone of the list
  public LList clone() {
    LList newList = new LList();
    LNode tmp = head;

    while (tmp != null) {
      newList.enqueue(tmp.getVal());
      tmp = tmp.getNext();
    }

    return newList;
  }

  // Removes and returns the first element in the list
  public int dequeue() {
    if (head == null) {
      return -1;
    }

    int val = head.getVal();
    head = head.getNext();

    if (head == null) {
      tail = null;
    } else {
      head.setPrev(null);
    }

    return val;
  }

  // Adds a node at the front of the list (stack-like push)
  public void push(int v) {
    LNode tmp = new LNode(null, v, head);
    if (head != null) {
      head.setPrev(tmp);
    } else {
      tail = tmp;
    }
    head = tmp;
  }

  // Adds a node at the front (same as push)
  public void add(int v) {
    push(v);
  }

  // String representation of the list
  @Override
  public String toString() {
    LNode tmp = head;
    String ans = "";
    while (tmp != null) {
      ans += tmp.getVal() + ", ";
      tmp = tmp.getNext();
    }
    return "{" + ans + "}";
  }
}

// Node class for doubly linked list
class LNode {
  private int val;
  private LNode next, prev;

  public LNode(LNode p, int v, LNode n) {
    val = v;
    next = n;
    prev = p;
  }

  public int getVal() {
    return val;
  }

  public void setNext(LNode n) {
    next = n;
  }

  public LNode getNext() {
    return next;
  }

  public void setPrev(LNode p) {
    prev = p;
  }

  public LNode getPrev() {
    return prev;
  }
}
