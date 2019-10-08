package pardisLab3;

import java.util.ArrayList;
import java.util.LinkedList;
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
	private static ArrayList<LogElement> bigLog = new ArrayList<LogElement>();
	
	
	
	
	public static ArrayList<LogElement> testTimeStamp(LazySkipListTimeStamp<Integer> list,int totOps,float percentAdd, float percentRemove, float percentContains) {
		

		ArrayList<LogElement>[] logArray = new ArrayList[Runtime.getRuntime().availableProcessors()];
		
		
		
		Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
		
		int addOps = (int)(totOps*percentAdd/threads.length);
		int removeOps = (int)(totOps*percentRemove/threads.length);
		int containOps = (int)(totOps*percentContains/threads.length);
		
		System.out.println(threads.length + " threads, with each thread performing:");
		System.out.println(addOps + " add operations");
		System.out.println(removeOps + " remove operations");
		System.out.println(containOps + " contain operations");
		for(int i =0;i<threads.length;i++) {

			logArray[i] = new ArrayList<LogElement>();
			ArrayList<LogElement> log = logArray[i];
			threads[i] = new Thread() {
				public void run() {
					
					StringBuilder stamps = testThreadStamp(list,addOps,removeOps,containOps,log);
					LazyTester.appendStamps(stamps);
				}
			};
			threads[i].start();
		}
		
	    for (int i = 0; i < threads.length; i++) {
	        try {
	          threads[i].join();
	          bigLog.addAll(logArray[i]);
	        } catch (InterruptedException e) {
	          e.printStackTrace();
	        }
	      }

	    
	    return bigLog;
	}
	
	protected static synchronized void appendStamps(StringBuilder stamps) {
		allStamps.append(stamps);
		
		
		
	}

	public static StringBuilder testThreadStamp(LazySkipListTimeStamp<Integer> list, int addOps, int removeOps, int containOps,ArrayList<LogElement> log) {
		Random random = new Random();
		StringBuilder builder = new StringBuilder("");
		int totOps = addOps+removeOps+containOps;
		
		

		builder.append("\n-----------Thread with id: " +Thread.currentThread().getId() + " log------------");
		ReturnAndStamp returnS = new ReturnAndStamp(false,0);
		
		while(addOps + removeOps + containOps > 0) {
			int op = random.nextInt(3);
			int value = random.nextInt(totOps);
			
			if(op == 0 && addOps > 0) {
				addOps--;
				
				 returnS = list.add(value);
				 builder.append("\nAttempted adding "+ value + ". Success: " + returnS.returnVal + ". Stamp: " + returnS.time);
				 log.add(new LogElement(value,op,returnS.time,returnS.returnVal));
			
			}
			if(op == 1 && removeOps > 0) {
				removeOps--;
				returnS = list.remove(value);
				 builder.append("\nAttempted removing "+ value + ". Success: " + returnS.returnVal + ". Stamp: " + returnS.time);
				 log.add(new LogElement(value,op,returnS.time,returnS.returnVal));
			}
			if(op == 2 && containOps > 0) {
				containOps--;
				returnS = list.contains(value);
				 builder.append("\nAttempted contains on "+ value + ". Success: " + returnS.returnVal + ". Stamp: " + returnS.time);
				 log.add(new LogElement(value,op,returnS.time,returnS.returnVal));
			}
		}
		
		return builder;
	}
	
	
	
	public static void performSeq(ArrayList<LogElement> log, LazySkipListTimeStamp<Integer> list) {
		
		for(LogElement elem : log) {
			switch(elem.operation) {
			case LogElement.ADD:
				list.add(elem.value);
				break;
			case LogElement.REMOVE:
				list.remove(elem.value);
				break;
			case LogElement.CONTAINS:
				list.contains(elem.value);
				break;
			default:
				System.err.print("\n\n\n:(\n\n\n");
				break;
			
			}	
		}
		
	}
	
	
	
	
	
}
