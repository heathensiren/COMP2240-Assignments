/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * FBV.java
 */

import java.util.ArrayList;
import java.util.Comparator;

public class FBV
{
    private ArrayList<Process> waitingQ = new ArrayList<>();            //Arraylist for incoming processes
    private ArrayList<Process> readyQ = new ArrayList<>();              //Arraylist for jobs that are ready to be processed
    private ArrayList<Process> completedQ = new ArrayList<Process>();         //Arraylist for all completed processes
    private int DISP;                                                   //Variable for dispatcher time
    private int time = 0;                                               //Variable for the current time
    private int execTime = 0;                                           //Variable for the execution time
    private Process currentProcess;                                     //Current process that is running
    private double avgTT;                                               //Variable for the average turnaround time
    private double avgWT;                                               //Variable for the average wait time

    //Constructor class for FBV
    public FBV(ArrayList<Process> processes, int dispatcher)
    {
        int processNum = processes.size();
        for (int i = 0; i<processNum; i++)
        {
            waitingQ.add(processes.get(i));
        }
        DISP = dispatcher;
        currentProcess = null;
        avgTT = 0;
        avgWT = 0;
    }

    //Method to run the FBV algorithm
    public void Run ()
    {
        System.out.println("\nFBV:");
        while (waitingQ.size() != 0 || readyQ.size() != 0 || currentProcess != null)
        {
            if (currentProcess == null)                         //Checks to see if the current process is null
            {
                Ready();                                        //Checks to see if there are any processes ready
                time += DISP;                                   //Adding the dispatcher time to the time variable
                if (readyQ.size() != 0)                         //If the ready queue is not empty, then it is removed and added to the current process
                {
                    currentProcess = readyQ.remove(0);
                }
            }
            else            //Break the while loop so the program at least runs properly
            {
                break;
            }
        }
        printTimes();
        setAverages();
    }

    //Checks if there are any processes in the waiting queue that are ready to be processed
    private void Ready()
    {
        if (waitingQ.size() != 0)		                                    //Checks waiting queue to see if it is empty
        {
            int temp = waitingQ.get(0).getArrivalTime();	                //Temp variable to store the arrival time at the front of the queue
            while (temp <= time)							                //Checks if the process is ready by comparing the arrival time to the temp time
            {
                readyQ.add(waitingQ.remove(0));			            //Removing the process at the front of the waiting queue and add to the ready queue
                if (waitingQ.size() == 0)                                   //If the waiting queue is null then break the loop
                {
                    break;
                }
                else
                {
                    temp = waitingQ.get(0).getArrivalTime();
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

    //Method to get the averages for turnaround and waiting time
    private void setAverages()
    {
        for (int i = 0;  i < completedQ.size(); i++)
        {
            avgTT += completedQ.get(i).getTAT();
            avgWT += completedQ.get(i).getWT();
        }
        avgTT /= completedQ.size();
        avgWT /= completedQ.size();
    }

    //Getters for average turnaround time and wait time
    public double getAvgTT()
    {
        return avgTT;
    }


    public double getAvgWT()
    {
        return avgWT;
    }
}
