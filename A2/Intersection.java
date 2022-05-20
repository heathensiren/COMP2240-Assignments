/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * Intersection.java
 */

import java.util.concurrent.Semaphore;

public class Intersection
{
    //Private variables
    private int crossed;            //A variable to store the wars crossed
    private final Semaphore sema;   //A semaphore for the intersection object
    private int track1 = 0;         //A variable to store track 1
    private int track2 = 0;         //A variable to store track 2

// ----------------------------------------------------------------------------------------------------

    //A constructor for the intersection class
    public Intersection()
    {
        crossed = 0;                                   //Assigning the value of wars crossed as 0
        sema = new Semaphore(1, true);  //Creating a new semaphore with 1 intersection
    }

    //Getter to return value of track 1
    public int getTrack1()
    {
        return track1;
    }

    //Getter to return value of track 2
    public int getTrack2()
    {
        return track2;
    }

    //Method to allow WAR to cross intersection
    public void warCrossing(String destination)
    {
        try
        {
            //Acquiring the lock on the intersection
            sema.acquire();

            //If the war was in the north then change the location
            if (destination.equals("Storage 2"))
            {
                track2++;
            }

            //If the war was in the east then change the location
            else if (destination.equals("Dock 1"))
            {
                track1++;
            }

            //If the war was in the south then change the location
            else if (destination.equals("Dock 2"))
            {
                track2++;
            }

            //If the war was in the west then change the location
            else if (destination.equals("Storage 1"))
            {
                track1++;
            }
            crossed++;

        }
        catch (final Exception e)
        {
            System.out.println("Error: " + e);
        }
    }

    //Method to print the total numbers of wars that crossed track 1 and 2 and exit conditions
    public void exit()
    {
        System.out.println("Total Crossed in Track 1: " + this.track1 + " Track 2: " + this.track2);

        //If either track 1 or 2 reaches 150 wars crossed then exit
        if (getTrack1() == 150 || getTrack2() == 150)
        {
            System.exit(0);
        }
        sema.release();     //Release the lock on the intersection
    }
}