

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileNotFoundException;
import java.util.Arrays;


public class BruteCollinearPoints
{
    private LineSegment[] rays;
    private int n;

    public BruteCollinearPoints(Point[] points)
    {
        //Precondition: N is >= 4 points.

        validate(points);

        int size = points.length, sizeI = size - 3,
                sizeJ = size - 2, sizeK = size - 1;

        Point[] pCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            pCopy[i] = points[i];

        Arrays.sort(pCopy);


        rays = new LineSegment[points.length*2];
        n = 0;
        for (int i = 0; i < sizeI; i++)
            for (int j = i + 1; j < sizeJ; j++)
                for (int k = j + 1; k < sizeK; k++)
                    for (int l = k + 1; l < size; l++)
                        if (pCopy[i].slopeTo(pCopy[j]) == pCopy[i].slopeTo(pCopy[k]) && pCopy[i].slopeTo(pCopy[k]) == pCopy[i].slopeTo(pCopy[l]))
                        {
                            if (n == rays.length) resize(2*rays.length);
                            rays[n++] = new LineSegment(pCopy[i], pCopy[l]);
                        }


    }

    private void resize(int size)
    {
        LineSegment[] tmp = new LineSegment[size];
        for (int i = 0; i < n; i++)
            tmp[i] = rays[i];
        rays = tmp;
    }
    private void validate(Point[] p)
    {
        if (p == null)
            throw new IllegalArgumentException("Point[] is a null.");

        for (int i = 0; i < p.length - 1; i++)
            for (int j = i + 1; j < p.length ; j++)
                if (p[i].compareTo(p[j]) == 0)
                    throw new IllegalArgumentException("Points are equal.");
                else if (p[i] == null || p[j] == null)
                    throw new IllegalArgumentException("Points are null.");
    }

    public int numberOfSegments()
    {
        return n;
    }
    public LineSegment[] segments()
    {
        LineSegment[] tmp = new LineSegment[n];
        for(int i = 0; i < n; ++i )
            tmp[i] = rays[i];
        return tmp;
    }

    public static void main(String[] args) throws FileNotFoundException
    {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();// print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

