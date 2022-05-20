import java.io.IOException;

/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * P1.java
 */

import java.io.*;
import java.util.Scanner;

public class P1
{

    public static void main(final String args[]) throws IOException
    {
        //Scanning the filename
        final Scanner myReader = new Scanner(new File(args[0]));
        String text = "";
        final Intersection intersect = new Intersection();

        //Reading the file
        try
        {
            int counter = 0;
            while (myReader.hasNext())
            {
                text = myReader.next();
                final char side = text.charAt(0);

                //If it reads N
                if (side == 'N')
                {

                    final String tempNo = text.substring(2, 3);
                    final int number = Integer.parseInt(tempNo);

                    //Then create a northern WAR
                    for (int i = 0; i < number; i++)
                    {
                        final WAR war = new WAR("WAR-" + (counter + 1), "Storage 2", intersect);
                        counter++;
                        war.start();
                    }
                }

                //If it reads E
                else if (side == 'E')
                {

                    final String tempNo = text.substring(2, 3);
                    final int number = Integer.parseInt(tempNo);

                    //Then create an eastern WAR
                    for (int i = 0; i < number; i++)
                    {
                        final WAR war = new WAR("WAR-" + (counter + 1), "Dock 1", intersect);
                        counter++;
                        war.start();
                    }
                }

                //If it reads S
                else if (side == 'S')
                {

                    final String tempNo = text.substring(2, 3);
                    final int number = Integer.parseInt(tempNo);

                    //Then create a southern WAR
                    for (int i = 0; i < number; i++)
                    {
                        final WAR war = new WAR("WAR-" + (counter + 1), "Dock 2", intersect);
                        counter++;
                        war.start();
                    }
                }

                //If it reads W
                else if (side == 'W')
                {

                    final String tempNo = text.substring(2, 3);
                    final int number = Integer.parseInt(tempNo);

                    //Then create a western WAR
                    for (int i = 0; i < number; i++)
                    {
                        final WAR war = new WAR("WAR-" + (counter + 1), "Storage 1", intersect);
                        counter++;
                        war.start();
                    }
                }
            }
        }

        //If that doesn't work then run this exception error
        catch (final Exception e)
        {
            System.out.println("Error reading file. Try again");
        }

        //Then close the reader
        myReader.close();
    }
}