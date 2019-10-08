package pardisLab3;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicMarkableReference;


public final class NodePrio<T>{
	final T item; 
	final int score; 
	AtomicBoolean marked = new AtomicBoolean(false);
	final AtomicMarkableReference<NodePrio<T>>[] next = new AtomicMarkableReference[PrioritySkipList.MAX_LEVEL+1]; 
	public int topLevel;
	public volatile boolean fullyLinked = false;
	// sentinel node constructor 
	public NodePrio(int myPriority) {		 
		this.item = null; 
		this.score = myPriority;
    for (int i = 0; i < next.length; i++) {
      next[i] = new AtomicMarkableReference(null, false);
    }
	 } 
	
	// ordinary node constructor 
	public NodePrio(T x, int myPriority){ 
		this.item = x;
		score = myPriority;	
    for (int i = 0; i < next.length; i++) {
      next[i] = new AtomicMarkableReference(null, false);
    }
	} 
}
