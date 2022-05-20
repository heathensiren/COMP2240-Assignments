/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * P2.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class P2
{
    public static void main(final String args[]) throws IOException
    {
        //Scanning the filename
        final Scanner file = new Scanner(new File(args[0]));
        String text = "";

        //Creating an arraylist for jobs and a new printer
        final ArrayList<Jobs> jobList = new ArrayList<>();
        final Printer printer = new Printer(0, null);

        //Reading the file
        try
        {
            while (file.hasNext())
            {
                text = file.next();
                int numJobs = Integer.parseInt(text);
                file.nextLine();

                //Reads for number of jobs from the file
                for (int i = 0; i < numJobs; i++)
                {
                    String tempStr = file.nextLine();
                    final char job = tempStr.charAt(0);                         //Reads the first letter and assigns the job
                    final String[] splitStr = tempStr.split("\\s+");
                    final String jobNo = splitStr[0];                           //Reads the job number
                    final int noOfPages = Integer.parseInt(splitStr[1]);        //Reads the number of pages

                    //Creates a temp job variable and adds it to the joblist
                    Jobs temp = new Jobs(job, jobNo, noOfPages, printer);
                    jobList.add(temp);
                }

                //Parsing the number of jobs
                printer.setNumJobs(numJobs);
            }
        }
        catch (final Exception e)
        {
            System.out.println("Error: " + e);
        }
        //Close file when done
        file.close();

        //Parsing the joblist and then running the printer
        printer.setJobList(jobList);
        printer.run();

        //Displaying the results
        for (final Jobs j : jobList)
        {
            j.getData();
        }
        System.out.println(" (" + printer.getTime() + ") DONE.");
    }
}