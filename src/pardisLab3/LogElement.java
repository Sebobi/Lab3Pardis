package pardisLab3;

public class LogElement implements Comparable<LogElement>{
	public static final int ADD = 0;
	public static final int REMOVE = 1;
	public static final int CONTAINS = 2;
	
	public int value;
	public int priority;
	public int operation;
	public long returnStamp;
	public boolean result;
	public LogElement(int value, int operation, long returnStamp, boolean result) {
		this.value = value;
		this.operation = operation;
		this.returnStamp = returnStamp;
		this.result = result;
	}
	public LogElement(int value,int priority, int operation, long returnStamp, boolean result) {
		this.value = value;
		this.priority = priority;
		this.operation = operation;
		this.returnStamp = returnStamp;
		this.result = result;
	}
	@Override
	public int compareTo(LogElement arg) {
		if(returnStamp-arg.returnStamp < 0)
			return -1;
		else if(returnStamp-arg.returnStamp > 0)
			return 1;		
		return 0;
	}
	
}
