/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * Printer.java
 */

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Printer
{
    //Private variables
    private ArrayList<Jobs> jobList;        //An arraylist for the joblist
    private final int maxJobs;              //Variable to hold the max number of jobs
    private int numJobs;                    //Variable for all number of jobs
    private int currentJobs;                //Variable for current jobs running
    private int time;                       //Variable to store the time of the job
    private boolean heads;                  //Boolean to see if there are any available heads


    //A constructor for the printer class
    public Printer(int numJobs, final ArrayList<Jobs> jobList)
    {
        this.jobList = jobList;
        this.maxJobs = 3;
        this.numJobs = numJobs;
        this.currentJobs = 0;
        this.time = 0;
        this.heads = false;
    }

    //Getters and Setters
    public int getCurrentJobs()
    {
        return currentJobs;
    }

    public int getMaxJobs()
    {
        return maxJobs;
    }

    public void setJobList(ArrayList<Jobs> jobList)
    {
        this.jobList = jobList;
    }

    public int getNumJobs()
    {
        return numJobs;
    }

    public int getTime()
    {
        return time;
    }

    public void setNumJobs(int numJobs)
    {
        this.numJobs = numJobs;
    }

    //Method to see if the job is colour or monochrome
    public boolean colour()
    {
        //Parse through the joblist and if it contains a C then return as true
        if (Objects.equals(jobList.get(0).getJobType(), "C"))
        {
            return true;
        }
        return false;
    }

    //Method to see if there are any available heads
    public boolean availableHeads()
    {
        if (this.maxJobs == this.currentJobs)
        {
            return true;
        }
        return false;
    }

    //Method to get the next job
    public void nextJob()
    {
        this.currentJobs++;
    }

    //Method to run the printer
    public void run()
    {
        //Start thread
        for (final Jobs j : jobList)
        {
            j.start();
        }

        //While all jobs are filled and  max number of jobs does not exceed 3, then run this
        while (getNumJobs() == 0 && getMaxJobs() != 3)
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
            //Increment time value
            this.time++;
        }
    }
}
