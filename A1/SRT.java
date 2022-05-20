/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * SRT.java
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class SRT
{
    private ArrayList<Process> waitingQ = new ArrayList<>();            //Arraylist for incoming processes ---- changed this and readyq to be arraylist because I needed to use sort() and get()
    private ArrayList<Process> readyQ = new ArrayList<>();              //Arraylist for jobs that are ready to be processed
    private ArrayList<Process> completedQ = new ArrayList<Process>();   //Arraylist for all completed processes
    private int DISP;                                                   //Variable for dispatcher time
    private int time = 0;                                               //Variable for the current time
    private int execTime = 10;                                          //Variable for the execution time
    private Process currentProcess;                                     //Current process that is running
    private double avgTT;                                               //Variable for the average turnaround time
    private double avgWT;                                               //Variable for the average wait time

    //Constructor for SRT class
    public SRT(ArrayList<Process> processes, int dispatcher)
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

    //Method to run the SRT algorithm
    public void Run()
    {
        System.out.println("\nSRT:");

        // While there is a current process and no other processes run this
        while (waitingQ.size() != 0 || readyQ.size() != 0 || currentProcess != null)
        {
            if (currentProcess == null)                                                     //If there is no current process run this
            {
                Ready();                                                                    //Checks to see if there is anything waiting
                readyQ.sort(Comparator.comparing(Process::getServiceTime));                 //Sorting what's ready by service time
                time += DISP;
                if (readyQ.size() != 0)                                                     //Checking ready queue to see if there is any processes
                {
                    currentProcess = readyQ.remove(0);                               //Storing current process into ready queue (head of the queue) and then removing from head
                    System.out.println("T" + time + ": p" + currentProcess.getID());
                    currentProcess.setTATWT(time);
                    completedQ.add(currentProcess);
                }
            }
            else if (readyQ.size() == 0)                      //Otherwise, if there is a current process and nothing in the ready queue run this
            {
                Ready();                                                                    //Checks to see if there is anything waiting
                readyQ.sort(Comparator.comparing(Process::getServiceTime));                 //Sorting what's ready by service time
                time += DISP;
                currentProcess.setServiceTime(currentProcess.getServiceTime() - DISP);  //Resetting service time to decrease the amount it has been processed
                if (currentProcess.getServiceTime() == 0)                               //If service is 0, then add it to completed q and set to null
                {
                    completedQ.add(currentProcess);
                    currentProcess = null;
                }
            }
            else //If the current process is not null
            {
                Ready();
                readyQ.sort(Comparator.comparing(Process::getServiceTime));             //Sort by service time
                if (currentProcess.getServiceTime() > readyQ.get(0).getServiceTime())   //Checks to see if the current service time is greater than the ready q's shortest service time
                {
                    Process temp = currentProcess;                                      //Set temp variable
                    currentProcess = readyQ.remove(0);                           //Swap around
                    System.out.println("T" + time + ": p" + currentProcess.getID());    //Because new process, print it out
                    currentProcess.setTATWT(time);
                    completedQ.add(currentProcess);
                    currentProcess = null;
                }
                //Program doesn't make it past this point for some reason?
                else if (currentProcess.getServiceTime() < readyQ.get(0).getServiceTime()) //Checks to see if the current service time is less than the ready q's shortest service time
                {
                    time += DISP;
                    currentProcess.setServiceTime(currentProcess.getServiceTime() - DISP); //Resetting service time to decrease by the dispatch time the amount it has been processed
                    if (currentProcess.getServiceTime() == 0)                              //If service is 0, then add it to completed q and set to null
                    {
                        completedQ.add(currentProcess);
                        currentProcess = null;

                    }
                }
                else //Checks to see if the current service time is equal to the ready queue's shortest service time
                {
                    if (currentProcess.getID() < readyQ.get(0).getID())                         //If ID is less than read q's ID
                    {
                        time += DISP;
                        currentProcess.setServiceTime(currentProcess.getServiceTime() - DISP);  //Resetting service time to decrease the amount it has been processed
                        if (currentProcess.getServiceTime() == 0)                               //If service is 0, then add it to completed q and set to null
                        {
                            completedQ.add(currentProcess);
                            currentProcess = null;
                        }
                    }
                    else //If current ID is greater than ready q's ID
                    {
                        Process temp = currentProcess;                      //Setting temp variable
                        currentProcess = readyQ.remove(0);           //Swap them bad bois //todo delete
                        System.out.println("T" + time + ": p" + currentProcess.getID());
                    }
                }
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
            waitingQ.sort(Comparator.comparing(Process::getArrivalTime));
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
