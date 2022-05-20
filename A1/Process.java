/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * Process.java
 */
public class Process
{
    //Setting variables for process class
    private int processID;
    private int arrivalTime;
    private int TAT;            //Turnaround time
    private int WT;             //Wait time
    private int tickets;
    private int serviceTime;
    private int quanta;

    //Constructor for process class
    public Process(int id, int arriveTime, int executionSize)
    {
        processID = id;
        arrivalTime = arriveTime;
        serviceTime = executionSize;
        quanta = 0;
        TAT = 0;
        WT = 0;
    }

    //-----------------Getters and setters-----------------

    //Returning a service time
    public int getServiceTime()
    {
        return serviceTime;
    }

    //Setting service time
    public void setServiceTime(int st)
    {
        serviceTime = st;
    }

    //Returning a process ID
    public int getID()
    {
        return processID;
    }

    //Returning the arrival time for the process
    public int getArrivalTime()
    {
        return arrivalTime;
    }

    //Setting the turnaround and waiting time
    public void setTATWT(int complete)
    {
        TAT = complete - arrivalTime;
        WT = TAT - serviceTime;
    }

    //Returning the turnaround time
    public int getTAT()
    {
        return TAT;
    }

    //Returning the waiting time
    public int getWT()
    {
        return WT;
    }

    //Setting the tickets
    public void setTickets(int x)
    {
        tickets = x;
    }

    //Returning the tickets
    public int getTickets()
    {
        return tickets;
    }

    //Resetting variables inside of the process
    public void reset()
    {
        serviceTime = serviceTime;
        TAT = 0;
        WT = 0;
    }
}