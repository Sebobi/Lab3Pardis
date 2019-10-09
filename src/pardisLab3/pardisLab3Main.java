package pardisLab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

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
  

  static void populatePriorityQueueTimeStamp(SkipQueueTimeStamp<Integer> lsl,SkipQueueTimeStamp<Integer> listSeq) {
	    Random rng = new Random();
	    Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
	    
	    for (int i = 0; i < t.length; i++) {
	      t[i] = new Thread() {
	        public void run() {
	          for (int i = 0; i < 100000 / t.length; i++) {
	        	int value = rng.nextInt();
	        	int prio = rng.nextInt();
	            lsl.add(value, prio);
	            listSeq.add(value,prio);
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
  
  static void populateQueuesJava(SkipQueue<Integer> lsl,PriorityBlockingQueue<Integer> listJava) {
	    Random rng = new Random();
	    Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
	    
	    for (int i = 0; i < t.length; i++) {
	      t[i] = new Thread() {
	        public void run() {
	          for (int i = 0; i < 100000 / t.length; i++) {
	        	int value = rng.nextInt();
	        	int prio = rng.nextInt();
	            while(!lsl.add(value, prio)) {
	            	prio = rng.nextInt();
	            }
	            listJava.add(value);
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
		
		/*
		PrioritySkipList<Integer> list3 = new PrioritySkipList<Integer>();
		time = System.currentTimeMillis();
		populatePrio(list3);
		System.out.println("List populated in: " + (System.currentTimeMillis() - time) + " ms");
		
		System.out.println(list3.size());
		//LazyTester.testPrio(list3, 1000000, 1f, 0.2f, 0.6f);
		
		System.out.println("List modified");
		System.out.println(list3.size());*/
		
		
		SkipQueue<Integer> skippy = new SkipQueue<>();
	    skippy.add(23, 50);
	    skippy.add(3, 51);
	    skippy.add(2, 0);
	    skippy.add(28, -5);
		
	    for (int i = 0; i < 4; i++) {
	      System.out.println(skippy.removeMin()); // 28 2 23 3
	    }
			
		SkipQueueTimeStamp<Integer> skippy2 = new SkipQueueTimeStamp<>();
		SkipQueueTimeStamp<Integer> skippySeq = new SkipQueueTimeStamp<>();
		
		populatePriorityQueueTimeStamp(skippy2,skippySeq);
		
		System.out.println(skippy2.size());
		System.out.println(skippySeq.size());
		
		ArrayList<LogElement> operations2 = LazyTester.testPriorityTimeStamp(skippy2,10000,0.5f,0.3f,0.2f);
		Collections.sort(operations2);
		
		System.out.println(skippy2.size());
		
	
		
		LazyTester.performPrioritySeq(operations2, skippySeq);
		
		System.out.println(skippySeq.size());
		
		
		System.out.println(skippySeq.removeMin().returnObj);
		System.out.println(skippySeq.removeMin().returnObj);
		System.out.println(skippySeq.size());
		System.out.println("Done!:)");
		
		
		//Final test
		
		System.out.println("-----------");
		
		SkipQueue<Integer> ourQueue = new SkipQueue();
		PriorityBlockingQueue<Integer> theirQueue = new PriorityBlockingQueue();
		
		
		populateQueuesJava(ourQueue,theirQueue);
		
		
		System.out.println(ourQueue.size());
		System.out.println(theirQueue.size());
		
		int totalOps = 10000;
		float addOps = 0.5f;
		float removeOps = 0.5f;
		float containOps = 0f;
		int threads = 2;
		System.out.println(CompareQueues.testOurs(ourQueue,threads,totalOps,addOps,removeOps,containOps));
		
		
		System.out.println(CompareQueues.testJava(theirQueue,threads,totalOps,addOps,removeOps,containOps));
		
		
		
		
		
		
	}
}
