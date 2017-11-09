//Process Control Block
public class PCB
{	
	private int processNumber;
	private int arrivalTime;
	private int burstTime;
	private int priority;
	private int startTime;

	//constructor
	public PCB(String process, int count)
	{
		arrivalTime = Character.getNumericValue(process.charAt(0));
		burstTime = Character.getNumericValue(process.charAt(2));
		priority = Character.getNumericValue(process.charAt(4));
		processNumber=count;
	}

	//methods
	public int getProcessNumber()
	{
		return processNumber;
	}

	public int getArrivalTime()
	{
		return arrivalTime;
	}

	public int getBurstTime()
	{
		return burstTime;
	}

	public int getPriority()
	{
		return priority;
	}

	public int getStartTime()
	{
		return startTime;
	}

	public void setStartTime(int t)
	{
		startTime = t;
	}
	
	public void reduceTime()
	{
		if(burstTime >0)
			burstTime--;
	}
}