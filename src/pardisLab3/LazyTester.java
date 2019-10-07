package pardisLab3;

import java.util.Random;

public class LazyTester {
	
	public static void test(LazySkipList<Integer> list,int totOps,float percentAdd, float percentRemove, float percentContains) {
		
		Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
		
		int addOps = (int)(totOps*percentAdd/threads.length);
		int removeOps = (int)(totOps*percentRemove/threads.length);
		int containOps = (int)(totOps*percentContains/threads.length);
		
		System.out.println(threads.length + " threads, with each thread performing:");
		System.out.println(addOps + " add operations");
		System.out.println(removeOps + " remove operations");
		System.out.println(containOps + " contain operations");
			
		for(int i =0;i<threads.length;i++) {
			threads[i] = new Thread() {
				public void run() {
					testThread(list,addOps,removeOps,containOps);
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
	
	}
	
	public static void testThread(LazySkipList<Integer> list, int addOps, int removeOps, int containOps) {
		Random random = new Random();
		
		while(addOps + removeOps + containOps > 0) {
			int op = random.nextInt(3);
			if(op == 0 && addOps > 0) {
				addOps--;
				list.add(random.nextInt());
			}
			if(op == 1 && removeOps > 0) {
				removeOps--;
				list.remove(random.nextInt());
			}
			if(op == 2 && containOps > 0) {
				containOps--;
				list.contains(random.nextInt());
			}
		}
	}
	
	public static StringBuilder allStamps = new StringBuilder("");
	
	public static void testTimeStamp(LazySkipListTimeStamp<Integer> list,int totOps,float percentAdd, float percentRemove, float percentContains) {
		
		Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
		
		int addOps = (int)(totOps*percentAdd/threads.length);
		int removeOps = (int)(totOps*percentRemove/threads.length);
		int containOps = (int)(totOps*percentContains/threads.length);
		
		System.out.println(threads.length + " threads, with each thread performing:");
		System.out.println(addOps + " add operations");
		System.out.println(removeOps + " remove operations");
		System.out.println(containOps + " contain operations");
		for(int i =0;i<threads.length;i++) {
			threads[i] = new Thread() {
				public void run() {
					StringBuilder stamps = testThreadStamp(list,addOps,removeOps,containOps);
					LazyTester.appendStamps(stamps);
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
	}
	
	protected static synchronized void appendStamps(StringBuilder stamps) {
		allStamps.append(stamps);
		
		
		
	}

	public static StringBuilder testThreadStamp(LazySkipListTimeStamp<Integer> list, int addOps, int removeOps, int containOps) {
		Random random = new Random();
		StringBuilder builder = new StringBuilder("");

		builder.append("\n-----------Thread with id: " +Thread.currentThread().getId() + " log------------");
		ReturnAndStamp returnS = new ReturnAndStamp(false,0);
		
		while(addOps + removeOps + containOps > 0) {
			int op = random.nextInt(3);
			int value = random.nextInt();
			
			if(op == 0 && addOps > 0) {
				addOps--;
				
				 returnS = list.add(value);
				 builder.append("\nAttempted adding "+ value + ". Success: " + returnS.returnVal + ". Stamp: " + returnS.time);
			}
			if(op == 1 && removeOps > 0) {
				removeOps--;
				returnS = list.remove(value);
				 builder.append("\nAttempted removing "+ value + ". Success: " + returnS.returnVal + ". Stamp: " + returnS.time);
				
			}
			if(op == 2 && containOps > 0) {
				containOps--;
				returnS = list.contains(value);
				 builder.append("\nAttempted contains on "+ value + ". Success: " + returnS.returnVal + ". Stamp: " + returnS.time);
				
			}
		}
		
		return builder;
	}
}
