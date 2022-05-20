/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * FCFS.java
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class FCFS
{
    private Queue<Process> waitingQ = new LinkedList<>();               //Queue for incoming processes
    private Queue<Process> readyQ = new LinkedList<>();                 //Queue for jobs that are ready to be processed
    private ArrayList<Process> completedQ = new ArrayList<Process>();         //Arraylist for all completed processes
    private int DISP;                                                   //Variable for dispatcher time
    private int time = 0;                                               //Variable for the current time
    private Process currentProcess;                                     //Current process that is running
    private double avgTT;                                               //Variable for the average turnaround time
    private double avgWT;                                               //Variable for the average wait time

    //Constructor for class
    public FCFS(ArrayList<Process> processes, int dispatcher)
    {
        int processNum = processes.size();
        for (int i = 0; i < processNum; i++)
        {
            waitingQ.add(processes.get(i));
        }
        DISP = dispatcher;
        currentProcess = null;
        avgTT = 0;
        avgWT = 0;
    }

    //Method to run the FCFS algorithm
    public void Run()
    {
        System.out.println("FCFS:");
        // While the there is a process waiting then keep looping until it is met
        while (waitingQ.peek() != null || readyQ.peek() != null || currentProcess != null)
        {
            if (currentProcess == null)                                 //If there are no current processes then run this
            {
                Ready();                                                //Checks to see if there are any ready processes
                time += DISP;                                           //Dispatcher time is added to the time variable
                if (waitingQ.peek() == null && readyQ.peek() == null)   //If all jobs are done
                {
                    break;                                              //Break the while loop
                }

                if (readyQ.peek() != null)                              //If there is something in the ready queue then remove and set it as a current process
                {
                    currentProcess = readyQ.remove();
                }
            }
            else
            {
                System.out.println("T" + time + ": p" + currentProcess.getID());        //Printing the time and current process ID
                time += currentProcess.getServiceTime();                                //Adding the current service time to the time variable
                currentProcess.setTATWT(time);                                          //Setting the turnaround and waiting time for the current process
                avgTT += currentProcess.getTAT();                                       //Adding the turnaround time to the variable
                avgWT += currentProcess.getWT();                                        //Adding the wait time to the variable
                completedQ.add(currentProcess);                                         //Add the current process to completed
                currentProcess = null;                                                  //Set current process to null to clear
            }
        }
        printTimes();
        setAverages();
    }

    //Checks if there are any processes in the waiting queue that are ready to be processed
    private void Ready()
    {
        if (waitingQ.peek() != null)		                //Checks waiting queue to see if it is empty
        {
            int temp = waitingQ.peek().getArrivalTime();	//Temp variable to store the arrival time at the front of the queue
            while (temp <= time)							//Checks if the process is ready by comparing the arrival time to the temp time
            {
                readyQ.add(waitingQ.remove());			    //Removing the process at the front of the waiting queue and add to the ready queue
                if (waitingQ.peek() == null)                //If the waiting queue is null then break the loop
                {
                    break;
                }
                else
                {
                    temp = waitingQ.peek().getArrivalTime();
                }
            }
        }
    }

    //Method to print the turnaround and waiting time
    private void printTimes()
    {
        System.out.println("\nProcess\tTurnaround Time\tWaiting Time");
        for (int i = 1; i < completedQ.size() + 1; i++)
        {
            for (int y = 0; y < completedQ.size(); y++)
            {
                if (i == completedQ.get(y).getID())
                {
                    System.out.println("p" + completedQ.get(y).getID() + "\t" + completedQ.get(y).getTAT() + "\t\t" + completedQ.get(y).getWT());
                }
            }
        }
    }

    //Method for setting the average turnaround time and wait time
    private void setAverages()
    {
        //Getting the average for turnaround time and wait time
        avgTT /= completedQ.size();
        avgWT /= completedQ.size();
    }

    //Getters for average turnaround and wait time
    public double getAvgTT()
    {
        return avgTT;
    }


    public double getAvgWT()
    {
        return avgWT;
    }
}