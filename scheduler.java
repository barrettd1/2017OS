/*Danyelle Barrett
  Operating Systems: Project 1
  Due: 11/9/17
  pPH: Preemptive Priority High (higher number has higher priority)
*/
import java.util.*;
import java.io.*;
class scheduler 
{

	public static void main(String[] args) throws IOException
	{
		//read input.data 
		BufferedReader inputStream = new BufferedReader(new FileReader("input.data"));
        String line = inputStream.readLine();
        //set up output file
        PrintWriter out = new PrintWriter("output.data", "UTF-8");

        int numOfProcesses =0; //# of processes to be scheduled 
        int count=1; //makes sure only the specified # of processes are scheduled
        int preemptive = 1;  //will always be preemptive
        int quantum = 1;	//default quantum 
        ArrayList<PCB> Jobs = new ArrayList<PCB>();

        //loop to read every line in the file
        while(line != null)                 
        {
        	//if the first line, assign it to be the number of processes
			if(line.length() == 1)
			{
				numOfProcesses = Integer.parseInt(line);
			}
			//if the second line with Preemptive and Quantum, set quantum
			else if(line.length() == 3)
			{
				quantum = Character.getNumericValue(line.charAt(2));
			}
			/* else, line is a process.
			The if statement makes sure it is within the # of processes.
			This checks if more processes then specified on the first line
			are sent, then they will not be scheduled.
			It then creates a PCB for the process and place it in the Job Queue.
			*/
			else if(numOfProcesses >= count)
			{
           		PCB p = new PCB(line, count);
           		Jobs.add(p);
           		count++;
			}

            line = inputStream.readLine(); //proceed to next line
        }
        inputStream.close();

        //send all PCB's to the dispatcher.
        dispatcher(out, Jobs, quantum);

        out.close();

	}

	//Simulates a dispatcher. dispatcher will choose which program to 
	// send to CPU (write to output.data)
	public static void dispatcher(PrintWriter out, ArrayList<PCB> processes, int quantum)
	{
		int time=0;
		int startTime=0;
		ArrayList<PCB> arrived = new ArrayList<PCB>();
		PCB highestP = new PCB("0 0 0", 0);
		ArrayList<printer> print = new ArrayList<printer>();


		//loop to send processes to CPU 
		//will continue until processes is empty
		//each time around the loop simulates one second
		while(processes.size() != 0)
		{
			//go through all process
			for(int i =0; i <processes.size(); i++)
			{
				//if a process has arrived at this time, add it to arrived array
				if(processes.get(i).getArrivalTime() == time){
					arrived.add(processes.get(i));
				}
			}
			//from those arrived, see which has highest priority
			//at quantum time
			if(arrived.size() !=0){
				if(time%quantum==0){
					for(int i =0; i < arrived.size(); i++)
					{
						if(arrived.get(i).getPriority() > highestP.getPriority()){
							highestP = arrived.get(i);
							//set the start time
							startTime= time;
							highestP.setStartTime(time);
						}
					}
				}
				
				//execute process
				highestP.reduceTime();
				
				//***DEBUG: See what is running each second of time***
				//System.out.println(startTime + " " + (time+1) + " " + highestP.getProcessNumber());
				
				//send info to printer
				print.add(new printer(startTime, (time+1), highestP.getProcessNumber()) );
				
				//check if process has completed
				if(highestP.getBurstTime() == 0){
					processes.remove(highestP);
					arrived.remove(highestP);
					//reset highest priority
					highestP = new PCB("0 0 0", 0);
					startTime=time+1;
				}
			}

			time++;
		}

		//print to output.data only end values, not every second of time
		for(int i = 0; i < print.size()-1; i++){
			int  j=i+1; 
			if(print.get(i).getProcessNumber() != print.get(j).getProcessNumber()){
				if(print.get(i).getProcessNumber() !=0)  //0 is not a process, means nothing is runnning
					out.println(print.get(i).getStartTime() + " " + print.get(i).getEndTime() + " " + print.get(i).getProcessNumber());		
			}
		}
		int last = print.size() - 1;
		out.println(print.get(last).getStartTime() + " " + print.get(last).getEndTime() + " " + print.get(last).getProcessNumber());
	}

}


