package pardisLab3;

public class ReturnAndStamp {
	
	public boolean returnVal;
  public Object returnObj;
	public long time;
	

	public ReturnAndStamp(boolean returnVal, long time) {
		this.returnVal = returnVal;
		this.time = time;
	}

	public ReturnAndStamp(Object returnObj, long time) {
		this.returnObj = returnObj;
		this.time = time;
	}
}
