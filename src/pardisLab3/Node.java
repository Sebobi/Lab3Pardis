package pardisLab3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Node<T> { 
	 final Lock lock = new ReentrantLock(); 
	 final T item; 
	 final int key; 
	 final Node<T>[] next; 
	 volatile boolean marked = false; 
	 volatile boolean fullyLinked = false; 
	 public int topLevel;
	 public long timeStamp;
	 public Node(int key) { // sentinel node constructor 
		 this.item = null; 
		 this.key = key; 
		 next = new Node[LazySkipList.MAX_LEVEL + 1]; 
		 topLevel = LazySkipList.MAX_LEVEL; 
		 } 
	 public Node(T x, int height) { 
		 item = x; 
		 key = x.hashCode(); 
		 next = new Node[height + 1]; 
		 topLevel = height; 
		 }
	 public Node(T x, int height, int score) {
		 item = x; key = score; topLevel = height; next = new Node[height + 1];
	 }
	 public void lock() { 
		 lock.lock(); 
		 } 
	 public void unlock() { 
		 lock.unlock(); 
		 } 
	 } 
