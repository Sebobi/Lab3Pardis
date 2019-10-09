package pardisLab3;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class CompareQueues {
	
	public static long testOurs(SkipQueue<Integer> ourQueue,int numThreads,int totOps,float percentAdd,float percentRemove,float percentContains) {
		
		
		
		
		Thread[] threads = new Thread[numThreads];
		
		int addOps = (int)(totOps*percentAdd/threads.length);
		int removeOps = (int)(totOps*percentRemove/threads.length);
		int containOps = (int)(totOps*percentContains/threads.length);
		
		long start = System.currentTimeMillis();
		
		for(int i =0;i<threads.length;i++) {

			threads[i] = new Thread() {
				public void run() {
					
					testQueueThreadwise(ourQueue,addOps,removeOps,containOps);
				}
			};
			threads[i].start();
		}
		
	    for (int i = 0; i < threads.length; i++) {
	        try {
	          threads[i].join();
	        } catch (InterruptedException e) {
	          e.printStackTrace();
	        }
	      }
	    
	    return System.currentTimeMillis() - start;
		
		
	}
	
	
	public static void testQueueThreadwise(SkipQueue<Integer> queue, int addOps, int removeOps, int containOps) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int totOps = addOps+removeOps+containOps;
		
		for(int i =0;i<totOps;i++) {
			int value = random.nextInt();
			int priority = random.nextInt(totOps);
			int op = random.nextInt(totOps);
			if(op<addOps) {
				queue.add(value, priority);
			} else if (op < addOps + removeOps) {
				queue.removeMin();
			} else {
				queue.contains(value,  priority);
			}
		}
	}
	
	
	public static long testJava(PriorityBlockingQueue<Integer> ourQueue,int numThreads,int totOps,float percentAdd,float percentRemove,float percentContains) {
		
		
		
		
		Thread[] threads = new Thread[numThreads];
		
		int addOps = (int)(totOps*percentAdd/threads.length);
		int removeOps = (int)(totOps*percentRemove/threads.length);
		int containOps = (int)(totOps*percentContains/threads.length);
		
		long start = System.currentTimeMillis();
		
		for(int i =0;i<threads.length;i++) {

			threads[i] = new Thread() {
				public void run() {
					
					testJavaThreadWise(ourQueue,addOps,removeOps,containOps);
				}
			};
			threads[i].start();
		}
		
	    for (int i = 0; i < threads.length; i++) {
	        try {
	          threads[i].join();
	        } catch (InterruptedException e) {
	          e.printStackTrace();
	        }
	      }
	    
	    return System.currentTimeMillis() - start;
		
		
	}
	
	
	public static void testJavaThreadWise(PriorityBlockingQueue<Integer> queue, int addOps, int removeOps, int containOps) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int totOps = addOps+removeOps+containOps;
		
		
		for (int i = 0; i < totOps; i++) {
			int op = random.nextInt(totOps);
			int value = random.nextInt();
			
			if(op < addOps) {
				queue.add(value);
			}
			else if(op < addOps + removeOps) {
				queue.remove();
					
			}
			else {
				queue.contains(value);
			}
		}
	}
	
	

}
