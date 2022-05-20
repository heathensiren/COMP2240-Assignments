/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * Jobs.java
 */

public class Jobs extends Thread
{
    //Private variables
    private String jobNo;           //Variable to store the jobType number
    private int pages;              //Variable to store the number of pages
    private int time;               //Variable to store the time
    private int headNo;             //Variable to store the head number
    private char jobType;           //Variable to store the job type
    private final Printer printer;  //Variable to store the printer

    //A constructor for the jobs class
    public Jobs(char jobType, String jobNo, int pages, final Printer p)
    {
        this.jobNo = jobNo;
        this.pages = pages;
        this.jobType = jobType;
        this.printer = p;
        this.headNo = 1;
    }

    //Getters and setters
    public String getJobNo()
    {
        return jobNo;
    }

    public char getJobType()
    {
        return jobType;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public void setHeadNo(int headNo)
    {
        this.headNo = headNo;
    }

    //Method to display the results
    public void getData()
    {
        //Formatting information
        final String startTimeStr = String.format(" (" + this.time + ") ");
        final String pagesStr = String.format(" (time: " + this.pages + ") \n");

        System.out.print(startTimeStr + " " + getJobNo() + " uses head " + headNo + pagesStr);
    }

    //Method to run the jobs
    public void run()
    {
        try
        {
            //If there are jobs to run then run this
            if (true)
            {
                synchronized (this)
                {
                    //If it is colour and there are available heads then run this
                    if (printer.colour() && printer.availableHeads())
                    {
                        printer.nextJob();                          //Gets the next job from the queue
                        this.setHeadNo(printer.getCurrentJobs());   //Assigns a head to the job
                        this.setTime(printer.getTime());            //Sets the time
                        wait();                                     //Waits to make sure that all jobs are run before starting the next
                    }

                    //If it is monochrome then run this
                    else if (!printer.colour())
                    {
                        printer.nextJob();                          //Gets the next job from the queue
                        this.setHeadNo(printer.getCurrentJobs());   //Assigns a head to the job
                        this.setTime(printer.getTime());            //Sets the time
                        wait();                                     //Waits to make sure that all jobs are run before starting the next
                    }

                    //Sets the time for when the job starts
                    setTime(printer.getTime());
                }
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
            System.out.println("Error: " + e);
        }
    }
}