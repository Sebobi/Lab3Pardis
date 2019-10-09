package pardisLab3;

public class SkipQueue<T> {
  PrioritySkipList<T> skiplist;
  public SkipQueue() {
    skiplist = new PrioritySkipList<T>();
  }
  public boolean add(T item, int score) {
    Node<T> node = (Node<T>)new Node(item, PrioritySkipListTimeStamp.randomLevel(),score);
    return skiplist.add(node);
  }
  public T removeMin() {
	long timeStamp = System.nanoTime();
    Node<T> node = skiplist.findAndMarkMin(timeStamp);
    if (node != null) {
      skiplist.remove(node);
      return node.item;
    } else{
      return null;
    }
  }
 

	  
	  public boolean contains(T item,int score) {

		  Node<T> node = (Node<T>)new Node(item, PrioritySkipListTimeStamp.randomLevel(), score);
		  return skiplist.contains(node);
		  
		  
		  
	  }
	  

	  public int size() {
		  return skiplist.size();
	  }
}

