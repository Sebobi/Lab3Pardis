package pardisLab3;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicMarkableReference;


public final class NodePrio<T>{
	final T item; 
	final int score; 
	AtomicBoolean marked; 
	final AtomicMarkableReference<NodePrio<T>>[] next; 
	public int topLevel;
	public volatile boolean fullyLinked;
	// sentinel node constructor 
	public NodePrio(int myPriority) {		 
		this.item = null; 
		this.score = myPriority;
		next = new AtomicMarkableReference[PrioritySkipList.MAX_LEVEL+1];
	 } 
	
	// ordinary node constructor 
	public NodePrio(T x, int myPriority){ 
		this.item = x;
		score = myPriority;	
		
		next = new AtomicMarkableReference[PrioritySkipList.MAX_LEVEL+1];
		
	} 
}
