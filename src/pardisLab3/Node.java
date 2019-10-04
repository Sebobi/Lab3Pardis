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
	 public void lock() { 
		 lock.lock(); 
		 } 
	 public void unlock() { 
		 lock.unlock(); 
		 } 
	 } 
