package pardisLab3;

import java.util.Random;

public class pardisLab3Main {

  static void populate(LazySkipList<Integer> lsl) {
    Random rng = new Random();
    Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
    for (int i = 0; i < t.length; i++) {
      t[i] = new Thread() {
        public void run() {
          for (int i = 0; i < 1000000 / t.length; i++) {
            lsl.add(rng.nextInt());
          }
        }
      };
      t[i].start();
    }
    for (int i = 0; i < t.length; i++) {
      try {
        t[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  static void populateTimeStamp(LazySkipListTimeStamp<Integer> lsl) {
	    Random rng = new Random();
	    Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
	    
	    for (int i = 0; i < t.length; i++) {
	      t[i] = new Thread() {
	        public void run() {
	          for (int i = 0; i < 1000000 / t.length; i++) {
	            lsl.add(rng.nextInt());
	          }
	        }
	      };
	      t[i].start();
	    }
	    for (int i = 0; i < t.length; i++) {
	      try {
	        t[i].join();
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LazySkipList<Integer> list1 = new LazySkipList<Integer>();
		long time = System.currentTimeMillis();
		populate(list1);
		System.out.println("Listed populated in: " + (System.currentTimeMillis() - time) + " ms");
		
		System.out.println(list1.size());
		LazyTester.test(list1, 1000000, 1f, 0.2f, 0.6f);
		
		
		System.out.println("List modified");
		System.out.println(list1.size());
		
		
		
		
		LazySkipListTimeStamp<Integer> list2 = new LazySkipListTimeStamp<Integer>();
		populateTimeStamp(list2);
		LazyTester.testTimeStamp(list2,100,0.5f,0.5f,0f);
		System.out.println(LazyTester.allStamps);
		
		
		
		
		
	}
	
	
	
	


}
