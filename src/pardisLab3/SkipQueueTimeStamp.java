package pardisLab3;

public class SkipQueueTimeStamp<T> {
  PrioritySkipListTimeStamp<T> skiplist;
  public SkipQueueTimeStamp() {
    skiplist = new PrioritySkipListTimeStamp<T>();
  }
  public ReturnAndStamp add(T item, int score) {
    NodePrio<T> node = (NodePrio<T>)new NodePrio(item, score);
    return skiplist.add(node);
  }
  public ReturnAndStamp removeMin() {
    long timeStamp = System.nanoTime();
    ReturnAndStamp ras = skiplist.findAndMarkMin(timeStamp);
    if (ras.returnObj != null) {
      skiplist.remove((NodePrio<T>)ras.returnObj);
      
    } 
    return ras;
    
  }
  
  public ReturnAndStamp contains(T item,int score) {

	  NodePrio<T> node = (NodePrio<T>)new NodePrio(item, score);
	  return skiplist.contains(node);
	  
	  
	  
  }
  
  public int size() {
	  return skiplist.size();
  }
  
}

