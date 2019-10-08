package pardisLab3;

import java.util.Random;

public final class LazySkipListTimeStamp<T> {

	
	
	
	 public static final int MAX_LEVEL = 25;
	 final Node<T> head = new Node<T>(Integer.MIN_VALUE); 
	 final Node<T> tail = new Node<T>(Integer.MAX_VALUE);
	 public LazySkipListTimeStamp() { 
		 for (int i = 0; i < head.next.length; i++) { 
			 head.next[i] = tail; 
			 } 
		 } 
	 int find(T x, Node<T>[] preds, Node<T>[] succs) { 
		 int key = x.hashCode(); 
		 int lFound = -1; 
		 Node<T> pred = head; 
		 for (int level = MAX_LEVEL; level >= 0; level--) { 
			 Node<T> curr = pred.next[level]; 
			 while (key > curr.key) { 
				 pred = curr; curr = pred.next[level]; 
				 } 
			 if (lFound == -1 && key == curr.key) { 
				 lFound = level; 
				 } 
			 preds[level] = pred; 
			 succs[level] = curr; 
			 } 
		 return lFound; 
	} 
	 
	private int randomLevel() {
		Random random = new Random();
		boolean finished = false;
		int level = 0;
		while(!finished) {
			if(level == MAX_LEVEL)
				return level;
			int val = random.nextInt(2);
			if(val == 0)
				finished = true;
			else {
				level++;
			}
			
		}
		return level;
			
	}
	 
	public ReturnAndStamp add(T x) { 
		int topLevel = randomLevel(); 
		Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
		Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
		while (true){ 
			int lFound = find(x, preds, succs); 
			if (lFound != -1) { 
				Node<T> nodeFound = succs[lFound]; 
				if (!nodeFound.marked) { 
					while (!nodeFound.fullyLinked) {} 
					synchronized(this) {
						return new ReturnAndStamp(false,System.nanoTime()); 	
					}
					
					} 
				continue; 
				} 
			int highestLocked = -1; 
			try { 
				Node<T> pred, succ; 
				boolean valid = true; 
				for (int level = 0; valid && (level <= topLevel); level++) { 
					pred = preds[level]; 
					succ = succs[level]; 
					pred.lock.lock(); 
					highestLocked = level; 
					valid = !pred.marked && !succ.marked && pred.next[level]==succ; 
					} 
				if (!valid) continue; 
				Node<T> newNode = new Node(x, topLevel); 
				for (int level = 0; level <= topLevel; level++) 
					newNode.next[level] = succs[level]; 
				for (int level = 0; level <= topLevel; level++) 
					preds[level].next[level] = newNode; 
				newNode.fullyLinked = true; // successful add linearization point 
				
				synchronized(this) {
					return new ReturnAndStamp(true,System.nanoTime()); 	
				}
				
				} finally { 
					for (int level = 0; level <= highestLocked; level++) 
						preds[level].unlock(); 
					} 
			} 
		}
	
	
	 public ReturnAndStamp remove(T x) { 
		 Node<T> victim = null; boolean isMarked = false; int topLevel = -1; 
		 Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
		 Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
		 while (true){ 
			 int lFound = find(x, preds, succs); 
			 if (lFound != -1) victim = succs[lFound]; 
			 if (isMarked | 
					 (lFound != -1 && 
					 (victim.fullyLinked 
							 && victim.topLevel == lFound 
							 && !victim.marked))) { 
				 if (!isMarked) { 
					 topLevel = victim.topLevel; 
					 victim.lock.lock(); 
					 if (victim.marked) { 
						 victim.lock.unlock(); 
						 synchronized(this) {
							 return new ReturnAndStamp(false,System.nanoTime());  
						 }
					} 
					 victim.marked = true; 
					 isMarked = true; 
					 } 
				 int highestLocked = -1; 
				 try { 
					 Node<T> pred, succ; boolean valid = true; 
					 for (int level = 0; valid && (level <= topLevel); level++) { 
						 pred = preds[level]; 
						 pred.lock.lock(); 
						 highestLocked = level; 
						 valid = !pred.marked && pred.next[level]==victim; 
						 } 
					 if (!valid) continue; 
					 for (int level = topLevel; level >= 0; level--) { 
						 preds[level].next[level] = victim.next[level]; 
						 } 
					 victim.lock.unlock(); 
					 synchronized(this) {

						 return new ReturnAndStamp(true,System.nanoTime());  
					 }
					 } finally { 
						 for (int i = 0; i <= highestLocked; i++) { 
							 preds[i].unlock(); 
							 } 
						 } 
				 } else 
					 synchronized(this){
						 return new ReturnAndStamp(false,System.nanoTime()); 
					 }
			 } 
		 } 
	 
	 
	 public ReturnAndStamp contains(T x) { 
		 Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
		 Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
		 int lFound = find(x, preds, succs); 
		 
		 synchronized(this) {
		 return new ReturnAndStamp((lFound != -1 
				 && succs[lFound].fullyLinked 
				 && !succs[lFound].marked),System.nanoTime()); 
		 }
		 }
	 
	 
	 public int size() {
		 boolean done = false;
		 int size = 0;
		 Node<T> node = head;
		 while(!done) {
			 node = node.next[0];
			 if(node.key != tail.key) {
				 size++;
			 } else {
				 done = true;
			 }
		 }
		 return size;
	 }
	 
	 public boolean compareTo(LazySkipListTimeStamp<T> other) {
		 boolean done = false;
		 Node<T> node1 = head;
		 Node<T> node2 = other.head;
		 
		 while(!done) {
			 node1 = node1.next[0];
			 node2 = node2.next[0];
			 
			 if(node1.key != node2.key)
				 return false;
			 
			 if(node1.next[0] == null && node2.next[0] == null)
				 done = true;
				 
		 }
		
		 return true;
		 
		 
		 
	 }
		
}	
	
	 
	 

	 

