package pardisLab3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicMarkableReference;

public final class PrioritySkipList<T> { 
	
	 public static final int MAX_LEVEL = 25;
	 final NodePrio<T> head = new NodePrio<T>(Integer.MIN_VALUE); 
	 final NodePrio<T> tail = new NodePrio<T>(Integer.MAX_VALUE);
	
	public PrioritySkipList() {
		for (int i = 0; i < head.next.length; i++) { 
			head.next[i].set(tail, false); 
		} 
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
	 int find(NodePrio<T> t, NodePrio<T>[] preds, NodePrio<T>[] succs) { 
		 int key = t.score; 
		 int lFound = -1; 
		 NodePrio<T> pred = head; 
		 for (int level = MAX_LEVEL; level >= 0; level--) { 
			 NodePrio<T> curr = pred.next[level].getReference();
			 while (key > curr.score) { 
				 pred = curr; curr = pred.next[level].getReference(); 
				 } 
			 if (lFound == -1 && key == curr.score) { 
				 lFound = level; 
				 } 
			 preds[level] = pred; 
			 succs[level] = curr; 
			 } 
		 return lFound; 
	} 
	
	
	 
	 
	boolean add(NodePrio<T> node) {

		int topLevel = randomLevel(); 
		node.topLevel = topLevel;
		
		NodePrio<T>[] preds = (NodePrio<T>[]) new NodePrio[MAX_LEVEL + 1];
		NodePrio<T>[] succs = (NodePrio<T>[]) new NodePrio[MAX_LEVEL + 1]; 
		
		while (true){ 
			int lFound = find(node, preds, succs); 
			if (lFound != -1) { 
				NodePrio<T> nodeFound = succs[lFound]; 
				if (!nodeFound.marked.get()) { 
					while (!nodeFound.fullyLinked) {} 
					return false; 
					} 
				continue; 
				} 

			NodePrio<T> pred, succ; 
			boolean valid = true; 
			for (int level = 0; valid && (level <= topLevel); level++) { 
				pred = preds[level]; 
				succ = succs[level]; 
				valid = !pred.marked.get() && !succ.marked.get() && pred.next[level].getReference()==succ; 
			} 	
			if (!valid) continue; 
			
			for (int level = 0; level <= topLevel; level++) 
				node.next[level].set(succs[level],false); 
			for (int level = 0; level <= topLevel; level++) 
				if (!preds[level].next[level].compareAndSet(succs[level], node, false, false))
					continue;
			node.fullyLinked = true; // successful add linearization point 
			node.timeStamp = System.nanoTime();
			return true;
		} 
		
	} 
	boolean remove(NodePrio<T> x) { 
		 NodePrio<T> victim = null; int topLevel = -1; 
		 NodePrio<T>[] preds = (NodePrio<T>[]) new NodePrio[MAX_LEVEL + 1]; 
		 NodePrio<T>[] succs = (NodePrio<T>[]) new NodePrio[MAX_LEVEL + 1]; 
		 while (true){ 
			 int lFound = find(x, preds, succs); 
			 if (lFound != -1) victim = succs[lFound]; 
			 if ( 
					 (lFound != -1 && 
					 (victim.fullyLinked 
							 && victim.topLevel == lFound 
							 && victim.marked.get()))) { 
				  
					 topLevel = victim.topLevel; 
					 if (!victim.marked.get()) { 
						 return false; 
						 } 
					 
				 
				 NodePrio<T> pred, succ; boolean valid = true; 
				 for (int level = 0; valid && (level <= topLevel); level++) { 
					 pred = preds[level];  
					 valid = !pred.marked.get() && pred.next[level].getReference()==victim; 
					 } 
				 if (!valid) continue; 
				 for (int level = topLevel; level >= 0; level--) { 
					 preds[level].next[level].compareAndSet(victim, victim.next[level].getReference(), false, false); 
					 } 
				 return true; 


				 } else 
					 return false; 
			 } 
		 } 
	
	
		public NodePrio<T> findAndMarkMin(long timeStamp) { 
			NodePrio<T> curr = null, succ = null; 
			curr = head.next[0].getReference(); 
			while (curr != tail) { 
				if (!curr.marked.get() && curr.timeStamp < timeStamp) { 
					if (curr.marked.compareAndSet(false, true)) { 
						return curr; 
						} else { 
							curr = curr.next[0].getReference(); 
							} 
					} 
				} 
			return null; // no unmarked nodes 
    }

    public int size() {
      boolean done = false;
      int size = 0;
      NodePrio<T> node = head;
      while(!done) {
        node = node.next[0].getReference();
        if(node.score != tail.score) {
          size++;
        } else {
          done = true;
        }
      }
      return size;
    }
}

