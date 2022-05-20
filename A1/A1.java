/**
 * COMP2240
 * Mirak Bumnanpol c3320409
 * A1.java
 */

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class A1
{
    public static void main (String args [])
    {
        //Setting variables and arraylist as we do not know how many jobs there will be
        BufferedReader br = null;
        int DISP = 0;
        int ID = 0;
        int arrivalTime = 0;
        int execSize = 0;
        ArrayList<Process> processList = new ArrayList<Process>();

        try
        {
            //Reading file
            br = new BufferedReader(new FileReader("."+File.separator+args[0]));
            String line = br.readLine();
            //While the datafile contains EOF keep running below
            while (!line.equals("EOF"))
            {
                if (!line.equals(""))
                {
                    String x = line.substring(0,2);
                    //If the datafile contains DI (dispatcher), then set the disp time to the variable
                    if (x.equals("DI"))
                    {
                        DISP = Integer.parseInt(line.substring(6));
                    }
                    //If the datafile contains ID then set the id process to the variable
                    if (x.equals("ID"))
                    {
                        ID = Integer.parseInt(line.substring(5));
                    }
                    //If the datafile contains Ar for arrive, then set the arrival time to the arrival time variable
                    if (x.equals("Ar"))
                    {
                        arrivalTime = Integer.parseInt(line.substring(8));
                    }
                    //If the datafile contains Ex for execsize, then set the execsize to the variable. Then create a new process p and add it to the joblist
                    if (x.equals("Ex"))
                    {
                        int leng = line.length();
                        execSize = Integer.parseInt(line.substring(10,leng));
                        Process p = new Process(ID,arrivalTime, execSize);
                        processList.add(p);
                    }

                }
                line = br.readLine();
            }
        }
        //Printing exception
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Close the reader
                br.close();
            }
            catch (IOException e)
            {
                //Printing exception
                e.printStackTrace();
            }
        }

        //Running algorithms for FCFS, SRT, FBV, LTR. Then resetting the process details
        FCFS algorithmFCFS = new FCFS(processList, DISP);
        algorithmFCFS.Run();
        resetProcess(processList);


        SRT algorithmSRT = new SRT(processList, DISP);
        algorithmSRT.Run();
        resetProcess(processList);


        FBV algorithmFBV = new FBV(processList, DISP);
        algorithmFBV.Run();
        resetProcess(processList);


        LTR algorithmLTR = new LTR(processList, DISP);
        algorithmLTR.Run();
        resetProcess(processList);


        //Printing the summmary

        System.out.println("\nSummary\nAlgorithm\tAverage Turnaround Time\tAverage Waiting Time");
        System.out.println("FCFS\t\t" + String.format("%.2f\t \t \t",algorithmFCFS.getAvgTT()) + String.format("%.2f",algorithmFCFS.getAvgWT()));
        System.out.println("SRT\t\t" + String.format("%.2f\t \t \t",algorithmSRT.getAvgTT()) + String.format("%.2f",algorithmSRT.getAvgWT()));
        System.out.println("FBV\t" + String.format("%.2f\t \t \t",algorithmFBV.getAvgTT()) + String.format("%.2f",algorithmFBV.getAvgWT()));
        System.out.println("LTR\t\t" + String.format("%.2f\t \t \t",algorithmLTR.getAvgTT()) + String.format("%.2f",algorithmLTR.getAvgWT()));
    }

    // Method to reset the process as mentioned above
    private static void resetProcess(ArrayList<Process> jobslist)
    {
        int x = jobslist.size();
        for (int z =0; z < x; z++)
        {
            jobslist.get(z).reset();
        }
    }
}