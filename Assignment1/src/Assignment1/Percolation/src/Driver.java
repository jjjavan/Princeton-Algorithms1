import edu.princeton.cs.algs4.StdRandom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver
{
    public static void main(String[] args)
    {
        File file = new File("input6.txt");
        try (Scanner stdIn = new Scanner(file))
        {
            int size = 1;
            Percolation system = new Percolation(size);

            /*while(!system.percolates())
            {
                int row = StdRandom.uniform(1,size+1);
                int col = StdRandom.uniform(1,size+1);
                system.open(row,col);

            }*/
            System.out.println(system.numberOfOpenSites());
            if(system.percolates())
                System.out.printf("p* = %.4f%n", system.numberOfOpenSites()/Math.pow((double)size,2));
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File does not exist.");
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);
        System.out.printf("mean                    = %.16f%n",percStats.mean());
        System.out.printf("stddev                  = %.16f%n",percStats.stddev());
        System.out.printf("95%% confidence interval = [%.16f %.16f]%n", percStats.confidenceLo(), percStats.confidenceHi());
    }
}
   /* public static void main(String[] args)
    {
        File file = new File("input20.txt");
        try (Scanner stdIn = new Scanner(file))
        {
            int size = stdIn.nextInt();
            Percolation system = new Percolation(size);
            while(stdIn.hasNext() && !system.percolates())
            {
                int row = stdIn.nextInt();
                int col = stdIn.nextInt();
                system.open(row,col);

            }
            System.out.println(system.numberOfOpenSites());
            if(system.percolates())
            System.out.printf("p* = %.4f%n", system.numberOfOpenSites()/Math.pow((double)size,2));
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File does not exist.");
        }

    }
}
*/
