/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * Printer3.java
 */

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Printer3
{
    //Private variables
    private ArrayList<Jobs3> jobList;        //An arraylist for the joblist
    private final int maxJobs;              //Variable to hold the max number of jobs
    private int numJobs;                    //Variable for all number of jobs
    private int currentJobs;                //Variable for current jobs running
    private int time;                       //Variable to store the time of the job
    private boolean heads;                  //Boolean to see if there are any available heads
    private final Semaphore sema;           //Initialising the sempahore


    //A constructor for the printer class
    public Printer3(int numJobs, final ArrayList<Jobs3> jobList)
    {
        this.jobList = jobList;
        this.maxJobs = 3;
        this.numJobs = numJobs;
        this.currentJobs = 0;
        this.time = 0;
        this.heads = false;
        this.sema = new Semaphore(3);   //Limits the semaphore to the 3 printer heads
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

    public void setJobList(ArrayList<Jobs3> jobList)
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
        //Making sure there are 3 heads
        if (this.sema.availablePermits() == 3)
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
        for (final Jobs3 j : jobList)
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
