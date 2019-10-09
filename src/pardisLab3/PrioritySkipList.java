package pardisLab3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicMarkableReference;

public final class PrioritySkipList<T> { 

  public static final int MAX_LEVEL = 25;
  final Node<T> head = new Node<T>(Integer.MIN_VALUE); 
  final Node<T> tail = new Node<T>(Integer.MAX_VALUE);

  public PrioritySkipList() {
    for (int i = 0; i < head.next.length; i++) { 
      head.next[i] = tail; 
    }
  }

  public static int randomLevel() {
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
  int find(Node<T> t, Node<T>[] preds, Node<T>[] succs) { 
    int key = t.key; 
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

  public boolean add(Node<T> x) { 
	int topLevel = x.topLevel; 
	Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
	Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
	while (true){ 
		int lFound = find(x, preds, succs); 
		if (lFound != -1) { 
			Node<T> nodeFound = succs[lFound]; 
			if (!nodeFound.marked) { 
				while (!nodeFound.fullyLinked) {} 
				return false;
				
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
			for (int level = 0; level <= topLevel; level++) 
				x.next[level] = succs[level]; 
			for (int level = 0; level <= topLevel; level++) 
				preds[level].next[level] = x; 
			x.fullyLinked = true; // successful add linearization point 
			
			return true;
			
			} finally { 
				for (int level = 0; level <= highestLocked; level++) 
					preds[level].unlock(); 
				} 
		} 
	}

  boolean remove(Node<T> x) { 
    Node<T> victim = null; int topLevel = -1; 
    Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
    Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
    
    while (true){ 
    	
    	
    	
      int lFound = find(x, preds, succs); 
      if (lFound != -1) victim = succs[lFound]; 
      if ( 
          (lFound != -1 && 
           (victim.fullyLinked 
            && victim.topLevel == lFound 
            && victim.marked))) { 

        topLevel = victim.topLevel; 
        if (!victim.marked) { 
          return false;
        }
        victim.lock(); 	

        int highestLocked = -1;
        try { 
			 Node<T> pred, succ; boolean valid = true; 
			 for (int level = 0; valid && (level <= topLevel); level++) { 
				 pred = preds[level]; 
				 pred.lock(); 
				 highestLocked = level; 
				 valid = !pred.marked && pred.next[level]==victim; 
				 } 
			 if (!valid) {

				 victim.lock.unlock(); 
				 continue; 
			 }
			 for (int level = topLevel; level >= 0; level--) { 
				 preds[level].next[level] = victim.next[level]; 
			 } 
			 victim.lock.unlock(); 
			 return true;
			 } finally { 
				 for (int i = 0; i <= highestLocked; i++) { 
					 preds[i].unlock(); 
				} 
			} 
      } else 
        return false;
      }
    } 


  public Node<T> findAndMarkMin(long timeStamp) { 
    Node<T> curr = null, succ = null; 
    curr = head.next[0]; 
    while (curr != tail) {
      curr.lock();
      if (!curr.marked && curr.timeStamp < timeStamp) { 
    	  curr.marked = true;
    	  curr.unlock();
          return curr;
      } else {
        curr.unlock();
        curr = curr.next[0]; 
      }
    } 
    return null; // no unmarked nodes 
  }
  
  public boolean contains(Node<T> x) { 
	 Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
	 Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1]; 
	 
	 int lFound = find(x, preds, succs); 
	 return (lFound != -1 
			 && succs[lFound].fullyLinked 
			 && !succs[lFound].marked);
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
}

