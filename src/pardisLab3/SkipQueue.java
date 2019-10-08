package pardisLab3;

public class SkipQueue<T> {
  PrioritySkipList<T> skiplist;
  public SkipQueue() {
    skiplist = new PrioritySkipList<T>();
  }
  public boolean add(T item, int score) {
    NodePrio<T> node = (NodePrio<T>)new NodePrio(item, score);
    return skiplist.add(node);
  }
  public T removeMin() {
	long timeStamp = System.nanoTime();
    NodePrio<T> node = skiplist.findAndMarkMin(timeStamp);
    if (node != null) {
      skiplist.remove(node);
      return node.item;
    } else{
      return null;
    }
  }
}

