public class printer{
	int startTime;
	int endTime;
	int processNumber;

	public printer(int start, int end, int process)
	{
		startTime=start;
		endTime=end;
		processNumber=process;
	}

	public int getStartTime()
	{
		return startTime;
	}
	public int getEndTime()
	{
		return endTime;
	}
	public int getProcessNumber()
	{
		return processNumber;
	}

}