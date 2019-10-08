package pardisLab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
  static void populatePrio(PrioritySkipList<Integer> psl) {
    Random rng = new Random();
    Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
    for (int i = 0; i < t.length; i++) {
      t[i] = new Thread() {
        public void run() {
          for (int i = 0; i < 1000000 / t.length; i++) {
            psl.add(new NodePrio<Integer>(rng.nextInt(), rng.nextInt()));
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
  
  
  static void populateTimeStamp(LazySkipListTimeStamp<Integer> lsl,LazySkipListTimeStamp<Integer> listSeq) {
	    Random rng = new Random();
	    Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
	    
	    for (int i = 0; i < t.length; i++) {
	      t[i] = new Thread() {
	        public void run() {
	          for (int i = 0; i < 1000000 / t.length; i++) {
	        	int value = rng.nextInt();
	            lsl.add(value);
	            listSeq.add(value);
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
		System.out.println("List populated in: " + (System.currentTimeMillis() - time) + " ms");
		
		System.out.println(list1.size());
		LazyTester.test(list1, 1000000, 1f, 0.2f, 0.6f);

		System.out.println("List modified");
		System.out.println(list1.size());
		
		
		
		
		LazySkipListTimeStamp<Integer> list2 = new LazySkipListTimeStamp<Integer>();
		LazySkipListTimeStamp<Integer> list2seq = new LazySkipListTimeStamp<Integer>();
		populateTimeStamp(list2,list2seq);
		
		
		
		ArrayList<LogElement> operations = LazyTester.testTimeStamp(list2,1000,0.5f,0.5f,0f);
		Collections.sort(operations);
		
		
	
		LazyTester.performSeq(operations, list2seq);
	
		System.out.println(list2.compareTo(list2seq));
		System.out.println(list2.size());
		System.out.println(list2seq.size());
		
		
		//System.out.println(LazyTester.allStamps);
		
		
		PrioritySkipList<Integer> list3 = new PrioritySkipList<Integer>();
		time = System.currentTimeMillis();
		populatePrio(list3);
		System.out.println("List populated in: " + (System.currentTimeMillis() - time) + " ms");
		
		System.out.println(list3.size());
		//LazyTester.testPrio(list3, 1000000, 1f, 0.2f, 0.6f);
		
		System.out.println("List modified");
		System.out.println(list3.size());
		
		
		SkipQueue<Integer> skippy = new SkipQueue<>();
		
		
		
	}
}
