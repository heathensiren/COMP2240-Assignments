import java.util.concurrent.TimeUnit;

/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * WAR.java
 */
public class WAR extends Thread
{

    //Private variables
    private String name;                        //A variable to store the name of the war
    private String destination;                 //A variable to store the destination of the war
    private String direction;                   //A variable to store the direction the war is going
    private String status;                      //A variable to store the status of the war
    private int track1 = 0;                     //A variable to store track 1
    private int track2 = 0;                     //A variable to store track 2
    private final Intersection intersection;    //A variable to store the intersection

    //A constructor for the WAR class
    public WAR(final String name, final String dest, final Intersection intersect)
    {
        this.name = name;
        this.destination = dest;
        this.intersection = intersect;
        setDirection();

    }

    //A setter to store the status of the war
    public void setStatus(String status)
    {
        this.status = status;
    }

    //A setter to store track 1
    public void setTrack1(int track1)
    {
        this.track1 = track1;
    }

    //A setter to store track 2
    public void setTrack2(int track2)
    {
        this.track2 = track2;
    }

    //Method to set the direction the war is going in
    public void setDirection()
    {
        //If the war was north then change the direction to south
        if (destination =="Storage 2")
        {
            this.direction = "Dock 2";
            setStatus("(Loaded)");          //Set the status of the war to loaded
            setTrack2(track2++);            //Add to the number of wars that have crossed track 2
        }

        //If the war was south then change the direction to north
        else if (destination =="Dock 2")
        {
            this.direction = "Storage 2";
            setStatus("(Unloaded)");        //Set the status of the war to unloaded
            setTrack2(track2++);            //Add to the number of wars that have crossed track 2
        }

        //If the war was east then change the direction to west
        else if (destination =="Dock 1")
        {
            this.direction = "Storage 1";
            setStatus("(Unloaded)");        //Set the status of the war to unloaded
            setTrack1(track1++);            //Add to the number of wars that have crossed track 1
        }

        //If the war was west then change the direction to east
        else if (destination == "Storage 1")
        {
            this.direction = "Dock 1";
            setStatus("(Loaded)");          //Set the status of the war to loaded
            setTrack1(track1++);            //Add to the number of wars that have crossed track 1
        }
    }

    //A method to set the destination of the war
    public void setDestination()
    {
        //If the war was in storage 2 then change the destination to dock 2
        if (destination == "Storage 2")
        {
            this.destination = "Dock 2";
        }

        //If the war was in dock 2 then change the destination to storage 2
        else if (destination =="Dock 2")
        {
            this.destination = "Storage 2";
        }

        //If the war was in dock 1 then change the destination to storage 1
        else if (destination == "Dock 1")
        {
            this.destination = "Storage 1";
        }

        //If the war was in storage 1 then change the destination to dock 1
        else if (destination == "Storage 1")
        {
            this.destination = "Dock 1";
        }
    }

    //A method to print all statements set all war names and statuses
    public void run()
    {
        //While track 1 and track 2 aren't at 150 wars then run this
        while (intersection.getTrack1() != 150 && intersection.getTrack2() != 150)
        {
            System.out.println(this.name + " " + this.status + ": Waiting at Intersection. Going towards " + direction);
            intersection.warCrossing(this.destination);

            System.out.println(this.name + " " + this.status + " : Crossing intersection Checkpoint 1.");

            //For each checkpoint, try to  use timeunit to convert to milliseconds - making output faster
            try
            {
                TimeUnit.MILLISECONDS.sleep(15);   //
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(this.name + " " + this.status + " : Crossing intersection Checkpoint 2.");

            try
            {
                TimeUnit.MILLISECONDS.sleep(15);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(this.name + " " + this.status + " : Crossing intersection Checkpoint 3.");

            try
            {
                TimeUnit.MILLISECONDS.sleep(15);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(this.name + " " + this.status + " : Crossed the intersection.");

            setDestination();               //Set the new destination when war has crossed intersection
            setDirection();                 //Resetting war direction
            intersection.exit();
        }
    }
}